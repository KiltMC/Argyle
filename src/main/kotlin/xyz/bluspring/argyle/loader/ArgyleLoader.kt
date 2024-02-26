package xyz.bluspring.argyle.loader

import com.google.gson.JsonParser
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.Version
import net.fabricmc.loader.impl.FabricLoaderImpl
import net.fabricmc.loader.impl.ModContainerImpl
import net.fabricmc.loader.impl.discovery.ModCandidate
import net.fabricmc.loader.impl.gui.FabricGuiEntry
import net.fabricmc.loader.impl.gui.FabricStatusTree
import net.fabricmc.loader.impl.launch.FabricLauncherBase
import net.fabricmc.loader.impl.metadata.LoaderModMetadata
import org.spongepowered.asm.mixin.FabricUtil
import org.spongepowered.asm.mixin.Mixins
import xyz.bluspring.argyle.Argyle
import xyz.bluspring.argyle.impl.ModContributorImpl
import xyz.bluspring.argyle.loader.mod.FabricModMetadata
import xyz.bluspring.argyle.loader.mod.QuiltMod
import xyz.bluspring.argyle.loader.mod.QuiltModContainer
import xyz.bluspring.argyle.wrappers.FabricVersionWrapper
import java.io.File
import java.util.zip.ZipFile
import kotlin.io.path.toPath
import kotlin.system.exitProcess

class ArgyleLoader {
    fun preloadMods() {
        Argyle.logger.info("Scanning the mods directory for Quilt mods...")

        val modsDir = File(FabricLoader.getInstance().gameDir.toFile(), "mods")

        if (!modsDir.exists() || !modsDir.isDirectory)
            throw IllegalStateException("Mods directory doesn't exist! ...how did you even get to this point?")

        val modFiles = modsDir.listFiles { file -> file.extension == "jar" } ?: throw IllegalStateException("Failed to load mod files!")

        val thrownExceptions = mutableMapOf<String, Exception>()

        modFiles.forEach { modFile ->
            thrownExceptions.putAll(preloadJarMod(modFile, ZipFile(modFile)))
        }

        // If exceptions had occurred during preloading, then create a window to show the exceptions.
        if (thrownExceptions.isNotEmpty()) {
            Argyle.logger.error("Exceptions occurred in Quilt mod loading! Creating window..")

            FabricGuiEntry.displayError("Exceptions occurred whilst loading Quilt mods in Argyle!", null, {
                val errorTab = it.addTab("Argyle Error")

                thrownExceptions.forEach { (name, exception) ->
                    errorTab.node.addMessage("$name failed to load!", FabricStatusTree.FabricTreeWarningLevel.ERROR)
                        .addCleanedException(exception)
                }

                // Little workaround to show the custom tab
                it.tabs.removeIf { tab -> tab != errorTab }
            }, true)

            exitProcess(1)
        }

        // TODO: validate deps
    }

    fun preloadJarMod(modFile: File, jarFile: ZipFile): Map<String, Exception> {
        // don't you dare load Fabric mods, we will end up in an infinite loop if we do.
        if (jarFile.getEntry("fabric.mod.json") != null)
            return mapOf()

        val thrownExceptions = mutableMapOf<String, Exception>()

        try {
            val qmjEntry = jarFile.getEntry("quilt.mod.json")

            if (qmjEntry == null) {
                Argyle.logger.warn("Did not load mod ${modFile.name} due to missing QMJ!")
                return mapOf()
            }

            val qmj = JsonParser.parseReader(jarFile.getInputStream(qmjEntry).reader()).asJsonObject

            val schemaVer = qmj.get("schema_version").asInt
            if (schemaVer == 1) {
                val qlMeta = qmj.getAsJsonObject("quilt_loader")
                val qlMetaData = qlMeta.getAsJsonObject("metadata")

                val mod = QuiltMod(
                    id = qlMeta.get("id").asString,
                    group = qlMeta.get("group").asString,
                    version = FabricVersionWrapper(Version.parse(qlMeta.get("version").asString)),
                    name = qlMetaData.get("name").asString,
                    description = qlMetaData.get("description").asString,
                    licenses = listOf(),
                    contributors = qlMetaData.get("contributors").asJsonObject.asMap().map {
                        ModContributorImpl(it.key, listOf(it.value.asString))
                    },
                    contactInfos = qlMetaData.get("contact").asJsonObject.asMap().map {
                        it.key to it.value.asString
                    }.toMap(),
                    icon = qlMetaData.get("icon").asString,
                    values = mapOf(),
                    nested = listOf(),
                    paths = listOf(modFile.toPath()),
                    depends = listOf(),
                    breaks = listOf(),
                    mixin = if (qmj.has("mixin")) qmj.get("mixin").asString else "",
                    intermediate = qlMeta.get("intermediate_mappings").asString
                )

                val candidate = createModCandidate(mod)
                mod.container = QuiltModContainer(candidate)

                Argyle.logger.info("Discovered Quilt mod ${mod.name()} (${mod.id()}) ${mod.version().raw()}")

                addModToLoader(mod)
                FabricLauncherBase.getLauncher().addToClassPath(modFile.toURI().toPath())

                if (mod.mixin.isNotBlank()) {
                    Mixins.addConfiguration(mod.mixin)

                    val config = Mixins.getConfigs().firstOrNull { it.name == mod.mixin } ?: return mapOf()
                    config.config.decorate(FabricUtil.KEY_MOD_ID, mod.id())
                    config.config.decorate(FabricUtil.KEY_COMPATIBILITY, FabricUtil.COMPATIBILITY_LATEST)
                }
            }
        } catch (e: Exception) {
            thrownExceptions[modFile.name] = e
            e.printStackTrace()
        }

        return thrownExceptions
    }

    fun createModCandidate(mod: QuiltMod): ModCandidate {
        //createPlain(List<Path> paths, LoaderModMetadata metadata, boolean requiresRemap, Collection<ModCandidate> nestedMods)
        val createPlainMethod = ModCandidate::class.java.getDeclaredMethod("createPlain", List::class.java, LoaderModMetadata::class.java, Boolean::class.java, Collection::class.java)
        createPlainMethod.isAccessible = true

        val metadata = createLoaderMetadata(mod)

        return createPlainMethod.invoke(this, mod.paths, metadata, false, mutableListOf<ModCandidate>().apply {
            mod.nested.forEach {
                this.add(createModCandidate(it))
            }
        }) as ModCandidate
    }

    fun createLoaderMetadata(mod: QuiltMod): FabricModMetadata {
        return FabricModMetadata(mod)
    }

    fun addModToLoader(mod: QuiltMod) {
        FabricLoaderImpl.INSTANCE.modsInternal.add(mod.container)

        val modMapField = FabricLoaderImpl::class.java.getDeclaredField("modMap")
        modMapField.isAccessible = true
        val modMap = modMapField.get(FabricLoaderImpl.INSTANCE) as MutableMap<String, ModContainerImpl>

        modMap[mod.id()] = mod.container
    }
}