package com.github.sbaldin.adventofcode

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe

class Day3Part1 : StringSpec({
    "day 3 simple test input" {
        val input = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..
        """.trimIndent().lines()

        runPuzzleDay3Part1(input) shouldBe 4361
    }
    "day 3 corner case test input" {
        val input = """
        ........#911
        298.........
        """.trimIndent().lines()

        runPuzzleDay3Part1(input) shouldBe 911
    }
    "day 3 corner case with no surround symbols test input" {
        val input = """
            .2....&1..
            ..........
            .......55.
        """.trimIndent().lines()

        runPuzzleDay3Part1(input) shouldBe 1
    }
})

class Day3Part2 : StringSpec({
    "parseNumberAndStars works as expected" {
        val input1 = """
            900..743..
            ...*......
            ..900..679
        """.trimIndent().lines()
        val result = input1.map { it.parseNumberAndStars() }

        result.size shouldBe 3
        result[0].let {
            val firstNumber = it.first()
            val lastNumber = it.last()

            firstNumber.position shouldBe  0
            firstNumber.valueAsInt shouldBe  900

            lastNumber.position shouldBe  5
            lastNumber.valueAsInt shouldBe  743
        }
        result[1].let {
            val firstNumber = it.last()

            firstNumber.position shouldBe  3
            firstNumber.value shouldBe  "*"
        }

    }

    "day 3 simple test input" {
        val input1 = """
            900..743..
            ...*......
            ..900..679
        """.trimIndent().lines()
        runPuzzle3Advanced(input1) shouldBe 8100

        val input2 = """
            ..399.....
            ...*......
            ..976..679
        """.trimIndent().lines()

        val input3 = """
            ....974...
            ...*......
            ..976..679
        """.trimIndent().lines()

        val input4 = """
            333.974...
            ...*......
            .......679
        """.trimIndent().lines()
    }
    "day 3 corner case test input" {
        val input = """
        ........#911
        298.........
        """.trimIndent().lines()

        runPuzzleDay3Part1(input) shouldBe 911
    }
    "day 3 corner case with no surround symbols test input" {
        val input = """
            .2....&1..
            ..........
            .......55.
        """.trimIndent().lines()

        runPuzzleDay3Part1(input) shouldBe 1
    }
})