package xyz.bluspring.argyle.loader.mod

import org.quiltmc.loader.api.*
import java.nio.file.Path

open class QuiltMod(
    private val id: String,
    private val group: String,
    private val version: Version,
    private val name: String,
    private val description: String,
    private val licenses: Collection<ModLicense>,
    private val contributors: Collection<ModContributor>,
    private val contactInfos: Map<String, String>,
    private val depends: Collection<ModDependency>,
    private val breaks: Collection<ModDependency>,
    private val icon: String,
    private val values: Map<String, LoaderValue>,
    val nested: List<NestedQuiltMod>,
    val paths: List<Path>,
    val mixin: List<String>,
    val intermediate: String
) : ModMetadata {
    lateinit var container: QuiltModContainer
    val entrypoints = mutableMapOf<String, MutableList<FabricEntrypointMeta>>()

    override fun id(): String {
        return id
    }

    override fun group(): String {
        return group
    }

    override fun version(): Version {
        return version
    }

    override fun name(): String {
        return name
    }

    override fun description(): String {
        return description
    }

    override fun licenses(): Collection<ModLicense> {
        return licenses
    }

    override fun contributors(): Collection<ModContributor> {
        return contributors
    }

    override fun getContactInfo(key: String): String? {
        return contactInfos[key]
    }

    override fun contactInfo(): Map<String, String> {
        return contactInfos
    }

    override fun depends(): Collection<ModDependency> {
        return depends
    }

    override fun breaks(): Collection<ModDependency> {
        return breaks
    }

    override fun icon(size: Int): String? {
        return icon
    }

    override fun containsValue(key: String): Boolean {
        return values.contains(key)
    }

    override fun value(key: String): LoaderValue? {
        return values[key]
    }

    override fun values(): Map<String, LoaderValue> {
        return values
    }
}