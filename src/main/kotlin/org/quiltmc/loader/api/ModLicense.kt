package org.quiltmc.loader.api

import xyz.bluspring.argyle.impl.ModLicenseImpl

interface ModLicense {
    fun name(): String
    fun id(): String
    fun url(): String
    fun description(): String

    companion object {
        @JvmStatic
        fun fromIdentifier(id: String): ModLicense? {
            return ModLicenseImpl.fromIdentifier(id)
        }

        fun fromIdentifierOrDefault(id: String): ModLicense {
            return ModLicenseImpl.fromIdentifierOrDefault(id)
        }
    }
}