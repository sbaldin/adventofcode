package com.github.sbaldin.adventofcode

import kotlin.math.pow

typealias ScratchCard = Triple<Int, Set<Int>, List<Int>>

val ScratchCard.number: Int get() = this.first
val ScratchCard.frontSide: Set<Int> get() = this.second
val ScratchCard.backSide: List<Int> get() = this.third

// INPUT Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
fun runPuzzleDay4Part1(input: List<String>): Any {
    return input.map { it.toScratchCard() }.sumOf { it.getWinPoints() }
}

private fun ScratchCard.getWinPoints(): Int {
    val power = this.backSide.filter { frontSide.contains(it) }.size - 1
    return if (power >= 0) 2.0.pow(power).toInt() else 0
}

private fun String.toScratchCard(): ScratchCard {
    val (cardNumberStr, cardSidesStr) = split(": ")
    val cardNumber = cardNumberStr.split(" ").last().toInt()
    val (frontSideStr, backSideStr) = cardSidesStr.split(" | ")
    val frontSide = frontSideStr.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    val backSide = backSideStr.split(" ").filter { it.isNotBlank() }.map { it.toInt() }

    return ScratchCard(cardNumber, frontSide.toSet(), backSide)
}

fun runPuzzle4Advanced(input: List<String>): Any {
    TODO()
}
