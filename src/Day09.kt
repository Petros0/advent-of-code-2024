import java.util.*

class Day09(input: String) {

    private val disk: List<Long?> = parse(input)

    private fun parse(input: String): List<Long?> =
        input
            .windowed(2, 2, true)
            .withIndex()
            .flatMap { (index, value) ->
                List(value.first().digitToInt()) { _ -> index.toLong() } +
                        List(value.getOrElse(1) { _ -> '0' }.digitToInt()) { null }
            }

    fun part1(): Long = solvePart1()

    fun part2(): Long = solvePart2()

    private fun solvePart1(): Long {
        val emptyBlocks = disk.indices.filter { disk[it] == null }.toMutableList()
        return disk.withIndex().reversed().sumOf { (index, value) ->
            if (value != null) {
                value * (emptyBlocks.removeFirstOrNull() ?: index)
            } else {
                emptyBlocks.removeLastOrNull()
                0
            }
        }
    }

    private fun solvePart2(): Long {
        val allBlocks = findAllBlocks(disk)
        val freeSpace: MutableMap<Int, PriorityQueue<Int>> = allBlocks
            .filter { it.fileId == null }
            .groupBy({ it.length }, { it.start })
            .mapValues { (_, v) -> PriorityQueue(v) }
            .toMutableMap()

        return allBlocks.filterNot { it.fileId == null }.reversed().sumOf { block ->
            block.checksum(
                freeSpace.findSpace(block)
            )
        }
    }

    private fun MutableMap<Int, PriorityQueue<Int>>.findSpace(block: Block): Int =
        (block.length .. 9).mapNotNull { trySize ->
            if (this[trySize]?.isNotEmpty() == true) trySize to this.getValue(trySize).first()
            else null
        }.sortedBy { it.second }.filter { it.second < block.start }.firstNotNullOfOrNull { (blockSize, startAt) ->
            this[blockSize]?.poll()
            if (blockSize != block.length) {
                val diff = blockSize - block.length
                computeIfAbsent(diff) { _ -> PriorityQueue() }.add(startAt + block.length)
            }
            startAt
        } ?: block.start

    private data class Block(val start: Int, val length: Int, val fileId: Long? = null) {
        fun checksum(index: Int = start): Long =
            (0 ..< length).sumOf {
                (index + it) * (fileId ?: 0L)
            }
    }

    private fun findAllBlocks(disk: List<Long?>): List<Block> = buildList {
        var blockStart = -1
        var previousValue: Long? = -1L
        disk.withIndex().forEach { (index, value) ->
            if (previousValue == -1L) {
                blockStart = index
                previousValue = value
            } else if (previousValue != value) {
                add(Block(blockStart, index - blockStart, previousValue))
                blockStart = index
                previousValue = value
            }
        }
        if (blockStart != -1) {
            add(Block(blockStart, disk.size - blockStart, previousValue))
        }
    }
}

data class File(val id: Long, val block: Long, val freeSpace: Long?)

fun main() {
    readInputAndTrim("Day09").let {
        Day09(it.first().trim()).run {
            println("Part 1: ${part1()}")
            println("Part 2: ${part2()}")
        }
    }
}
