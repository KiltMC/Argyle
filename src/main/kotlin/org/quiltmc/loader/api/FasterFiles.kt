package org.quiltmc.loader.api

import java.io.IOException
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.attribute.FileAttribute
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream


object FasterFiles {
    @Throws(IOException::class)
    fun createFile(path: Path, vararg attrs: FileAttribute<*>): Path {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).createFile(path, *attrs)
        } else {
            Files.createFile(path, *attrs)
        }
    }

    @Throws(IOException::class)
    fun createDirectories(dir: Path, vararg attrs: FileAttribute<*>): Path {
        return if (dir.fileSystem is FasterFileSystem) {
            (dir.fileSystem as FasterFileSystem).createDirectories(dir, *attrs)
        } else {
            Files.createDirectories(dir, *attrs)
        }
    }

    @Throws(IOException::class)
    fun copy(source: Path, target: Path, vararg options: CopyOption): Path {
        return if (target.fileSystem is FasterFileSystem) {
            (target.fileSystem as FasterFileSystem).copy(source, target, *options)
        } else {
            Files.copy(source, target, *options)
        }
    }

    fun isSymbolicLink(path: Path): Boolean {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).isSymbolicLink(path)
        } else {
            Files.isSymbolicLink(path)
        }
    }

    fun isDirectory(path: Path, vararg options: LinkOption): Boolean {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).isDirectory(path, *options)
        } else {
            Files.isDirectory(path, *options)
        }
    }

    fun isRegularFile(path: Path, vararg options: LinkOption): Boolean {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).isRegularFile(path, options.toList().toTypedArray())
        } else {
            Files.isRegularFile(path, *options)
        }
    }

    fun exists(path: Path, vararg options: LinkOption): Boolean {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).exists(path, *options)
        } else {
            Files.exists(path, *options)
        }
    }

    fun notExists(path: Path, vararg options: LinkOption): Boolean {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).notExists(path, *options)
        } else {
            Files.notExists(path, *options)
        }
    }

    fun isReadable(path: Path): Boolean {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).isReadable(path)
        } else {
            Files.isReadable(path)
        }
    }

    fun isWritable(path: Path): Boolean {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).isWritable(path)
        } else {
            Files.isWritable(path)
        }
    }

    fun isExecutable(path: Path): Boolean {
        return if (path.fileSystem is FasterFileSystem) {
            (path.fileSystem as FasterFileSystem).isExecutable(path)
        } else {
            Files.isExecutable(path)
        }
    }

    @Throws(IOException::class)
    fun list(dir: Path): Stream<Path> {
        return if (dir.fileSystem is FasterFileSystem) {
            (dir.fileSystem as FasterFileSystem).list(dir)
        } else {
            Files.list(dir)
        }
    }

    @Throws(IOException::class)
    fun getChildren(dir: Path): Collection<Path> {
        return if (dir.fileSystem is FasterFileSystem) {
            (dir.fileSystem as FasterFileSystem).getChildren(dir)
        } else {
            getChildrenIndirect(dir)
        }
    }

    @Throws(IOException::class)
    fun getChildrenIndirect(dir: Path): Collection<Path> {
        return Collections.unmodifiableCollection(
            Files.list(dir).collect(Collectors.toCollection { ArrayList() })
        )
    }
}