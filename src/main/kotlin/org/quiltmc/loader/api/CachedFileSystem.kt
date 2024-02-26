package org.quiltmc.loader.api

import java.nio.file.LinkOption
import java.nio.file.Path

interface CachedFileSystem : FasterFileSystem {
    fun isPermanentlyReadOnly(): Boolean

    override fun exists(path: Path, vararg option: LinkOption): Boolean {
        return super.exists(path, *option)
    }

    companion object {
        @JvmStatic
        fun doesExist(path: Path, vararg options: LinkOption): Boolean {
            return FasterFiles.exists(path, *options)
        }
    }
}