class Day10(input: List<String>) {

    private val grid: List<IntArray> = input.map { row ->
        row.map { it.digitToInt() }.toIntArray()
    }

    fun part1(): Int = scoreTrails(true)

    fun part2(): Int = scoreTrails(false)

    private fun scoreTrails(memory: Boolean): Int =
        grid.mapIndexed { y, row ->
            row.mapIndexed { x, i ->
                if (i == 0) findPaths(Point2D(x, y), memory) else 0
            }.sum()
        }.sum()

    private fun findPaths(start: Point2D, memory: Boolean): Int {
        val queue = mutableListOf(start)
        val seen = mutableSetOf<Point2D>()
        var found = 0

        while (queue.isNotEmpty()) {
            val place = queue.removeFirst()
            if (place in seen) continue

            if (memory) seen += place
            if (grid[place] == 9) found++
            else {
                val cardinalNeighbors = place.cardinalNeighbors()
                    .filter { it in grid }
                    .filter { grid[it] == grid[place] + 1 }
                queue.addAll(cardinalNeighbors)
            }
        }

        return found
    }

    private operator fun List<IntArray>.contains(at: Point2D): Boolean =
        at.y in indices && at.x in get(at.y).indices

    private operator fun List<IntArray>.get(at: Point2D): Int =
        this[at.y][at.x]
}

fun main() {
    readInputAndTrim("Day10_test")
        .let {
            Day10(it).run {
                println("Part 1: ${part1()}")
                println("Part 2: ${part2()}")
            }
        }
}
