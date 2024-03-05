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
    val power = getMatchingNumber() - 1
    return if (power >= 0) 2.0.pow(power).toInt() else 0
}

private fun ScratchCard.getMatchingNumber(): Int = this.backSide.filter { frontSide.contains(it) }.size

private fun String.toScratchCard(): ScratchCard {
    val (cardNumberStr, cardSidesStr) = split(": ")
    val cardNumber = cardNumberStr.split(" ").last().toInt()
    val (frontSideStr, backSideStr) = cardSidesStr.split(" | ")
    val frontSide = frontSideStr.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    val backSide = backSideStr.split(" ").filter { it.isNotBlank() }.map { it.toInt() }

    return ScratchCard(cardNumber, frontSide.toSet(), backSide)
}


data class CardMultiplier(var value: Int = 1)

/**
 *   N - card number
 *   C - copies of current card
 *   CP - copies that current card bring to game
 *  Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
 *  Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
 *  Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
 *  Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
 *  Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
 *  Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
 *
 *   iter 1
 *   N C CP
 *   1 1 4
 *   2 2 0
 *   3 2 0
 *   4 2 0
 *   5 2 0
 *   6 1 0
 *
 *   iter 2
 *   N C CP
 *   1 1 4
 *   2 2 2
 *   3 4 0
 *   4 4 0
 *   5 2 0
 *   6 1 0
 *
 *   iter 3
 *   N C CP
 *   1 1 4
 *   2 2 2
 *   3 4 2
 *   4 8 0
 *   5 6 0
 *   6 1 0
 *
 *   iter 4
 *   N C CP
 *   1 1 4
 *   2 2 2
 *   3 4 2
 *   4 8 1
 *   5 14 0
 *   6 1 0
 *
 *
 *   iter 5
 *   N C CP
 *   1 1 4
 *   2 2 2
 *   3 4 2
 *   4 8 1
 *   5 14 0
 *   6 1 0
 */
fun runPuzzle4Advanced(input: List<String>): Any {
    val cards = input.map { it.toScratchCard() }

    val cardMultipliers = buildList { repeat(cards.size) { add(CardMultiplier(1)) } }

    cards.forEachIndexed { currentCardIndex, card ->
        val copiesCount = card.getMatchingNumber()
        val currentCardMultiplier = cardMultipliers[currentCardIndex]

        // loop from 0 to copiesCount - 1
        for (i in 1 .. copiesCount) {
            val newCardIndex = currentCardIndex + i
            cardMultipliers.getOrNull(newCardIndex)?.let {
                cardMultipliers[newCardIndex].value +=  currentCardMultiplier.value
            }
        }
    }

    return cardMultipliers.sumOf { it.value }
}
