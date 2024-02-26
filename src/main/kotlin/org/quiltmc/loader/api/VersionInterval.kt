package org.quiltmc.loader.api

import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.impl.util.version.VersionIntervalImpl
import xyz.bluspring.argyle.wrappers.FabricVersionIntervalWrapper
import net.fabricmc.loader.api.Version as FabricVersion
import net.fabricmc.loader.api.metadata.version.VersionInterval as FabricVersionInterval

interface VersionInterval : Comparable<VersionInterval> {
    val isSemantic: Boolean
    val min: Version?
    val isMinInclusive: Boolean
    val max: Version?
    val isMaxInclusive: Boolean

    fun isSatisfiedBy(version: Version): Boolean {
        if (version.raw() == "\${version}" && FabricLoader.getInstance().isDevelopmentEnvironment)
            return true

        val min = this.min
        if (min != null) {
            val cmp = min.compareTo(version)
            if (this.isMinInclusive)
                if (cmp > 0)
                    return false
            else if (cmp >= 0)
                return false
        }

        val max = this.max
        if (max != null) {
            val cmp = max.compareTo(version)
            if (this.isMaxInclusive)
                if (cmp < 0)
                    return false
                else if (cmp <= 0)
                    return false
        }

        return true
    }

    override fun compareTo(other: VersionInterval): Int {
        val minT = this.min
        val minO = other.min

        if ((minT == null) != (minO == null))
            return if (minT == null) -1 else 1

        if (minT != null) {
            val cmp = minT.compareTo(minO)
            if (cmp != 0)
                return cmp

            val incT = this.isMinInclusive
            val incO = other.isMinInclusive

            if (incT != incO)
                return if (incT) -1 else 1
        }

        val maxT = this.max
        val maxO = other.max

        if ((maxT == null) != (maxO == null))
            return if (maxT == null) 1 else -1

        if (maxT != null) {
            val cmp = maxT.compareTo(maxO)
            if (cmp != 0)
                return cmp

            val incT = this.isMaxInclusive
            val incO = other.isMaxInclusive

            if (incT != incO)
                return if (incT) 1 else -1
        }

        return 0
    }

    fun toVersionRange(): VersionRange {
        return VersionRange.ofInterval(this)
    }

    fun and(o: VersionInterval): VersionInterval? {
        return and(this, o)
    }

    fun or(o: VersionRange): VersionRange {
        return or(o, this)
    }

    fun or(o: VersionInterval): VersionRange {
        return VersionRange.ofIntervals(listOf(this, o))
    }

    fun not(): VersionRange {
        return not(this)
    }

    fun doesOverlap(o: VersionInterval): Boolean {
        return or(o).size == 1
    }

    fun mergeOverlapping(o: VersionInterval): VersionInterval {
        val range = or(o)
        if (range.size > 1)
            throw IllegalArgumentException("$this doesn't overlap with $o")

        return range.first()
    }

    fun toFabric(): FabricVersionInterval {
        return VersionIntervalImpl(
            if (this.min != null) FabricVersion.parse(this.min!!.raw()) else null,
            this.isMinInclusive,
            if (this.max != null) FabricVersion.parse(this.max!!.raw()) else null,
            this.isMaxInclusive
        )
    }

    companion object {
        @JvmStatic
        fun and(a: VersionInterval, b: VersionInterval): VersionInterval {
            val fabricA = a.toFabric()
            val fabricB = b.toFabric()
            return FabricVersionIntervalWrapper(FabricVersionInterval.and(fabricA, fabricB))
        }

        @JvmStatic
        fun and(a: VersionRange, b: VersionRange): VersionRange {
            return VersionRange.ofIntervals(FabricVersionInterval.and(
                a.map { it.toFabric() },
                b.map { it.toFabric() }
            ).map { FabricVersionIntervalWrapper(it) })
        }

        @JvmStatic
        fun or(a: VersionRange, b: VersionInterval): VersionRange {
            val list = mutableListOf<VersionInterval>()
            list.addAll(a)
            list.add(b)

            return VersionRange.ofIntervals(list)
        }

        @JvmStatic
        fun not(interval: VersionInterval): VersionRange {
            return VersionRange.ofIntervals(FabricVersionInterval.not(interval.toFabric()).map { FabricVersionIntervalWrapper(it) })
        }

        @JvmStatic
        fun of(min: Version?, minInclusive: Boolean, max: Version?, maxInclusive: Boolean): VersionInterval {
            return FabricVersionIntervalWrapper(VersionIntervalImpl(min?.toFabric(), minInclusive, max?.toFabric(), maxInclusive))
        }

        @JvmStatic
        fun ofExact(version: Version): VersionInterval {
            return FabricVersionIntervalWrapper(VersionIntervalImpl(version.toFabric(), true, version.toFabric(), true))
        }

        @JvmField
        val ALL = FabricVersionIntervalWrapper(FabricVersionInterval.INFINITE)
    }
}