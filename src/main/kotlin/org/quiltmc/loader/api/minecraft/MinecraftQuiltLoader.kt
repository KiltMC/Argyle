package org.quiltmc.loader.api.minecraft

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader

class MinecraftQuiltLoader {
    companion object {
        @JvmStatic
        val environmentType: EnvType
            get() {
                return FabricLoader.getInstance().environmentType
            }
    }
}