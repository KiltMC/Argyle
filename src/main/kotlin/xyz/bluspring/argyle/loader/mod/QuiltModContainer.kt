package xyz.bluspring.argyle.loader.mod

import net.fabricmc.loader.impl.ModContainerImpl
import net.fabricmc.loader.impl.discovery.ModCandidateImpl

class QuiltModContainer(
    val candidate: ModCandidateImpl
) : ModContainerImpl(candidate) {
}