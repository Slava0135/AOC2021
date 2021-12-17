import kotlin.math.max

fun main() {

    class Bounds(val x: IntRange, val y: IntRange)

    fun parseInput(input: String): Bounds {
        val (xrange, yrange) = input.replace(",", "")
            .split(" ").drop(2)
            .map { it.split("=").last() }
            .map { it.split("..").map { it.toInt() } }
        return Bounds(xrange.first()..xrange.last(), yrange.first()..yrange.last())
    }

    fun part1(bounds: Bounds): Int {
        val maxSpeed = 0 - bounds.y.first
        return maxSpeed * (maxSpeed - 1) / 2
    }

    fun part2(bounds: Bounds): Int {
        val maxSpeedY = 0 - bounds.y.first
        val maxSpeedX = bounds.x.last
        var count = 0
        for (speedY in -maxSpeedY..maxSpeedY) {
            for (speedX in 0..maxSpeedX) {
                var x = 0
                var y = 0
                var spdX = speedX
                var spdY = speedY
                while (true) {
                    x += spdX
                    y += spdY
                    if (x > bounds.x.last || y < bounds.y.first) break
                    if (x in bounds.x && y in bounds.y) {
                        count++
                        break
                    }
                    spdX = max(0, spdX - 1)
                    spdY -= 1
                }
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(parseInput(testInput.first())) == 45)
    check(part2(parseInput(testInput.first())) == 112)

    val input = readInput("Day17")
    println(part1(parseInput(input.first())))
    println(part2(parseInput(input.first())))
}
