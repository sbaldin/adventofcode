package com.github.sbaldin.adventofcode

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import runPuzzleDay1Part1
import runPuzzleDay1Part2
import java.io.FileNotFoundException

class Puzzle1Spec : StringSpec({
    "Puzzle 1 part 1 test input" {
        runPuzzleDay1Part1(getTestInputPart1()) shouldBe 142
    }
    "Puzzle 1 part 1 huge input" {
        runPuzzleDay1Part1(getPuzzleInput()) shouldBe 54632
    }


    "Puzzle 1 part 2 test input" {
        runPuzzleDay1Part2(getTestInputPart2()) shouldBe 281
    }
    "Puzzle 1 part 2 huge input" {
        runPuzzleDay1Part2(getPuzzleInput()) shouldBe 54019
    }
})

fun getTestInputPart1(): List<String> = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent().lines()


fun getTestInputPart2(): List<String> = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent().lines()


private fun StringSpec.getPuzzleInput(): List<String> = javaClass
    .classLoader
    .getResourceAsStream("./puzzle_input.txt")
    ?.reader()?.readLines() ?: throw FileNotFoundException("Can't find puzzle_input.txt in resource folder!")