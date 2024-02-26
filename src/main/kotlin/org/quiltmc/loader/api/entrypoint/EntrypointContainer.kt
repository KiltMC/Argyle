package org.quiltmc.loader.api.entrypoint

import org.quiltmc.loader.api.ModContainer

interface EntrypointContainer<T> {
    fun getEntrypoint(): T
    fun getProvider(): ModContainer
}