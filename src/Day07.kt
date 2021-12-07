import kotlin.math.abs
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }
        var minFuel = Int.MAX_VALUE
        for (align in positions.indices) {
            var fuel = 0
            for (pos in positions) {
                fuel += abs(align - pos)
            }
            minFuel = min(minFuel, fuel)
        }
        return minFuel
    }

    fun part2(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }
        var minFuel = Int.MAX_VALUE
        for (align in positions.indices) {
            var fuel = 0
            for (pos in positions) {
                val steps = abs(align - pos)
                fuel += steps * (1 + steps) / 2
            }
            minFuel = min(minFuel, fuel)
        }
        return minFuel
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
