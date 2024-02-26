package xyz.bluspring.argyle.wrappers

import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.api.ModMetadata
import xyz.bluspring.argyle.loader.ArgyleLoader
import java.nio.file.Path
import net.fabricmc.loader.api.ModContainer as FabricModContainer

class FabricModContainerWrapper(val wrapped: FabricModContainer) : ModContainer {
    override fun metadata(): ModMetadata {
        return FabricModMetadataWrapper(wrapped.metadata)
    }

    override fun rootPath(): Path {
        return wrapped.rootPath
    }

    override fun getSourcePaths(): List<List<Path>> {
        return listOf(wrapped.rootPaths)
    }

    override fun getSourceType(): ModContainer.BasicSourceType {
        return ModContainer.BasicSourceType.NORMAL_FABRIC
    }

    override fun getClassLoader(): ClassLoader {
        return ArgyleLoader::class.java.classLoader
    }
}