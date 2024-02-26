package org.quiltmc.loader.api

import xyz.bluspring.argyle.impl.VersionRangeImpl
import java.util.*

interface VersionRange : SortedSet<VersionInterval> {
    companion object {
        @JvmStatic
        fun ofInterval(interval: VersionInterval): VersionRange {
            if (interval.min == null && interval.max == null)
                return ANY

            return VersionRangeImpl(interval)
        }

        @JvmStatic
        fun ofIntervals(intervals: Collection<VersionInterval>): VersionRange {
            return VersionRangeImpl(intervals.toList())
        }

        @JvmStatic
        fun ofExact(version: Version): VersionRange {
            return VersionRangeImpl(VersionInterval.ofExact(version))
        }

        @JvmStatic
        fun ofInterval(min: Version, minInclusive: Boolean, max: Version, maxInclusive: Boolean): VersionRange {
            return VersionRangeImpl(VersionInterval.of(min, minInclusive, max, maxInclusive))
        }

        @JvmField
        val NONE = VersionRangeImpl.NONE
        @JvmField
        val ANY = VersionRangeImpl.ANY
    }

    fun combineMatchingBoth(other: VersionRange): VersionRange
    fun convertToConstraints(): Collection<VersionConstraint>

    fun isSatisfiedBy(version: Version): Boolean {
        for (interval in this) {
            if (interval.isSatisfiedBy(version))
                return true
        }

        return false
    }

    override fun subSet(fromElement: VersionInterval?, toElement: VersionInterval?): SortedSet<VersionInterval>
    override fun headSet(toElement: VersionInterval?): SortedSet<VersionInterval>
    override fun tailSet(fromElement: VersionInterval?): SortedSet<VersionInterval>
}