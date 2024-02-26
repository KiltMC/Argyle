package org.quiltmc.loader.api.entrypoint

import net.fabricmc.loader.api.EntrypointException as FabricEntrypointException

open class EntrypointException(val wrapped: FabricEntrypointException) : RuntimeException(wrapped.message, wrapped.cause) {
    open fun getKey(): String {
        return wrapped.key
    }
}