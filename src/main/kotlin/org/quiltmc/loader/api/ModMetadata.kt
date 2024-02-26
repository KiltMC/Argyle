package org.quiltmc.loader.api

interface ModMetadata {
    fun id(): String
    fun group(): String
    fun version(): Version
    fun name(): String
    fun description(): String
    fun licenses(): Collection<ModLicense>
    fun contributors(): Collection<ModContributor>
    fun getContactInfo(key: String): String?
    fun contactInfo(): Map<String, String>
    fun depends(): Collection<ModDependency>
    fun breaks(): Collection<ModDependency>

    fun provides(): Collection<out ProvidedMod> {
        return emptyList()
    }

    interface ProvidedMod {
        fun group(): String
        fun id(): String
        fun version(): Version
    }

    fun icon(size: Int): String?
    fun containsValue(key: String): Boolean
    fun value(key: String): LoaderValue?
    fun values(): Map<String, LoaderValue>
}