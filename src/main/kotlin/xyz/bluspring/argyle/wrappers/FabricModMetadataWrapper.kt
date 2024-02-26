package xyz.bluspring.argyle.wrappers

import org.quiltmc.loader.api.*
import xyz.bluspring.argyle.impl.ModContributorImpl
import xyz.bluspring.argyle.impl.ModLicenseImpl
import kotlin.jvm.optionals.getOrNull
import net.fabricmc.loader.api.metadata.ModMetadata as FabricModMetadata

class FabricModMetadataWrapper(val wrapped: FabricModMetadata) : ModMetadata {
    private val contributors = mutableListOf<ModContributor>().apply {
        for (contributor in wrapped.authors) {
            this.add(ModContributorImpl(contributor.name, listOf("Author")))
        }

        for (contributor in wrapped.contributors) {
            this.add(ModContributorImpl(contributor.name, listOf("Contributor")))
        }
    }

    override fun id(): String {
        return wrapped.id
    }

    override fun group(): String {
        return wrapped.id
    }

    override fun version(): Version {
        return FabricVersionWrapper(wrapped.version)
    }

    override fun name(): String {
        return wrapped.name
    }

    override fun description(): String {
        return wrapped.description
    }

    override fun licenses(): Collection<ModLicense> {
        return wrapped.license.map { ModLicenseImpl.fromIdentifierOrDefault(it) }
    }

    override fun contributors(): Collection<ModContributor> {
        return contributors
    }

    override fun getContactInfo(key: String): String? {
        return wrapped.contact[key]?.getOrNull()
    }

    override fun contactInfo(): Map<String, String> {
        return wrapped.contact.asMap()
    }

    override fun depends(): Collection<ModDependency> {
        TODO("Not yet implemented")
    }

    override fun breaks(): Collection<ModDependency> {
        TODO("Not yet implemented")
    }

    override fun icon(size: Int): String? {
        return wrapped.getIconPath(size)?.getOrNull()
    }

    override fun containsValue(key: String): Boolean {
        return wrapped.containsCustomValue(key)
    }

    override fun value(key: String): LoaderValue? {
        TODO("Not yet implemented")
    }

    override fun values(): Map<String, LoaderValue> {
        TODO("Not yet implemented")
    }
}