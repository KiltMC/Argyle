package xyz.bluspring.argyle.loader.mod

import net.fabricmc.loader.api.metadata.CustomValue

class CustomStringValue(private val stringValue: String) : CustomValue {
    override fun getType(): CustomValue.CvType {
        return CustomValue.CvType.STRING
    }

    override fun getAsObject(): CustomValue.CvObject {
        TODO("Not yet implemented")
    }

    override fun getAsArray(): CustomValue.CvArray {
        TODO("Not yet implemented")
    }

    override fun getAsString(): String {
        return stringValue
    }

    override fun getAsNumber(): Number {
        TODO("Not yet implemented")
    }

    override fun getAsBoolean(): Boolean {
        TODO("Not yet implemented")
    }
}