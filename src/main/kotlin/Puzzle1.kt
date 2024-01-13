/**
 * As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been
 * amended by a very young Elf who was apparently just excited to show off her art skills. Consequently,
 * the Elves are having trouble reading the values on the document.
 *
 * The newly-improved calibration document consists of lines of text; each line originally contained
 * a specific calibration value that the Elves now need to recover. On each line, the calibration
 * value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number.
 *
 * For example:
 *
 * 1abc2
 * pqr3stu8vwx
 * a1b2c3d4e5f
 * treb7uchet
 * In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces 142.
 *
 * Consider your entire calibration document. What is the sum of all the calibration values?
 *
 *  1. simple case       line = aa2a1b      leftDigit = null , rightDigit = 1   , result 21
 *  2. single digit      line = 1           leftDigit = null , rightDigit = null, result 11
 *  3. asymmetric right  line = 1adsdasdsad leftDigit = 1    , rightDigit = null, result 11
 *  4. asymmetric left   line = adsdsdads1  leftDigit = null , rightDigit = 1   , result 11
 *  5. in the middle     line = aa1ds       leftDigit = null , rightDigit = null, result 11
 *  7. asymmetric middle line = aa21b       leftDigit = null , rightDigit = 1   , result 21
 *  7. no digits         line = aa21b       leftDigit = null , rightDigit = 1   , result 21
 */
fun runPuzzle(input: List<String>): Any {
    // calc complexity rows * line_length >> 0^2
    val resultList = mutableListOf<Int>()

    for (line in input) {
        var leftDigit: Char? = null
        var rightDigit: Char? = null
        var rightIndex = line.lastIndex
        for (leftIndex in 0..line.lastIndex) {
            val leftChar = line[leftIndex]

            if (leftDigit == null && leftChar.isDigit()) {
                leftDigit = leftChar
            }

            val rightChar = line[rightIndex]
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