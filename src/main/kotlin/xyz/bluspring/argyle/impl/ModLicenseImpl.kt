package xyz.bluspring.argyle.impl

import org.quiltmc.loader.api.ModLicense

class ModLicenseImpl(private val name: String, private val id: String, private val url: String, private val description: String) : ModLicense {
    override fun name(): String {
        return name
    }

    override fun id(): String {
        return id
    }

    override fun url(): String {
        return url
    }

    override fun description(): String {
        return description
    }

    companion object {
        fun fromIdentifier(id: String): ModLicense? {
            return null // TODO: implement proper license getter
        }

        fun fromIdentifierOrDefault(id: String): ModLicense {
            return fromIdentifier(id) ?: ModLicenseImpl(id, id, "", "")
        }
    }
}