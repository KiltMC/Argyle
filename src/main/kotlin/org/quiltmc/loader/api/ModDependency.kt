package org.quiltmc.loader.api

interface ModDependency {
    fun shouldIgnore(): Boolean

    interface Only : ModDependency {
        fun id(): ModDependencyIdentifier
        fun versions(): Collection<VersionConstraint> {
            return versionRange().convertToConstraints()
        }
        fun versionRange(): VersionRange
        fun reason(): String
        fun unless(): ModDependency?
        fun optional(): Boolean
        fun matches(version: Version): Boolean {
            return versionRange().isSatisfiedBy(version)
        }
    }

    interface Any : Collection<Only>, ModDependency {
        override fun shouldIgnore(): Boolean {
            for (dep in this) {
                if (!dep.shouldIgnore())
                    return false
            }

            return true
        }
    }

    interface All : Collection<Only>, ModDependency {
        override fun shouldIgnore(): Boolean {
            for (dep in this) {
                if (!dep.shouldIgnore())
                    return false
            }

            return true
        }
    }
}