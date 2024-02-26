package org.quiltmc.loader.api

import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ObjectShare
import net.fabricmc.loader.impl.FabricLoaderImpl
import org.quiltmc.loader.api.entrypoint.EntrypointContainer
import org.quiltmc.loader.api.entrypoint.EntrypointException
import xyz.bluspring.argyle.wrappers.FabricEntrypointContainerWrapper
import xyz.bluspring.argyle.wrappers.FabricMappingResolverWrapper
import xyz.bluspring.argyle.wrappers.FabricModContainerWrapper
import java.nio.file.Path
import java.util.*
import net.fabricmc.loader.api.EntrypointException as FabricEntrypointException

class QuiltLoader private constructor() {
    companion object {
        @JvmStatic
        @Throws(EntrypointException::class)
        fun <T> getEntrypoints(key: String, type: Class<T>): List<T> {
            return try {
                FabricLoader.getInstance().getEntrypoints(key, type)
            } catch (e: FabricEntrypointException) {
                throw EntrypointException(e)
            }
        }

        @JvmStatic
        @Throws(EntrypointException::class)
        fun <T> getEntrypointContainers(key: String, type: Class<T>): List<EntrypointContainer<T>> {
            return try {
                FabricLoader.getInstance().getEntrypointContainers(key, type).map {
                    FabricEntrypointContainerWrapper(it)
                }
            } catch (e: FabricEntrypointException) {
                throw EntrypointException(e)
            }
        }

        @JvmStatic
        val mappingResolver: MappingResolver
            get() {
                return FabricMappingResolverWrapper(FabricLoader.getInstance().mappingResolver)
            }

        @JvmStatic
        fun getModContainer(id: String): Optional<ModContainer> {
            return FabricLoader.getInstance().getModContainer(id).map { FabricModContainerWrapper(it) }
        }

        @JvmStatic
        val allMods: Collection<ModContainer>
            get() {
                return FabricLoader.getInstance().allMods.map { FabricModContainerWrapper(it) }
            }

        @JvmStatic
        fun isModLoaded(id: String): Boolean {
            return FabricLoader.getInstance().isModLoaded(id)
        }

        @JvmStatic
        fun getModContainer(clazz: Class<*>): Optional<ModContainer> {
            TODO("unimplemented, who does this")
        }

        @JvmStatic
        val isDevelopmentEnvironment: Boolean
            get() {
                return FabricLoader.getInstance().isDevelopmentEnvironment
            }

        @JvmStatic
        val gameInstance: Any?
            get() {
                return FabricLoader.getInstance().gameInstance
            }

        @JvmStatic
        val normalizedGameVersion: String
            get() {
                return FabricLoaderImpl.INSTANCE.gameProvider.normalizedGameVersion
            }

        @JvmStatic
        val rawGameVersion: String
            get() {
                return FabricLoaderImpl.INSTANCE.gameProvider.rawGameVersion
            }

        @JvmStatic
        val gameDir: Path
            get() {
                return FabricLoader.getInstance().gameDir
            }

        @JvmStatic
        val cacheDir: Path
            get() {
                return FabricLoader.getInstance().gameDir.resolve("cache")
            }

        @JvmStatic
        val globalCacheDir: Path
            get() {
                return FabricLoader.getInstance().gameDir.resolve("cache")
            }

        @JvmStatic
        val globalConfigDir: Path
            get() {
                return FabricLoader.getInstance().configDir
            }

        @JvmStatic
        fun globalDirsEnabled(): Boolean {
            return false // not yet
        }

        @JvmStatic
        fun getLaunchArguments(sanitize: Boolean): Array<out String> {
            return FabricLoader.getInstance().getLaunchArguments(sanitize)
        }

        @JvmStatic
        val objectShare: ObjectShare
            get() {
                return FabricLoader.getInstance().objectShare
            }

        @JvmStatic
        fun createModTable(): String {
            return FabricLoader.getInstance().allMods.joinToString("\n") { "${it.metadata.name} (${it.metadata.id}) - ${it.metadata.version.friendlyString}" }
        }
    }
}