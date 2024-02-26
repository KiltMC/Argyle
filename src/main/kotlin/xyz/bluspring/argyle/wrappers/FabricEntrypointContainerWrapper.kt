package xyz.bluspring.argyle.wrappers

import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.entrypoint.EntrypointContainer
import net.fabricmc.loader.api.entrypoint.EntrypointContainer as FabricEntrypointContainer

class FabricEntrypointContainerWrapper<T>(val wrapped: FabricEntrypointContainer<T>) : EntrypointContainer<T> {
    override fun getEntrypoint(): T {
        return wrapped.entrypoint
    }

    override fun getProvider(): ModContainer {
        return FabricModContainerWrapper(wrapped.provider)
    }
}