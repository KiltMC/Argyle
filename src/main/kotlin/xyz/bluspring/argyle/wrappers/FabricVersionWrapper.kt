package xyz.bluspring.argyle.wrappers

import org.quiltmc.loader.api.Version
import net.fabricmc.loader.api.Version as FabricVersion

class FabricVersionWrapper(val wrapped: FabricVersion) : Version {
    override fun raw(): String {
        return wrapped.friendlyString
    }

    override fun compareTo(other: Version?): Int {
        if (other == null)
            return 0

        return wrapped.compareTo(FabricVersion.parse(other.raw()))
    }
}