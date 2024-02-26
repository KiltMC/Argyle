package xyz.bluspring.argyle.impl

import org.quiltmc.loader.api.ModContributor

class ModContributorImpl(val name: String, val roles: List<String>) : ModContributor {
    override fun name(): String {
        return name
    }

    override fun role(): String {
        return roles.firstOrNull() ?: "Developer"
    }

    override fun roles(): Collection<String> {
        return roles
    }
}