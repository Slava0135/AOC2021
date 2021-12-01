fun main() {
    fun part1(input: List<String>): Int {
        var count = 0
        var lastValue = input.first().toInt()
        for (i in 1 until input.size) {
            val value = input[i].toInt()
            if (value > lastValue) count++
            lastValue = value
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val window = 3
        var count = 0
        for (i in window until input.size) {
            if (input[i-window].toInt() < input[i].toInt()) count++
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
