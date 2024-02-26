package xyz.bluspring.argyle.impl

import org.quiltmc.loader.api.ModDependencyIdentifier

class ModDependencyIdentifierImpl(private val mavenGroup: String, private val id: String) : ModDependencyIdentifier {
    override fun mavenGroup(): String {
        return mavenGroup
    }

    override fun id(): String {
        return id
    }

    override fun toString(): String {
        return "$mavenGroup:$id"
    }
}