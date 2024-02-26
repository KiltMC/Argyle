package xyz.bluspring.argyle.loader.mod

import net.fabricmc.loader.impl.metadata.NestedJarEntry
import org.quiltmc.loader.api.*
import java.nio.file.Path

class NestedQuiltMod(private val qFile: String,
                     id: String,
                     group: String,
                     version: Version,
                     name: String,
                     description: String,
                     licenses: Collection<ModLicense>,
                     contributors: Collection<ModContributor>,
                     contactInfos: Map<String, String>,
                     depends: Collection<ModDependency>,
                     breaks: Collection<ModDependency>,
                     icon: String,
                     values: Map<String, LoaderValue>,
                     nested: List<NestedQuiltMod>,
    paths: List<Path>,
    mixin: List<String>,
    intermediate: String
) : QuiltMod(
    id,
    group,
    version,
    name,
    description,
    licenses,
    contributors,
    contactInfos,
    depends,
    breaks,
    icon,
    values,
    nested,
    paths,
    mixin, intermediate
), NestedJarEntry {
    override fun getFile(): String {
        return qFile
    }
}