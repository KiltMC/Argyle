package xyz.bluspring.argyle.loader.mod

import net.fabricmc.loader.impl.ModContainerImpl
import net.fabricmc.loader.impl.discovery.ModCandidate

class QuiltModContainer(
    val candidate: ModCandidate
) : ModContainerImpl(candidate) {
}