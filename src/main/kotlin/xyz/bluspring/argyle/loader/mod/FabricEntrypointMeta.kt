package xyz.bluspring.argyle.loader.mod

import net.fabricmc.loader.impl.metadata.EntrypointMetadata

class FabricEntrypointMeta(
    private val value: String,
    private val adapter: String = "default"
) : EntrypointMetadata {
    override fun getAdapter(): String {
        return this.adapter
    }

    override fun getValue(): String {
        return this.value
    }
}