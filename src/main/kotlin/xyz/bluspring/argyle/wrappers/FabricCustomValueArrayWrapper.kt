package xyz.bluspring.argyle.wrappers

import net.fabricmc.loader.api.metadata.CustomValue
import org.quiltmc.loader.api.LoaderValue

class FabricCustomValueArrayWrapper(val key: String, val wrapped: CustomValue.CvArray) : LoaderValue.LArray {
    override fun type(): LoaderValue.LType? {
        return LoaderValue.LType.valueOf(wrapped.type.name)
    }

    override fun location(): String? {
        return key
    }

    override fun asObject(): LoaderValue.LObject? {
        TODO("Not yet implemented")
    }

    override fun asArray(): LoaderValue.LArray? {
        return FabricCustomValueArrayWrapper(key, wrapped)
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

    override val size: Int
        get() = wrapped.size()

    override fun add(element: LoaderValue): Boolean {
        return false
    }

    override fun add(index: Int, element: LoaderValue) {

    }

    override fun addAll(index: Int, elements: Collection<LoaderValue>): Boolean {
        return false
    }

    override fun addAll(elements: Collection<LoaderValue>): Boolean {
        return false
    }

    override fun clear() {

    }

    override fun contains(element: LoaderValue): Boolean {
        return wrapped.contains(QuiltLoaderValueWrapper(key, element))
    }

    override fun containsAll(elements: Collection<LoaderValue>): Boolean {
        return false
    }

    override fun get(index: Int): LoaderValue {
        return QuiltLoaderValueWrapper(key, wrapped[index])
    }

    override fun indexOf(element: LoaderValue): Int {
        return wrapped.indexOf(QuiltLoaderValueWrapper(key, element))
    }

    override fun isEmpty(): Boolean {
        return wrapped.size() == 0
    }

    override fun iterator(): MutableIterator<LoaderValue> {
        return wrapped.map { QuiltLoaderValueWrapper(key, it) }.toMutableList().listIterator()
    }

    override fun lastIndexOf(element: LoaderValue): Int {
        return wrapped.lastIndexOf(QuiltLoaderValueWrapper(key, element))
    }

    override fun listIterator(): MutableListIterator<LoaderValue> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): MutableListIterator<LoaderValue> {
        TODO("Not yet implemented")
    }

    override fun remove(element: LoaderValue): Boolean {
        return false
    }

    override fun removeAll(elements: Collection<LoaderValue>): Boolean {
        return false
    }

    override fun removeAt(index: Int): LoaderValue {
        throw IllegalStateException()
    }

    override fun retainAll(elements: Collection<LoaderValue>): Boolean {
        return false
    }

    override fun set(index: Int, element: LoaderValue): LoaderValue {
        throw IllegalStateException()
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<LoaderValue> {
        return wrapped.toList().subList(fromIndex, toIndex).map { QuiltLoaderValueWrapper(key, it) }.toMutableList()
    }
}