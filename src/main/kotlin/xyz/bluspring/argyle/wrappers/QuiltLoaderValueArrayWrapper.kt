package xyz.bluspring.argyle.wrappers

import net.fabricmc.loader.api.metadata.CustomValue
import org.quiltmc.loader.api.LoaderValue

class QuiltLoaderValueArrayWrapper(val wrapped: LoaderValue.LArray) : CustomValue.CvArray {
    override fun iterator(): MutableIterator<CustomValue> {
        return wrapped.iterator()
    }

    override fun getType(): CustomValue.CvType {
        return CustomValue.CvType.ARRAY
    }

    override fun getAsObject(): CustomValue.CvObject? {
        return wrapped.asObject()?.toFabric()
    }

    override fun getAsArray(): CustomValue.CvArray? {
        return wrapped.asArray()?.toFabric()
    }

    override fun getAsString(): String? {
        return wrapped.asString()
    }

    override fun getAsNumber(): Number? {
        return wrapped.asNumber()
    }

    override fun getAsBoolean(): Boolean {
        return wrapped.asBoolean()
    }

    override fun size(): Int {
        return wrapped.size
    }

    override fun get(index: Int): CustomValue {
        return wrapped[index]
    }
}