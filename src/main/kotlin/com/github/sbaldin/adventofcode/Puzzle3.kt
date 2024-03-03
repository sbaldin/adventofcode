package com.github.sbaldin.adventofcode

fun runPuzzleDay3Part1(input: List<String>): Any {
    var sum = 0
    input.forEachIndexed outer@{ lineNumber, line ->
        var number = ""
        line.forEachIndexed inner@{ charPosition, c ->
            if (c.isDigit()) {
                number += c
                if (charPosition == line.lastIndex) {
                    if (checkSurround(charPosition, lineNumber, input, number, line)) {
                        sum += number.toInt()
                        print("$number ")
                        number = ""
                        return@inner
                    }
                }
            } else {
                if (number.isNotEmpty() && checkSurround(charPosition, lineNumber, input, number, line)) {
                    sum += number.toInt()
                    print("$number ")
                    number = ""
                    return@inner
                }
                number = ""
            }
        }
        print("\n")
    }
    return sum
}

fun getRange(charPosition: Int, number: String): IntRange {
    var start = charPosition - number.length
    if (charPosition - number.length > 0) {
        start -= 1
    }
    //charPosition указывает на последний символ числа если оно в коцне строки иначе на следующий символ после числа
    return IntRange(start, charPosition)
}

fun checkSurround(charPosition: Int, lineNumber: Int, input: List<String>, number: String, line: String): Boolean {
    val prevLine = input.getOrNull(lineNumber - 1)
    val nextLine = input.getOrNull(lineNumber + 1)
    var notDotSymbol = prevLine?.substring(getRange(charPosition, number))?.replace(".", "")
    if (!notDotSymbol.isNullOrBlank()) {
        return true
    }
    notDotSymbol = nextLine?.substring(getRange(charPosition, number))?.replace(".", "")
    if (!notDotSymbol.isNullOrBlank()) {
        return true
    }
    if (charPosition - number.length > 0) {
        val margin = if (charPosition == line.lastIndex && line[charPosition].isDigit()) 0 else 1
        val leftIndex = charPosition - number.length - margin
        if (line[leftIndex] != '.') {
            return true
        }
    }
    return charPosition < line.lastIndex && line[charPosition] != '.'
}


typealias NumberOrStar = Pair<Int, StringBuilder>

val NumberOrStar.position: Int get() = this.first
val NumberOrStar.value: StringBuilder get() = this.second
val NumberOrStar.valueAsInt: Int get() = this.second.toString().toInt()
val NumberOrStar.valueRange: IntRange get() = IntRange(position, (position + value.lastIndex).coerceAtLeast(0))

fun runPuzzle3Advanced(input: List<String>): Any {
    var sum = 0

    val parsedLines = mutableMapOf<Int, List<NumberOrStar>>()

    input.forEachIndexed { lineNumber, line ->
        parsedLines.getOrPut(lineNumber) {
            line.parseNumberAndStars()
        }.filter {
            it.value.toString() == "*"
        }.map { (position, _) ->
            sum += getNumbers(parsedLines, input, lineNumber, position).fold(1) { result, current ->
                result * current.valueAsInt
            }
        }

    }
    return sum
}

fun getNumbers(
    parsedLines: MutableMap<Int, List<NumberOrStar>>,
    input: List<String>,
    lineIndex: Int,
    starPosition: Int
): MutableList<NumberOrStar> {
    val result = mutableListOf<NumberOrStar>()

    val lineProcessor = { index: Int ->
        val numberAndStars = parsedLines.getOrPut(index) { input[index].parseNumberAndStars() }
        val onlyNumbers = numberAndStars.filter { it.value.toString() != "*" }

        val numbersInRange = onlyNumbers.filter { number ->
            listOf(starPosition, starPosition - 1, starPosition + 1).any { it in number.valueRange }
        }
        result.addAll(numbersInRange)
    }

    val previousLineIndex = lineIndex - 1
    if (previousLineIndex >= 0) {
        lineProcessor(previousLineIndex)
    }
    val nextLineIndex = lineIndex + 1
    if (nextLineIndex <= input.lastIndex) {
        lineProcessor(nextLineIndex)
    }
    lineProcessor(lineIndex)

    return result
}

/**
 *   Fold string ....213...*...21...&...* -> listOf(213, *, 21, *)
 **/
fun String.parseNumberAndStars(): List<NumberOrStar> =
    this.foldIndexed<MutableList<NumberOrStar>>(mutableListOf()) { index, r, char ->
        val lastParsedNumber =
            //Get last Number that we parsed or create a new StringBuilder for it
            r.lastOrNull { it.value.toString() != "*" } ?: NumberOrStar(index, StringBuilder()).also { r.add(it) }

        when {
            //if new digit is near to previous number than add such digit to number
            char.isDigit() && listOf(index, index - 1).any { it in lastParsedNumber.valueRange } -> {
                lastParsedNumber.value.append(char)
            }
            // else we think that it's a starting of new number
            // than add digit to empty string build and put in all to result
            char.isDigit() -> {
                r.add(NumberOrStar(index, StringBuilder("" + char)))
            }
            // finally if char is star than put it as Start to result
            char == '*' -> {
                r.add(NumberOrStar(index, StringBuilder("*")))
            }
        }
        r.filterNotTo(mutableListOf()) { it.value.isEmpty() }
    }.toList()