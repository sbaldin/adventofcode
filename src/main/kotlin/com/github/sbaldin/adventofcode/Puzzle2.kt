package com.github.sbaldin.adventofcode

import kotlin.math.max

typealias CubeSet = Triple<Int, Int, Int>

val CubeSet.red: Int get() = this.first
val CubeSet.green: Int get() = this.second
val CubeSet.blue: Int get() = this.third

typealias Game = Pair<Int, List<CubeSet>>

val Game.number: Int get() = this.first
val Game.cubeSets: List<CubeSet> get() = this.second


fun runPuzzleDay2Part1(input: List<String>): Any {
    // bag should contain only 12 red cubes, 13 green cubes, and 14 blue cubes
    val expectedCubesInBag = CubeSet(12, 13, 14)

    return input.map {
        it.toGame()
    }.filter { game ->
        game.cubeSets.none { it.red > expectedCubesInBag.red || it.blue > expectedCubesInBag.blue || it.green > expectedCubesInBag.green }
    }.sumOf { game ->
        game.number
    }
}

fun runPuzzleDay2Part2(input: List<String>): Any {
    return input.sumOf { line ->
        var maxRed = 1
        var maxGreen = 1
        var maxBlue = 1

        line.toGame().cubeSets.forEach {
            // call java Math.max
            maxRed = max(it.red, maxRed)
            maxGreen = max(it.green, maxGreen)
            maxBlue = max(it.blue, maxBlue)
        }

        maxBlue * maxRed * maxGreen
    }
}

// String here should follow contract:  Game 42: 7 green, 2 blue, 1 red; 8 green, 4 red; 5 blue, 1 red, 3 green
private fun String.toGame(): Game {
    val (gameStr, cubeSetStr) = split(":")

    // We can use here regexp, but it's slow
    // val gameNumber = "game (\\d+)".toRegex().find(gameStr)?.groupValues[1]?.toInt() ?: throw IllegalStateException("Can't find game number.")

    // Here is a simple way to do the same
    val gameNumber = gameStr.split("Game ").last().toInt()

    val cubeSets: List<CubeSet> = cubeSetStr.split(";").map { cubesStr ->
        cubesStr.trim().toCubeSet()
    }

    return Game(gameNumber, cubeSets)
}

private fun String.toCubeSet(): CubeSet {
    // 7 green, 2 blue, 1 red -> array([7, green], [2, blue], [1, red])
    val chunkedCubes = replace(",", "").split(" ").chunked(2)
    var red = 0
    var green = 0
    var blue = 0
    //array([7, green], [2, blue], [1, red]) -> for each will call it one after another -> (7, green), and so on
    try {
        chunkedCubes.forEach { (cubeCount, cubeColor) ->
            when (cubeColor) {
                "red" -> {
                    red = cubeCount.toInt()
                }

                "green" -> {
                    green = cubeCount.toInt()
                }

                "blue" -> {
                    blue = cubeCount.toInt()
                }

            }
        }
    } catch (e: Exception) {
        println(chunkedCubes.joinToString { it.joinToString() })
        throw e
    }
    return CubeSet(red, green, blue)
}
