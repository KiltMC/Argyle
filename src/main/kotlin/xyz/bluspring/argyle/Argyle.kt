package xyz.bluspring.argyle

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import xyz.bluspring.argyle.loader.ArgyleLoader

class Argyle : ModInitializer {
    override fun onInitialize() {

    }

    companion object {
        val loader = ArgyleLoader()
        val logger = LoggerFactory.getLogger(Argyle::class.java)
    }
}