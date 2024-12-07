class Day07(input: List<String>) {

    private val equations: List<Equation> = input
        .filter { it.isNotEmpty() }
        .map {
            val testValue = it.takeWhile { c -> c != ':' }
            val numbers = it.dropWhile { c -> c != ':' }
                .drop(1)
                .split(" ")
                .filter { num -> num.isNotEmpty() }
                .map { num -> num.toLong() }

            Equation(testValue.toLong(), numbers)
        }

    private val operators: List<(Long, Long) -> Long> = listOf(
        { a, b -> a + b },
        { a, b -> a * b }
    )

    fun part1(): Long = solve(operators)

    private fun solve(validOperators: List<(Long, Long) -> Long>): Long =
        equations
            .filter {
                hasSolution(
                    validOperators,
                    it.testValue,
                    it.numbers.first(),
                    it.numbers.subList(1, it.numbers.size)
                )
            }
            .sumOf { it.testValue }

    private fun hasSolution(
        operators: List<(Long, Long) -> Long>,
        testValue: Long,
        sum: Long,
        remaining: List<Long>
    ): Boolean =
        when {
            remaining.isEmpty() -> testValue == sum
            sum > testValue -> false
            else -> operators.any { operator ->
                hasSolution(
                    operators,
                    testValue,
                    operator.invoke(sum, remaining[0]),
                    remaining.subList(1, remaining.size)
                )
            }
        }

    fun part2(): Long = solve(operators + { a, b -> "$a$b".toLong() })
}

data class Equation(val testValue: Long, val numbers: List<Long>)

fun main() {
    readInput("Day07").lines().let {
        Day07(it).run {
            println("Part 1: ${part1()}")
            println("Part 2: ${part2()}")
        }
    }
}
