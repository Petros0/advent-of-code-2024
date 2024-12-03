fun main() {

    fun reports(inputFile: String): List<List<Int>> {
        val input = readInput(inputFile).lines()
            .filter { it.isNotBlank() }
            .map { it.split(" ").map { it -> it.toInt() } }
            .toList()
        return input
    }

    fun isSafe(it: List<Int>): Boolean {
        val diffs = it.zipWithNext().map { (left, right) -> right - left }
        return diffs.all { it in 1..3 } || diffs.all { it in -3..-1 }
    }

    fun part1(inputFile: String): Int {
        val input = reports(inputFile)
        return input.count { isSafe(it) }
    }

    fun isSafeDampened(it: List<Int>): Boolean =
        it.indices.any { removeThis ->
            isSafe(it.filterIndexed { index, _ -> removeThis != index })
        }

    fun part2(inputFile: String): Int {
        val input = reports(inputFile)
        return input.count { isSafeDampened(it) }
    }

    println(part1("Day02"))
    println(part2("Day02_test"))
}

