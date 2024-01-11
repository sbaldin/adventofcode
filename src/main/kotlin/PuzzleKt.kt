// Puzzle 1
// asdasd1dsad32dsd3232dsds3
// asdasdsdasdasdasd1
// 12323123
// sdsadsad
// dsadasf4324
// dasda1
fun runPuzzle(input: List<String>): Any {
    // calc complexity rows * line_length >> 0^2
    val resultList = mutableListOf<Int>()

    for (line in input) {
        var leftDigit: Char? = null
        var rightDigit: Char? = null
        var rightIndex = line.lastIndex
        ///line = aa21b leftDigit = null , rightDigit = 1, result 21
        ///line = 1 leftDigit = null , rightDigit = null, result 11
        //       l           r
        //        l         r
        //           r l
        //line = 87________fm leftDigit = 8 , rightDigit = 6, result 86
        //line = mbxbrntsfm876 leftDigit = 8 , rightDigit = 6, result 86
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
           /* else {
                ///1. line = aa1dsdasdsad leftDigit = 1 , rightDigit = null, result 11
                ///2. line = adsdsda1ds leftDigit = null , rightDigit = 1, result 11
                if (leftDigit == null && rightDigit != null) {
                    leftDigit = rightDigit
                }

                if (rightDigit == null && leftDigit != null) {
                    rightDigit = leftDigit
                }

                ///3. line = aa1ds leftDigit = null , rightDigit = null, result 11
                ///4. line = 1 leftDigit = null , rightDigit = null, result 11
                ///5. line = aa21b leftDigit = null , rightDigit = 1, result 21
                ///6. line = a21bb leftDigit = 2 , rightDigit = null, result 21
                if (leftChar.isDigit()) {
                    if (leftDigit == null) {
                        leftDigit = leftChar
                    }
                    if (rightDigit == null) {
                        rightDigit = leftChar
                    }
                }

                if (leftDigit != null && rightDigit != null) {
                    resultList.add(mergeDigits(leftDigit, rightDigit))
                    break
                }

                ///line = aaa leftDigit = null , rightDigit = null, result skip line
                break
            }*/
        }
    }
    resultList.joinToString().also { println(it) }

    return resultList.sum()
}

private fun mergeDigits(leftDigit: Char, rightDigit: Char) =
    Character.getNumericValue(leftDigit) * 10 + Character.getNumericValue(rightDigit.code)