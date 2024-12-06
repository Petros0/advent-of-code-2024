class Day06(private val input: List<String>) {

    private val grid: List<CharArray> = input.map { it.toCharArray() }
    private val start: Point2D = grid
        .flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c == '^') Point2D(x, y) else null
            }
        }
        .filterNotNull()
        .first()

    init {
        println("Input: $start")
    }

    fun part1(): Int = traverse().first.size

    private fun traverse(): Pair<Set<Point2D>, Boolean> {
        val seen = mutableSetOf<Pair<Point2D, Point2D>>()
        var location = start
        var direction = Point2D.NORTH

        while (grid[location] != null && (location to direction) !in seen) {
            seen += location to direction
            val next = location + direction

            if (grid[next] == '#') direction = direction.turn()
            else location = next
        }
        return seen.map { it.first }.toSet() to (grid[location] != null)
    }

    fun part2(): Int =
        traverse()
            .first
            // we can't obstacle were the guard is
            .filterNot { it == start }
            .count { candidate ->
                grid[candidate] = '#'
                // run again with new grid
                traverse().also { grid[candidate] = '.' }.second
            }
}

data class Point2D(val x: Int, val y: Int) {

    operator fun plus(other: Point2D): Point2D =
        Point2D(x + other.x, y + other.y)

    companion object {
        val NORTH = Point2D(0, -1)
        val EAST = Point2D(1, 0)
        val SOUTH = Point2D(0, 1)
        val WEST = Point2D(-1, 0)
    }
}

fun main() {
    readInput("Day06_test").lines().let {
        Day06(it).run {
            println("Part 1: ${part1()}")
            println("Part 2: ${part2()}")
        }
    }
}

private operator fun List<CharArray>.get(at: Point2D): Char? =
    getOrNull(at.y)?.getOrNull(at.x)

private operator fun List<CharArray>.set(at: Point2D, c: Char) {
    this[at.y][at.x] = c
}

private fun Point2D.turn(): Point2D =
    when (this) {
        Point2D.NORTH -> Point2D.EAST
        Point2D.EAST -> Point2D.SOUTH
        Point2D.SOUTH -> Point2D.WEST
        Point2D.WEST -> Point2D.NORTH
        else -> throw IllegalArgumentException("Invalid direction: $this")
    }
