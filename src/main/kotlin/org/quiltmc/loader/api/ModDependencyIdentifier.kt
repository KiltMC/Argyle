package org.quiltmc.loader.api

import xyz.bluspring.argyle.impl.ModDependencyIdentifierImpl

interface ModDependencyIdentifier {
    fun mavenGroup(): String
    fun id(): String

    companion object {
        fun of(mavenGroup: String, id: String): ModDependencyIdentifier {
            return ModDependencyIdentifierImpl(mavenGroup, id)
        }
    }
}