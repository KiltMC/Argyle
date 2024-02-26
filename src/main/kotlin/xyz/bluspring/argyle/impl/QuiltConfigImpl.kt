package xyz.bluspring.argyle.impl

import net.fabricmc.loader.api.FabricLoader
import org.quiltmc.config.api.ConfigEnvironment
import org.quiltmc.config.api.Serializer
import org.quiltmc.config.api.serializers.Json5Serializer
import org.quiltmc.config.api.serializers.TomlSerializer
import xyz.bluspring.argyle.Argyle


object QuiltConfigImpl {
    private var ENV: ConfigEnvironment? = null
    init {
        val serializerMap: MutableMap<String, Serializer> = LinkedHashMap<String, Serializer>()
        serializerMap["toml"] = TomlSerializer.INSTANCE
        serializerMap["json5"] = Json5Serializer.INSTANCE
        for (serializer in FabricLoader.getInstance().getEntrypoints("config_serializer", Serializer::class.java)) {
            val oldValue: Serializer? = serializerMap.put(serializer.fileExtension, serializer)
            if (oldValue != null) {
                Argyle.logger.warn("Replacing {} serializer {} with {}", serializer.fileExtension, oldValue::class.java, serializer::class.java)
            }
        }
        val globalConfigExtension = System.getProperty("loader.globalConfigExtension")
        val defaultConfigExtension = System.getProperty("loader.defaultConfigExtension")
        val serializers: Array<Serializer> = serializerMap.values.toTypedArray()
        if (globalConfigExtension != null && !serializerMap.containsKey(globalConfigExtension)) {
            throw RuntimeException("Cannot use file extension $globalConfigExtension globally: no matching serializer found")
        }
        if (defaultConfigExtension != null && !serializerMap.containsKey(defaultConfigExtension)) {
            throw RuntimeException("Cannot use file extension $defaultConfigExtension by default: no matching serializer found")
        }
        if (defaultConfigExtension == null) {
            ENV = ConfigEnvironment(
                FabricLoader.getInstance().configDir, globalConfigExtension,
                serializers[0]
            )
            for (i in 1 until serializers.size) {
                ENV!!.registerSerializer(serializers[i])
            }
        } else {
            ENV = ConfigEnvironment(
                FabricLoader.getInstance().configDir, globalConfigExtension,
                serializerMap[defaultConfigExtension]
            )
            for (serializer in serializers) {
                ENV!!.registerSerializer(serializer)
            }
        }
    }

    val configEnvironment: ConfigEnvironment?
        get() = ENV
}