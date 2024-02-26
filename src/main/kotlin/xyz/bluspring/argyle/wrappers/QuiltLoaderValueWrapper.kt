package xyz.bluspring.argyle.wrappers

import net.fabricmc.loader.api.metadata.CustomValue
import org.quiltmc.loader.api.LoaderValue

class QuiltLoaderValueWrapper(private val key: String, val wrapped: CustomValue) : LoaderValue {
    override fun type(): LoaderValue.LType {
        return LoaderValue.LType.valueOf(wrapped.type.name)
    }

    override fun location(): String {
        return key
    }

    override fun asObject(): LoaderValue.LObject? {
        val obj = wrapped.asObject ?: return null

        return FabricCustomValueObjectWrapper(key, obj)
    }

    override fun asArray(): LoaderValue.LArray? {
        val arr = wrapped.asArray ?: return null

        return FabricCustomValueArrayWrapper(key, arr)
    }

    override fun asString(): String? {
        return wrapped.asString
    }

    override fun asNumber(): Number? {
        return wrapped.asNumber
    }

    override fun asBoolean(): Boolean {
        return wrapped.asBoolean
    }
}