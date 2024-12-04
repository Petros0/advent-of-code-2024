import kotlin.system.measureTimeMillis

fun main() {

    fun readFile(file: String): List<String> {
        return readInput(file).lines()
            .filter { it.isNotBlank() }
            .toList()
    }

    tailrec fun vectorFind(input: List<String>, target: String, x: Int, y: Int, vector: Pair<Int, Int>): Boolean =
        when {
            target.isEmpty() -> true
            target.first() != input.safeAt(x, y) -> false
            else -> vectorFind(input, target.substring(1), x + vector.first, y + vector.second, vector)
        }

    fun part1(input: List<String>): Int {
        val ALL_DIRECTIONS = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1, 0 to 1,
            1 to -1, 1 to 0, 1 to 1
        )


        return input.flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c == 'X') {
                    ALL_DIRECTIONS.count { vector ->
                        vectorFind(input, "XMAS", x, y, vector)
                    }
                } else 0
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val CORNERS = listOf(
            -1 to -1,
            -1 to 1,
            1 to 1,
            1 to -1
        )

        return input.flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c == 'A') {
                    CORNERS
                        .map { (dx, dy) -> input.safeAt(x + dx, y + dy) }
                        .joinToString("") in setOf("MMSS", "MSSM", "SSMM", "SMMS")
                } else false
            }
        }.count { it }
    }

    val timeInMillis = measureTimeMillis { part1(readFile("Day04_test")).println() }
    println("Time: $timeInMillis ms")

    val timeInMillis2 = measureTimeMillis { part2(readFile("Day04")).println() }
    println("Time: $timeInMillis2 ms")
}

private fun List<String>.safeAt(x: Int, y: Int): Char {
    return if (y in indices && x in this[y].indices) this[y][x] else ' '
}
