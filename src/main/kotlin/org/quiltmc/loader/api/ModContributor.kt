package org.quiltmc.loader.api

interface ModContributor {
    fun name(): String
    @Deprecated("") fun role(): String
    fun roles(): Collection<String>
}