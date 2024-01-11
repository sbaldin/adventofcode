import java.io.FileNotFoundException

fun main(args: Array<String>) {
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Hello World!")

    PuzzleApplication().run {
        val output = runPuzzle(getPuzzleInput())
        println("Puzzle output:")
        println(output)
    }

}

class PuzzleApplication

private fun PuzzleApplication.getPuzzleInput(): List<String> = javaClass
    .classLoader
    .getResourceAsStream("./puzzle_input.txt")
    ?.reader()?.readLines() ?: throw FileNotFoundException("Can't find puzzle_input.txt in resource folder!")