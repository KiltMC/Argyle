package xyz.bluspring.argyle.wrappers

import org.quiltmc.loader.api.Version
import org.quiltmc.loader.api.VersionInterval
import net.fabricmc.loader.api.metadata.version.VersionInterval as FabricVersionInterval

class FabricVersionIntervalWrapper(val wrapped: FabricVersionInterval) : VersionInterval {
    override val isSemantic: Boolean
        get() = wrapped.isSemantic

    override val min: Version?
        get() {
            if (wrapped.min == null)
                return null

            return FabricVersionWrapper(wrapped.min)
        }

    override val isMinInclusive: Boolean
        get() {
            return wrapped.isMinInclusive
        }

    override val max: Version?
        get() {
            if (wrapped.max == null)
                return null

            return FabricVersionWrapper(wrapped.max)
        }

    override val isMaxInclusive: Boolean
        get() {
            return wrapped.isMaxInclusive
        }
}