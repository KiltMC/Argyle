package org.quiltmc.loader.api

import net.fabricmc.loader.api.metadata.CustomValue
import xyz.bluspring.argyle.wrappers.QuiltLoaderValueArrayWrapper
import xyz.bluspring.argyle.wrappers.QuiltLoaderValueObjectWrapper

interface LoaderValue : CustomValue {
    fun type(): LType?
    fun location(): String?

    fun asObject(): LObject?

    fun asArray(): LArray?
    fun asString(): String?
    fun asNumber(): Number?
    fun asBoolean(): Boolean

    interface LObject : LoaderValue, MutableMap<String?, LoaderValue?> {
        fun toFabric(): CustomValue.CvObject {
            return QuiltLoaderValueObjectWrapper(this)
        }
    }

    interface LArray : LoaderValue, MutableList<LoaderValue> {
        fun toFabric(): CustomValue.CvArray {
            return QuiltLoaderValueArrayWrapper(this)
        }
    }

    enum class LType {
        OBJECT,
        ARRAY,
        STRING,
        NUMBER,
        BOOLEAN,
        NULL
    }

    override fun getAsArray(): CustomValue.CvArray? {
        return this.asArray()?.toFabric()
    }

    override fun getAsBoolean(): Boolean {
        return this.asBoolean()
    }

    override fun getAsNumber(): Number? {
        return this.asNumber()
    }

    override fun getAsObject(): CustomValue.CvObject? {
        return this.asObject()?.toFabric()
    }

    override fun getAsString(): String? {
        return this.asString()
    }

    override fun getType(): CustomValue.CvType {
        return CustomValue.CvType.valueOf(this.type()?.name ?: "NULL")
    }
}