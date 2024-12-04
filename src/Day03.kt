fun main() {

    fun part1(instruction: String): Int {
        return """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
            .findAll(instruction)
            .sumOf { match -> match
                .groupValues
                .drop(1)
                .map { it.toInt() }
                .reduce(Int::times)
            }
    }

    fun part2(instruction: String): Int {
        return """(^|do\(\)).*?(${'$'}|don't\(\))""".toRegex()
            .findAll(instruction)
            .sumOf { part1(it.value) }
    }

    val part1Instruction = readAsString("Day03_test")
    val part1Result = part1(part1Instruction)
    part1Result.println()

    val part2Instruction = readAsString("Day03_test")
    val part2Result = part2(part2Instruction)
    part2Result.println()
}
