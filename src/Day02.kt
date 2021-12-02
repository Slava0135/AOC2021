fun main() {
    fun part1(input: List<String>): Int {
        var depth = 0
        var dist = 0
        for (line in input) {
            val (command, value) = line.split(" ")
            when (command) {
                "forward" -> dist += value.toInt()
                "up" -> depth -= value.toInt()
                "down" -> depth += value.toInt()
            }
        }
        return depth * dist
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var dist = 0
        var aim = 0
        for (line in input) {
            val (command, value) = line.split(" ")
            when (command) {
                "forward" -> {
                    value.toInt().let {
                        dist += it
                        depth += aim * it
                    }
                }
                "up" -> aim -= value.toInt()
                "down" -> aim += value.toInt()
            }
        }
        return depth * dist
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
