fun main() {
    fun readInputIntoList(inputPath: String): Pair<MutableList<Int>, MutableList<Int>> {
        val input = readInput(inputPath);
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        val delimiter = "   "
        input.lineSequence()
            .filter { it.isNotEmpty() }
            .forEach {
                val (first, second) = it.split(delimiter).map(String::toInt)
                firstList.add(first)
                secondList.add(second)
            }
        return Pair(firstList, secondList)
    }

    fun part1(inputPath: String): Int {
        val (firstList, secondList) = readInputIntoList(inputPath)
        firstList.sort()
        secondList.sort()
        check(firstList.size == secondList.size)

        var totalDistance = 0
        firstList.forEachIndexed { index, first ->
            val second = secondList[index]
            val distance = when {
                first > second -> first - second
                first < second -> second - first
                else -> 0
            }
            totalDistance += distance
        }

        return totalDistance
    }

    fun part2(inputPath: String): Int {
        val (firstList, secondList) = readInputIntoList(inputPath)

        val secondListMap = mutableMapOf<Int, Int>()
        secondList.forEach {
            secondListMap[it] = secondListMap.getOrDefault(it, 0) + 1
        }

        return firstList
            .filter { secondListMap.containsKey(it) }
            .sumOf { it * secondListMap[it]!! }
    }

    println(part1("Day01_test"))
    println(part2("Day01_test"))

}
