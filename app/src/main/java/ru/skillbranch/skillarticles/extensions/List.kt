package ru.skillbranch.skillarticles.extensions

import kotlin.math.max
import kotlin.math.min

fun List<Pair<Int, Int>>.groupByBounds(bounds: List<Pair<Int, Int>>) : List<List<Pair<Int, Int>>> {
    val result = mutableListOf<List<Pair<Int, Int>>>()
    bounds.forEach { (lowInterval, highInterval) ->
        result.add(this
            .filter { (lowFound, highFound) ->
                lowFound < highInterval && highFound > lowInterval
            }.map { (low, high) -> Pair(max(lowInterval, low), min(highInterval, high)) })

    }
    return result
}