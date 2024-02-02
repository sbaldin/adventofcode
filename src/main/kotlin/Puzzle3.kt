fun runPuzzle3(input: List<String>): Any {
    var sum = 0
    input.forEachIndexed outer@{ lineNumber, line ->
        var number = ""
        line.forEachIndexed { charPosition, c ->
            if (c.isDigit()) {
                number += c
                if (charPosition == line.lastIndex) {
                    if (checkSurround(charPosition, lineNumber, input, number, line)) {
                        sum += number.toInt()
                        print("$number ")
                        number = ""
                        return@forEachIndexed
                    }
                }
            } else {
                if (number.isNotEmpty() && checkSurround(charPosition, lineNumber, input, number, line)) {
                    sum += number.toInt()
                    print("$number ")
                    number = ""
                    return@forEachIndexed
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
        if (notDotSymbol.any { it.isDigit() }) {
            print("NOT BLACK $notDotSymbol")
        }
        return true
    }
    notDotSymbol = nextLine?.substring(getRange(charPosition, number))?.replace(".", "")
    if (!notDotSymbol.isNullOrBlank()) {
        if (notDotSymbol.any { it.isDigit() }) {
            print("NOT BLACK $notDotSymbol")
        }
        return true
    }
    if (charPosition - number.length > 0) {
        val leftIndex = charPosition - number.length - 1
        if (line[leftIndex] != '.') {
            return true
        }
    }
    return charPosition < line.lastIndex && line[charPosition] != '.'
}

fun runPuzzle3Advanced(input: List<String>): Any {
    var sum = 0
    input.forEachIndexed outer@{ lineNumber, line ->
        var number = ""
        line.forEachIndexed { charPosition, c ->
            if (c == '*') {
                if (charPosition > 0 && line[charPosition - 1].isDigit()) {
                    val leftNumber = getNumber(charPosition, line, "", Direction.LEFT)
                }
                val prevLine = input.getOrNull(lineNumber - 1)
                val nextLine = input.getOrNull(lineNumber + 1)
                if (prevLine != null) {
                    val top = getNumber(charPosition, line, "", Direction.BOTH)
                }
                // if ()
            }
        }
    }
    return sum
}

fun getNumber(charPosition: Int, line: String, number: String, direction: Direction): String? {

    if (direction == Direction.BOTH) {
        val left = if (line[charPosition - 1].isDigit()) {
            getNumber(charPosition - 1, line, number, Direction.LEFT)
        } else {
            ""
        }
        return left + getNumber(charPosition, line, number, Direction.RIGHT)
    }
    val newNumber = if (direction == Direction.LEFT) line[charPosition] + number else number + line[charPosition]

    val newCharPosition = charPosition + direction.multiplier
    return if (newCharPosition < 0 || newCharPosition >= line.length || !line[newCharPosition].isDigit()) {
        newNumber
    } else {
        getNumber(newCharPosition, line, newNumber, direction)
    }
}

enum class Direction(val multiplier: Int) {
    LEFT(-1), RIGHT(1), BOTH(0)
}