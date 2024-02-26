package xyz.bluspring.argyle.impl

import net.fabricmc.loader.api.FabricLoader
import org.quiltmc.loader.api.Version
import org.quiltmc.loader.api.VersionConstraint
import xyz.bluspring.argyle.wrappers.FabricVersionWrapper
import java.util.*

class VersionConstraintImpl(private val version: Version, private val type: VersionConstraint.Type) : VersionConstraint {
    companion object {
        @JvmField
        val ANY = VersionConstraintImpl("", VersionConstraint.Type.ANY)
    }

    constructor(version: String, type: VersionConstraint.Type) : this(FabricVersionWrapper(net.fabricmc.loader.api.Version.parse(version)), type)

    override fun version(): String {
        return version.raw()
    }

    override fun type(): VersionConstraint.Type {
        return type
    }

    override fun matches(version: Version): Boolean {
        if (type == VersionConstraint.Type.ANY)
            return true

        if (version.raw() == "\${version}" && FabricLoader.getInstance().isDevelopmentEnvironment)
            return true

        return when (type) {
            VersionConstraint.Type.EQUALS -> version.compareTo(this.version) == 0
            VersionConstraint.Type.GREATER_THAN_OR_EQUAL -> version.compareTo(this.version) >= 0
            VersionConstraint.Type.LESSER_THAN_OR_EQUAL -> version.compareTo(this.version) <= 0
            VersionConstraint.Type.GREATER_THAN -> version.compareTo(this.version) > 0
            VersionConstraint.Type.LESSER_THAN -> version.compareTo(this.version) < 0
            else -> {
                return if (version.isSemantic() && this.version.isSemantic()) {
                    val inVer = version.semantic()
                    val cmpVer = this.version.semantic()

                    return when (type) {
                        VersionConstraint.Type.SAME_MAJOR ->
                            inVer.major() == cmpVer.major()
                        VersionConstraint.Type.SAME_MAJOR_AND_MINOR ->
                            inVer.major() == cmpVer.major() && inVer.minor() == inVer.minor()
                        VersionConstraint.Type.SAME_TO_NEXT_MAJOR ->
                            inVer.compareTo(this.version) >= 0 && inVer.major() == cmpVer.major()
                        VersionConstraint.Type.SAME_TO_NEXT_MINOR ->
                            inVer.compareTo(this.version) >= 0 && inVer.major() == cmpVer.major() && inVer.minor() == cmpVer.minor()
                        else -> throw IllegalStateException()
                    }
                } else {
                    false
                }
            }
        }
    }

    override fun toString(): String {
        return type.prefix() + version
    }

    override fun equals(other: Any?): Boolean {
        if (this == other)
            return true

        if (other == null || this.javaClass != other.javaClass)
            return false

        return this.version == (other as VersionConstraintImpl).version && this.type == other.type
    }

    override fun hashCode(): Int {
        return Objects.hash(this.version, this.type)
    }
}