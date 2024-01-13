/**
 * Your calculation isn't quite right.
 * It looks like some of the digits are actually spelled out with letters:
 * one, two, three, four, five, six, seven, eight, nine.
 *
 * Also count as valid "digits".
 *
 * two1nine
 * eightwothree
 * abcone2threexyz
 * xtwone3four
 * 4nineeightseven2
 * zoneight234
 * 7pqrstsixteen
 * In this example, the calibration values are
 * 29, 83, 13, 24, 42, 14, and 76.
 * Adding these together produces 281.
 */
fun runPuzzle2(input: List<String>): Any {
    println("Run Puzzle 2:")
    val wordToDigitMap = mapOf(
        "one" to 1,
        "two" to 2,
        "six" to 6,
        "four" to 4,
        "five" to 5,
        "nine" to 9,
        "seven" to 7,
        "three" to 3,
        "eight" to 8,
    )
    val resultList = mutableListOf<Int>()

    for (line in input) {
        var leftDigit: Char? = null
        var rightDigit: Char? = null

        val leftThreeLetterWindow = StringBuilder()
        val leftFourLetterWindow = StringBuilder()
        val leftFiveLetterWindow = StringBuilder()

        val rightThreeLetterWindow = StringBuilder()
        val rightFourLetterWindow = StringBuilder()
        val rightFiveLetterWindow = StringBuilder()

        var rightIndex = line.lastIndex

        for (leftIndex in 0..line.lastIndex) {
            val leftChar = line[leftIndex]
            val rightChar = line[rightIndex]

            if (leftDigit == null) {
                appendToWindowOrGetValue(leftThreeLetterWindow, 3, leftChar, wordToDigitMap)?.also {
                    leftDigit = it
                }
                appendToWindowOrGetValue(leftFourLetterWindow, 4, leftChar, wordToDigitMap)?.also {
                    leftDigit = it
                }
                appendToWindowOrGetValue(leftFiveLetterWindow, 5, leftChar, wordToDigitMap)?.also {
                    leftDigit = it
                }
                if (leftChar.isDigit()) {
                    leftDigit = leftChar
                }
            }

            if (rightDigit == null) {
                appendToWindowOrGetValue(rightThreeLetterWindow, 3, rightChar, wordToDigitMap, false)?.also {
                    rightDigit = it
                }
                appendToWindowOrGetValue(rightFourLetterWindow, 4, rightChar, wordToDigitMap, false)?.also {
                    rightDigit = it
                }
                appendToWindowOrGetValue(rightFiveLetterWindow, 5, rightChar, wordToDigitMap, false)?.also {
                    rightDigit = it
                }
                if (rightChar.isDigit()) {
                    rightDigit = rightChar
                }
            }

            if (leftDigit != null && rightDigit != null) {
                resultList.add(mergeDigits(leftDigit!!, rightDigit!!))
                break
            }
            rightIndex--
        }
    }
    resultList.joinToString().also { println(it) }

    return resultList.sum()
}

private fun appendToWindowOrGetValue(
    letterWindow: StringBuilder,
    windowSize: Int,
    currentChar: Char,
    wordToDigitMap: Map<String, Int>,
    appendToRight: Boolean = true
): Char? {
    var leftDigit: Char? = null
    letterWindow.append(currentChar)
    if (letterWindow.length == windowSize) {
        val word = if (appendToRight) {
            letterWindow.toString()
        } else {
            letterWindow.toString().reversed()
        }
        val digit = wordToDigitMap[word]
        leftDigit = digit?.run { Character.forDigit(digit, 10) }
        letterWindow.deleteCharAt(0)
    }
    return leftDigit
}

private fun attemptWithReplace(
    input: List<String>,
    wordDigit: Map<String, Int>
): Int {
    val resultList = mutableListOf<Int>()

    for (line in input) {
        var leftDigit: Char? = null
        var rightDigit: Char? = null

        var lineBuffer = line
        wordDigit.entries.forEach { (digitAsWord, digit) ->
            lineBuffer = lineBuffer.replace(digitAsWord, digit.toString())
        }
        var rightIndex = lineBuffer.lastIndex

        for (leftIndex in 0..lineBuffer.lastIndex) {
            val leftChar = lineBuffer[leftIndex]

            if (leftDigit == null && leftChar.isDigit()) {
                leftDigit = leftChar
            }

            val rightChar = lineBuffer[rightIndex]
            if (rightDigit == null && rightChar.isDigit()) {
                rightDigit = rightChar
            }

            if (leftDigit != null && rightDigit != null) {
                resultList.add(mergeDigits(leftDigit, rightDigit))
                break
            }
            rightIndex--
        }
    }
    resultList.joinToString().also { println(it) }

    return resultList.sum()
}

private fun mergeDigits(leftDigit: Char, rightDigit: Char) =
    Character.getNumericValue(leftDigit) * 10 + Character.getNumericValue(rightDigit.code)