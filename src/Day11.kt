class Day11(input: String) {

    private val stones = input.split(" ").map { it.toLong() }

    private val cache: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()

    fun part1(): Long = sumBlinks(25)

    fun part2(): Long = sumBlinks(75)

    private fun sumBlinks(times: Int): Long = stones.sumOf { blink(it, times) }

    private fun blink(stone: Long,
                      blinks: Int,
                      key: Pair<Long, Int> = stone to blinks): Long =
        when {
            blinks == 0 -> 1
            key in cache -> cache.getValue(key)
            else -> {
                val result = when {
                    stone == 0L -> blink(1, blinks -1)
                    stone.hasEvenDigits() -> stone.split().sumOf { blink(it, blinks - 1) }
                    else -> blink(stone * 2024, blinks - 1)
                }
                cache[key] = result
                result
            }
        }

    private fun Long.hasEvenDigits(): Boolean = toString().length % 2 == 0

    private fun Long.split(): List<Long> {
        val s = toString()
        return listOf(
            s.substring(0, s.length / 2).toLong(),
            s.substring(s.length / 2, s.length).toLong()
        )
    }
}

fun main() {
    val input = readInputAndTrim("Day11_test").first()
    val day11 = Day11(input)
    println("Day 11, Part 1: ${day11.part1()}")
    println("Day 11, Part 2: ${day11.part2()}")
}
