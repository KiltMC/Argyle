package xyz.bluspring.argyle.wrappers

import net.fabricmc.loader.api.metadata.CustomValue
import org.quiltmc.loader.api.LoaderValue

class QuiltLoaderValueObjectWrapper(val wrapped: LoaderValue.LObject) : CustomValue.CvObject {
    override fun iterator(): MutableIterator<MutableMap.MutableEntry<String?, LoaderValue?>> {
        return wrapped.iterator()
    }

    override fun getType(): CustomValue.CvType {
        return CustomValue.CvType.valueOf(wrapped.type()?.name ?: "NULL")
    }

    override fun getAsObject(): CustomValue.CvObject {
        return QuiltLoaderValueObjectWrapper(wrapped)
    }

    override fun getAsArray(): CustomValue.CvArray {
        TODO("Not yet implemented")
    }

    override fun getAsString(): String {
        TODO("Not yet implemented")
    }

    override fun getAsNumber(): Number {
        TODO("Not yet implemented")
    }

    override fun getAsBoolean(): Boolean {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        return wrapped.size
    }

    override fun containsKey(key: String?): Boolean {
        return wrapped.containsKey(key)
    }

    override fun get(key: String?): CustomValue? {
        return wrapped[key]
    }
}