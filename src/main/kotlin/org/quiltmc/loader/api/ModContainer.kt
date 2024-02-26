package org.quiltmc.loader.api

import java.nio.file.Path

interface ModContainer {
    fun metadata(): ModMetadata
    fun rootPath(): Path
    fun getPath(file: String): Path {
        val root = rootPath()
        return root.resolve(file.replace("/", root.fileSystem.separator))
    }
    fun getSourcePaths(): List<List<Path>>
    fun getSourceType(): BasicSourceType
    fun getClassLoader(): ClassLoader

    enum class BasicSourceType {
        NORMAL_QUILT,
        NORMAL_FABRIC,
        BUILTIN,
        OTHER
    }
}