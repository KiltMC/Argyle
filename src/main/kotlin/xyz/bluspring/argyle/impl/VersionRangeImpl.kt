package xyz.bluspring.argyle.impl

import org.quiltmc.loader.api.Version
import org.quiltmc.loader.api.VersionConstraint
import org.quiltmc.loader.api.VersionInterval
import org.quiltmc.loader.api.VersionRange
import xyz.bluspring.argyle.wrappers.FabricVersionIntervalWrapper
import java.util.*
import kotlin.collections.AbstractSet

class VersionRangeImpl(intervals: Collection<VersionInterval>) : AbstractSet<VersionInterval>(), VersionRange {
    constructor(interval: VersionInterval) : this(listOf(interval))

    private val intervals: SortedSet<VersionInterval>

    init {
        if (intervals.isEmpty())
            this.intervals = NONE.intervals
        else if (intervals.size == 1)
            this.intervals = TreeSet<VersionInterval>().apply {
                this.add(intervals.first())
            }
        else
            this.intervals = TreeSet<VersionInterval>().apply {
                val sorted = intervals.sorted()
                var last = sorted.first()

                for (i in 1 until intervals.size) {
                    val next = sorted[i]
                    val nextMin = next.min
                    val lastMax = last.max ?: break

                    if (nextMin == null || nextMin.compareTo(lastMax) < 0) {
                        val nextMax = next.max

                        if (nextMax == null) {
                            last = VersionInterval.of(last.min, last.isMinInclusive, null, false)
                            break
                        }

                        val cmp = nextMax.compareTo(lastMax)
                        val max: Version
                        val maxInclusive: Boolean

                        if (cmp == 0) {
                            max = lastMax
                            maxInclusive = next.isMaxInclusive || last.isMaxInclusive
                        } else {
                            max = if (cmp < 0) nextMax else lastMax
                            maxInclusive = (if (cmp < 0) next else last).isMaxInclusive
                        }

                        last = VersionInterval.of(last.min, last.isMinInclusive, max, maxInclusive)
                    } else {
                        this.add(last)
                        last = next
                    }
                }

                this.add(last)
            }
    }

    override val size: Int
        get() = intervals.size

    override fun combineMatchingBoth(other: VersionRange): VersionRange {
        val combined = mutableListOf<VersionInterval>()
        for (a in this) {
            for (b in other) {
                val merged = a.and(b)
                if (merged != null)
                    combined.add(merged)
            }
        }

        return VersionRangeImpl(other)
    }

    override fun convertToConstraints(): Collection<VersionConstraint> {
        val constraints = mutableListOf<VersionConstraint>()

        for (interval in this) {
            val min = interval.min
            val minInclusive = interval.isMinInclusive
            val max = interval.max
            val maxInclusive = interval.isMaxInclusive

            if (min == null && max == null) {
                constraints.add(VersionConstraint.any())
                continue
            }

            val maxBound = if (maxInclusive)
                VersionConstraint.Type.LESSER_THAN_OR_EQUAL
            else
                VersionConstraint.Type.LESSER_THAN

            if (min == null) {
                constraints.add(VersionConstraintImpl(max!!, maxBound))
                continue
            }

            val minBound = if (minInclusive)
                VersionConstraint.Type.GREATER_THAN_OR_EQUAL
            else
                VersionConstraint.Type.GREATER_THAN

            if (max == null) {
                constraints.add(VersionConstraintImpl(min, minBound))
                continue
            }

            constraints.add(VersionConstraintImpl(min, minBound))
            constraints.add(VersionConstraintImpl(max, maxBound))
        }

        return constraints
    }

    override fun subSet(fromElement: VersionInterval?, toElement: VersionInterval?): SortedSet<VersionInterval> {
        return VersionRangeImpl(intervals.subSet(fromElement, toElement))
    }

    override fun headSet(toElement: VersionInterval?): SortedSet<VersionInterval> {
        return VersionRangeImpl(intervals.headSet(toElement))
    }

    override fun tailSet(fromElement: VersionInterval?): SortedSet<VersionInterval> {
        return VersionRangeImpl(intervals.tailSet(fromElement))
    }

    override fun add(element: VersionInterval?): Boolean {
        return intervals.add(element)
    }

    override fun addAll(elements: Collection<VersionInterval>): Boolean {
        return intervals.addAll(elements)
    }

    override fun clear() {
        intervals.clear()
    }

    override fun iterator(): MutableIterator<VersionInterval> {
        return intervals.iterator()
    }

    override fun remove(element: VersionInterval?): Boolean {
        return intervals.remove(element)
    }

    override fun removeAll(elements: Collection<VersionInterval>): Boolean {
        return intervals.removeAll(elements.toSet())
    }

    override fun retainAll(elements: Collection<VersionInterval>): Boolean {
        return intervals.retainAll(elements.toSet())
    }

    override fun comparator(): Comparator<in VersionInterval>? {
        return intervals.comparator()
    }

    override fun first(): VersionInterval {
        return intervals.first()
    }

    override fun last(): VersionInterval {
        return intervals.last()
    }

    companion object {
        @JvmField
        val ANY = VersionRangeImpl(listOf(FabricVersionIntervalWrapper(net.fabricmc.loader.api.metadata.version.VersionInterval.INFINITE)))
        @JvmField
        val NONE = VersionRangeImpl(listOf())
    }
}