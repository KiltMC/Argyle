package org.quiltmc.loader.api

import net.fabricmc.loader.api.Version as FabricVersion

interface Version {
    fun raw(): String

    fun isSemantic(): Boolean {
        return this is Semantic
    }

    fun semantic(): Semantic {
        return this as Semantic
    }

    fun compareTo(other: Version?): Int

    fun toFabric(): FabricVersion {
        return FabricVersion.parse(this.raw())
    }

    interface Raw : Version, Comparable<Version> {
    }

    interface Semantic : Version, Comparable<Semantic> {
        companion object {
            @JvmField
            val EMPTY_BUT_PRESENT_PRERELEASE = String()
        }

        fun versionComponentCount(): Int
        fun versionComponent(index: Int): Int
        fun versionComponents(): Array<Int>

        fun major(): Int {
            return versionComponent(0)
        }

        fun minor(): Int {
            return versionComponent(1)
        }

        fun patch(): Int {
            return versionComponent(2)
        }

        fun preRelease(): String

        fun isPreReleasePresent(): Boolean {
            return preRelease().isNotEmpty() || preRelease() == EMPTY_BUT_PRESENT_PRERELEASE
        }

        fun buildMetadata(): String
        override fun compareTo(other: Semantic): Int
    }
}