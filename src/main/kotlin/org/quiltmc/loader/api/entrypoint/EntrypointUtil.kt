package org.quiltmc.loader.api.entrypoint

import net.fabricmc.loader.api.FabricLoader
import org.quiltmc.loader.api.ModContainer
import xyz.bluspring.argyle.Argyle
import xyz.bluspring.argyle.wrappers.FabricModContainerWrapper
import java.util.function.BiConsumer
import java.util.function.Consumer

object EntrypointUtil {
    @JvmStatic
    fun <T> invoke(name: String, type: Class<T>, invoker: Consumer<in T>) {
        FabricLoader.getInstance().invokeEntrypoints(name, type, invoker)
    }

    @JvmStatic
    fun <T> invoke(name: String, type: Class<T>, invoker: BiConsumer<T, ModContainer>) {
        FabricLoader.getInstance().invokeEntrypoints(name, type) {
            invoker.accept(it, FabricModContainerWrapper(FabricLoader.getInstance().getModContainer("argyle").get()))
        }
    }

    @JvmStatic
    fun <T> invokeContainer(name: String, type: Class<T>, invoker: Consumer<EntrypointContainer<T>>) {
        FabricLoader.getInstance().invokeEntrypoints(name, type) {
            Argyle.logger.error("hey dipshit")
        }
    }
}