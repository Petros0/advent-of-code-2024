class Day08(private val input: List<String>) {

    private val nodes: Collection<List<Point2D>> = parseGrid(input)

    private fun parseGrid(input: List<String>): Collection<List<Point2D>> =
        input.flatMapIndexed { y, row ->
            row.mapIndexed { x, c ->
                if (c != '.') c to Point2D(x, y) else null
            }
        }.filterNotNull()
            .groupBy({ it.first }, { it.second })
            .values

    fun part1(): Int = countAntiNodes(::antiNodesForPart1)

    fun part2(): Int = countAntiNodes(::antiNodesForPart2)

    private fun countAntiNodes(worker: (Point2D, Point2D, Point2D) -> Set<Point2D>): Int =
        nodes.flatMap { nodeList ->
            nodeList.flatMapIndexed { i, a ->
                nodeList.drop(i + 1).flatMap { b ->
                    worker.invoke(a, b, a - b)
                }
            }.filter { it.isOnGrid() }
        }.toSet().size

    private fun antiNodesForPart1(a: Point2D, b: Point2D, diff: Point2D): Set<Point2D> =
        if (a.y > b.y) setOf(a - diff, b + diff)
        else setOf(a + diff, b - diff)

    private fun antiNodesForPart2(a: Point2D, b: Point2D, diff: Point2D): Set<Point2D> =
        generateSequence(a) { it - diff }.takeWhile { it.isOnGrid() }.toSet() +
                generateSequence(a) { it + diff }.takeWhile { it.isOnGrid() }.toSet()

    private fun Point2D.isOnGrid(): Boolean =
        y in input.indices && x in input[y].indices
}

fun main() {
    readInput("Day08_test").lines().let {
        Day08(it).run {
            println("Part 1: ${part1()}")
            println("Part 2: ${part2()}")
        }
    }
}
