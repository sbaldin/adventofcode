package com.github.sbaldin.adventofcode

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import runPuzzleDay1Part1
import runPuzzleDay1Part2
import runPuzzleDay2Part1
import runPuzzleDay2Part2
import java.io.FileNotFoundException

class Puzzle2Spec : StringSpec({
    "Puzzle 2 part 1 test input" {
        runPuzzleDay2Part1(getTestInput()) shouldBe 8
    }
    "Puzzle 2 part 1 huge input" {
        runPuzzleDay2Part1(getPuzzleInput()) shouldBe 1931
    }


    "Puzzle 2 part 2 test input" {
        runPuzzleDay2Part2(getTestInput()) shouldBe 2286
    }
    "Puzzle 2 part 2 huge input" {
        runPuzzleDay2Part2(getPuzzleInput()) shouldBe 83105
    }
})

fun getTestInput(): List<String> = """
Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent().lines()


private fun StringSpec.getPuzzleInput(): List<String> = javaClass
    .classLoader
    .getResourceAsStream("./puzzle_input_2.txt")
    ?.reader()?.readLines() ?: throw FileNotFoundException("Can't find puzzle_input_2.txt in resource folder!")