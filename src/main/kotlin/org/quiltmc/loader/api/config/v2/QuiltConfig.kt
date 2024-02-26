package org.quiltmc.loader.api.config.v2

import org.quiltmc.config.api.Config
import org.quiltmc.config.api.ReflectiveConfig
import org.quiltmc.config.impl.ConfigImpl
import org.quiltmc.config.implementor_api.ConfigFactory
import xyz.bluspring.argyle.impl.QuiltConfigImpl
import java.nio.file.Path
import java.nio.file.Paths


object QuiltConfig {
    @JvmStatic
    fun create(family: String, id: String, path: Path, vararg creators: Config.Creator): Config {
        return ConfigImpl.create(QuiltConfigImpl.configEnvironment, family, id, path, *creators)
    }

    @JvmStatic
    fun create(family: String, id: String, vararg creators: Config.Creator): Config {
        return create(family, id, Paths.get(""), *creators)
    }

    @JvmStatic
    fun <C : ReflectiveConfig> create(
        family: String,
        id: String,
        path: Path,
        before: Config.Creator,
        configCreatorClass: Class<C>,
        after: Config.Creator
    ): C {
        return ConfigFactory.create(
            QuiltConfigImpl.configEnvironment,
            family,
            id,
            path,
            before,
            configCreatorClass,
            after
        )
    }

    @JvmStatic
    fun <C : ReflectiveConfig> create(
        family: String,
        id: String,
        path: Path,
        before: Config.Creator,
        configCreatorClass: Class<C>
    ): C {
        return create(family, id, path, before, configCreatorClass) { builder -> }
    }

    @JvmStatic
    fun <C : ReflectiveConfig> create(
        family: String,
        id: String,
        path: Path,
        configCreatorClass: Class<C>,
        after: Config.Creator
    ): C {
        return create(family, id, path, { builder -> }, configCreatorClass, after)
    }

    @JvmStatic
    fun <C : ReflectiveConfig> create(family: String, id: String, path: Path, configCreatorClass: Class<C>): C {
        return create(family, id, path, { builder -> }, configCreatorClass, { builder -> })
    }

    @JvmStatic
    fun <C : ReflectiveConfig> create(
        family: String,
        id: String,
        before: Config.Creator,
        configCreatorClass: Class<C>,
        after: Config.Creator
    ): C {
        return create(family, id, Paths.get(""), before, configCreatorClass, after)
    }

    @JvmStatic
    fun <C : ReflectiveConfig> create(
        family: String,
        id: String,
        before: Config.Creator,
        configCreatorClass: Class<C>
    ): C {
        return create(family, id, Paths.get(""), before, configCreatorClass) { builder -> }
    }

    @JvmStatic
    fun <C : ReflectiveConfig> create(
        family: String,
        id: String,
        configCreatorClass: Class<C>,
        after: Config.Creator
    ): C {
        return create(family, id, Paths.get(""), { builder -> }, configCreatorClass, after)
    }

    @JvmStatic
    fun <C : ReflectiveConfig> create(family: String, id: String, configCreatorClass: Class<C>): C {
        return create(family, id, Paths.get(""), { builder -> }, configCreatorClass, { builder -> })
    }
}