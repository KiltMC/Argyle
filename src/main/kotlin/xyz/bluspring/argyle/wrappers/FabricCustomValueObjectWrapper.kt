package xyz.bluspring.argyle.wrappers

import net.fabricmc.loader.api.metadata.CustomValue
import org.quiltmc.loader.api.LoaderValue

class FabricCustomValueObjectWrapper(val key: String, val wrapped: CustomValue.CvObject) : LoaderValue.LObject {
    override fun type(): LoaderValue.LType? {
        return LoaderValue.LType.valueOf(wrapped.type.name)
    }

    override fun location(): String? {
        return key
    }

    override fun asObject(): LoaderValue.LObject? {
        return this
    }

    override fun asArray(): LoaderValue.LArray? {
        TODO("Not yet implemented")
    }

    override fun asString(): String? {
        TODO("Not yet implemented")
    }

    override fun asNumber(): Number? {
        TODO("Not yet implemented")
    }

    override fun asBoolean(): Boolean {
        TODO("Not yet implemented")
    }

    override val entries: MutableSet<MutableMap.MutableEntry<String?, LoaderValue?>>
        get() {
            val map = mutableMapOf<String?, LoaderValue?>()
            for ((key, value) in wrapped) {
                if (value == null)
                    continue

                map[key] = QuiltLoaderValueWrapper(key, value)
            }

            return map.entries
        }

    override val keys: MutableSet<String?>
        get() = wrapped.map { it.key }.toMutableSet()
    override val size: Int
        get() = wrapped.size()
    override val values: MutableCollection<LoaderValue?>
        get() = wrapped.map { if (it.value == null) null else QuiltLoaderValueWrapper(it.key, it.value!!) }.toMutableSet()

    override fun clear() {
    }

    override fun containsKey(key: String?): Boolean {
        return wrapped.containsKey(key)
    }

    override fun containsValue(value: LoaderValue?): Boolean {
        return false
    }

    override fun get(key: String?): LoaderValue? {
        val value = wrapped.get(key) ?: return null
        return QuiltLoaderValueWrapper(key ?: return null, value)
    }

    override fun isEmpty(): Boolean {
        return wrapped.size() == 0
    }

    override fun put(key: String?, value: LoaderValue?): LoaderValue? {
        return null
    }

    override fun putAll(from: Map<out String?, LoaderValue?>) {

    }

    override fun remove(key: String?): LoaderValue? {
        return null
    }
}