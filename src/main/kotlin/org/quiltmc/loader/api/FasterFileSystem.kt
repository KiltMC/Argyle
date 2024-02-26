package org.quiltmc.loader.api

import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.attribute.FileAttribute
import java.util.stream.Stream

interface FasterFileSystem {
    fun createFile(path: Path, vararg attrs: FileAttribute<*>): Path {
        return Files.createFile(path, *attrs)
    }

    fun createDirectories(path: Path, vararg attrs: FileAttribute<*>): Path {
        return Files.createDirectories(path, *attrs)
    }

    fun copy(source: Path, target: Path, vararg options: CopyOption): Path {
        return Files.copy(source, target, *options)
    }

    fun isSymbolicLink(path: Path): Boolean {
        return Files.isSymbolicLink(path)
    }

    fun isDirectory(path: Path, vararg options: LinkOption): Boolean {
        return Files.isDirectory(path, *options)
    }

    fun isRegularFile(path: Path, options: Array<LinkOption>): Boolean {
        return Files.isRegularFile(path, *options)
    }

    fun exists(path: Path, vararg options: LinkOption): Boolean {
        return Files.exists(path, *options)
    }

    fun notExists(path: Path, vararg options: LinkOption): Boolean {
        return Files.notExists(path, *options)
    }

    fun isReadable(path: Path): Boolean {
        return Files.isReadable(path)
    }

    fun isWritable(path: Path): Boolean {
        return Files.isWritable(path)
    }

    fun isExecutable(path: Path): Boolean {
        return Files.isExecutable(path)
    }

    fun list(dir: Path): Stream<Path> {
        return Files.list(dir)
    }

    fun getChildren(dir: Path): Collection<Path> {
        return FasterFiles.getChildrenIndirect(dir)
    }
}