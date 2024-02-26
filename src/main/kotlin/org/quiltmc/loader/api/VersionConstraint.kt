package org.quiltmc.loader.api

import xyz.bluspring.argyle.impl.VersionConstraintImpl

interface VersionConstraint {
    companion object {
        @JvmStatic
        fun any(): VersionConstraint {
            return VersionConstraintImpl.ANY
        }
    }

    fun version(): String
    fun type(): Type
    fun matches(version: Version): Boolean

    enum class Type(private val prefix: String) {
        ANY("*"),
        EQUALS("="),
        GREATER_THAN_OR_EQUAL(">="),
        LESSER_THAN_OR_EQUAL("<="),
        GREATER_THAN(">"),
        LESSER_THAN("<"),
        SAME_MAJOR("^^"),
        SAME_MAJOR_AND_MINOR("~~"),
        SAME_TO_NEXT_MAJOR("^"),
        SAME_TO_NEXT_MINOR("~");

        fun prefix(): String {
            return prefix
        }
    }
}