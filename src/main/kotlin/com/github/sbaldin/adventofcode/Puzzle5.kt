package com.github.sbaldin.adventofcode

import java.util.*

typealias Seeds = List<List<Long>>
typealias AlmanacSection = Pair<String, SortedSet<SectionMapping>>
typealias SectionMapping = Triple<Long, Long, Long>

val AlmanacSection.name: String get() = this.first
val AlmanacSection.sectionMappings: SortedSet<SectionMapping> get() = this.second

val SectionMapping.destination: Long get() = this.first
val SectionMapping.source: Long get() = this.second
val SectionMapping.length: Long get() = this.third

// make sure that our seed foll into section range
private fun SectionMapping.contains(input: Long): Boolean {
    return source <= input && input < (source + length)
}

fun AlmanacSection.map(input: Long): Long {
    // find the section that contains our input
    val mapping = sectionMappings.firstOrNull { it.contains(input) } ?: return input
    /* find the shift factor example
     * seed-to-soil map:
     * 52 50 48
     *
     * so 50 shifts two times to the right and became 52, 51 -> 53 and so on
     * shift factor here is 2
     */
    val shiftFactor = mapping.destination - mapping.source
    return input + shiftFactor
}
typealias SeedSet = Pair<Long, Long>

val SeedSet.source: Long get() = this.second
val SeedSet.length: Long get() = this.second

fun runPuzzleDay5Part1(input: List<String>): Any {

    lateinit var seeds: Seeds
    val almanac = mutableListOf<AlmanacSection>()
    lateinit var almanacSection: AlmanacSection

    input.forEachIndexed { index, s ->
        if (index == 0) {
            val (_, seedsString) = s.split(": ")
            //PIZDEC cac mnogo semian

            seeds = seedsString.split(" ").map { it.toLong() }.chunked(2)

        } else if (s.isNotEmpty()) {
            if (s.contains("map")) {
                almanacSection =
                    AlmanacSection(s.split(" ").first(), sortedSetOf<SectionMapping>(compareBy { it.source }))
                almanac.add(almanacSection)
            } else {
                val (first, second, third) = s.split(" ").map { it.toLong() }
                val diapason = SectionMapping(first, second, third)
                almanacSection.sectionMappings.add(diapason)
            }
        }
    }

    println(seeds.joinToString(" "))

    almanac.forEach {
        println(it.name)
        it.sectionMappings.forEach {
            println("${it.destination} ${it.source} ${it.length} ")
        }
    }
    return seeds.minOf { seedSet ->
        // To iterate through the section we will use fold it will invoke map function on each section
        // and result of current section will be input of next one
        // example:
        // map seed-to-soil
        // map soil-to-fertilizer
        // map fertilizer-to-water
        // map water-to-light
        // map light-to-temperature
        // map temperature-to-humidity
        // map humidity-to-location
        println("Process ${seedSet.joinToString()}")


        val (start, length) = seedSet
        var minLocation = Long.MAX_VALUE
        for (seed in start until start + length) {
            val location = almanac.fold(seed) { input, section -> section.map(input) }
            if (location < minLocation) {
                minLocation = location
            }

        }
        println("Done ${seedSet.joinToString()}")
        minLocation
    }
}

fun runPuzzleDay5Part2(input: List<String>): Any {
    lateinit var seeds: Seeds
    val almanac = mutableListOf<AlmanacSection>()
    lateinit var almanacSection: AlmanacSection

    input.forEachIndexed { index, s ->
        if (index == 0) {
            val (_, seedsString) = s.split(": ")
            //PIZDEC cac mnogo semian

            seeds = seedsString.split(" ").map { it.toLong() }.chunked(2)

        } else if (s.isNotEmpty()) {
            if (s.contains("map")) {
                almanacSection =
                    AlmanacSection(s.split(" ").first(), sortedSetOf<SectionMapping>(compareBy { it.source }))
                almanac.add(almanacSection)
            } else {
                val (first, second, third) = s.split(" ").map { it.toLong() }
                val diapason = SectionMapping(first, second, third)
                almanacSection.sectionMappings.add(diapason)
            }
        }
    }

    println(seeds.joinToString(" "))

    almanac.forEach {
        println(it.name)
        it.sectionMappings.forEach {
            println("${it.destination} ${it.source} ${it.length} ")
        }
    }
    return 0;
}
