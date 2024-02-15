import kotlin.math.max

typealias CubeSet = Triple<Int, Int, Int>

val CubeSet.red: Int get() = this.first
val CubeSet.green: Int get() = this.second
val CubeSet.blue: Int get() = this.third

typealias Game = Pair<Int, List<CubeSet>>

val Game.number: Int get() = this.first
val Game.cubeSets: List<CubeSet> get() = this.second


fun runPuzzleDay2Part1(input: List<String>): Any {
   TODO()
}

fun runPuzzleDay2Part2(input: List<String>): Any {
   TODO()
}

