package xyz.bluspring.argyle.loader.mod

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.Version
import net.fabricmc.loader.api.metadata.*
import net.fabricmc.loader.impl.metadata.EntrypointMetadata
import net.fabricmc.loader.impl.metadata.LoaderModMetadata
import net.fabricmc.loader.impl.metadata.NestedJarEntry
import java.util.*

class FabricModMetadata(private val mod: QuiltMod) : ModMetadata, LoaderModMetadata {
    private val customValues = mutableMapOf<String, CustomValue>(
        "name" to CustomStringValue(mod.name()),
        "description" to CustomStringValue(description),
        "icon" to CustomStringValue(mod.icon(16) ?: "")
    )

    override fun getType(): String {
        return "fabric"
    }

    override fun getId(): String {
        return mod.id()
    }

    override fun getProvides(): MutableCollection<String> {
        return mod.nested.map {
            it.id()
        }.toMutableList()
    }

    override fun getVersion(): Version {
        return mod.version().toFabric()
    }

    override fun getEnvironment(): ModEnvironment {
        return ModEnvironment.UNIVERSAL // TODO: add support for handling this in QMJ
    }

    override fun getDependencies(): MutableCollection<ModDependency> {
        return mutableListOf() // Already handled by Kilt
    }

    override fun getName(): String {
        return mod.name()
    }

    override fun getDescription(): String {
        return mod.description()
    }

    override fun getAuthors(): MutableCollection<Person> {
        return mutableListOf<Person>().apply {
            mod.contributors().forEach {
                this.add(object : Person {
                    override fun getName(): String {
                        return it.name()
                    }

                    override fun getContact(): ContactInformation {
                        return object : ContactInformation {
                            override fun get(key: String?): Optional<String> {
                                return Optional.empty()
                            }

                            override fun asMap(): MutableMap<String, String> {
                                return mutableMapOf()
                            }
                        }
                    }

                })
            }
        }
    }

    override fun getContributors(): MutableCollection<Person> {
        return mutableListOf()
    }

    override fun getContact(): ContactInformation {
        return object : ContactInformation {
            override fun get(key: String?): Optional<String> {
                return Optional.empty()
            }

            override fun asMap(): MutableMap<String, String> {
                return mutableMapOf()
            }
        }
    }

    override fun getLicense(): MutableCollection<String> {
        return mod.licenses().map { it.name() }.toMutableList()
    }

    override fun getIconPath(size: Int): Optional<String> {
        return Optional.ofNullable(mod.icon(size))
    }

    override fun containsCustomValue(key: String?): Boolean {
        return mod.containsValue(key!!)
    }

    override fun getCustomValue(key: String): CustomValue? {
        return mod.value(key)
    }

    override fun getCustomValues(): MutableMap<String, CustomValue> {
        return mod.values().toMutableMap()
    }

    override fun containsCustomElement(key: String?): Boolean {
        return mod.values().contains(key)
    }

    override fun loadsInEnvironment(type: EnvType?): Boolean {
        return true
    }

    override fun getEntrypoints(type: String?): MutableList<EntrypointMetadata> {
        return mutableListOf()
    }

    override fun getEntrypointKeys(): MutableCollection<String> {
        return mutableListOf()
    }

    override fun getSchemaVersion(): Int {
        return 1
    }

    override fun getLanguageAdapterDefinitions(): MutableMap<String, String> {
        return mutableMapOf()
    }

    override fun getJars(): MutableCollection<NestedJarEntry> {
        return mod.nested.toMutableList()
    }

    override fun getMixinConfigs(type: EnvType?): MutableCollection<String> {
        return mutableListOf()
    }

    override fun getAccessWidener(): String? {
        return mod.accessWidener
    }

    override fun getOldInitializers(): MutableCollection<String> {
        return mutableListOf()
    }

    override fun emitFormatWarnings() {
    }

    override fun setVersion(version: Version?) {
    }

    override fun setDependencies(dependencies: MutableCollection<ModDependency>?) {
    }
}