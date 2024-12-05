class Day05(input:List <String>) {

    private val rules: Set<String> = input
        .takeWhile { it.isNotEmpty() }
        .toSet()

    private val updates: List<List<String>> = input
        .dropWhile { it.isNotEmpty() }
        .drop(1)
        .filter { it.isNotEmpty() }
        .map { row -> row.split(",") }

    private val comparator: Comparator<String> = Comparator { a, b ->
        when {
            "$a|$b" in rules -> -1
            "$b|$a" in rules -> 1
            else -> 0
        }
    }

    fun solvePart1(): Int =
        updates
            .map { formatCorrectly(it) }
            .filter { it.first == it.second }
            .sumOf { it.second.midpoint().toInt() }

    fun solvePart2(): Int =
        updates
            .map { formatCorrectly(it) }
            .filterNot { it.first == it.second }
            .sumOf { it.second.midpoint().toInt() }

    private fun formatCorrectly(update: List<String>): Pair<List<String>, List<String>> =
        update to update.sortedWith(comparator)
}

fun main() {
    val input = readInput("Day05").lines()

    Day05(input).run {
        println(solvePart1())
        println(solvePart2())
    }
}
