import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    fun part1(input: List<String>): Int {
        return VentField(input, true).countOverlaps()
    }

    fun part2(input: List<String>): Int {
        return VentField(input, false).countOverlaps()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

class VentField(input: List<String>, skipDiagonal: Boolean) {

    var maxX = 0
    var maxY = 0
    val grid: Array<IntArray>

    init {
        val lines = input.map { Line(it) }
        grid = Array(maxX + 1) { IntArray(maxY + 1) }
        for (line in lines) {
            if (line.start.x == line.end.x) {
                val x = line.start.x
                for (y in min(line.start.y, line.end.y)..max(line.start.y, line.end.y)) {
                    grid[x][y]++
                }
            } else if (line.start.y == line.end.y) {
                val y = line.start.y
                for (x in min(line.start.x, line.end.x)..max(line.start.x, line.end.x)) {
                    grid[x][y]++
                }
            } else if (!skipDiagonal) {
                val deltaX = if (line.start.x < line.end.x) 1 else -1
                val deltaY = if (line.start.y < line.end.y) 1 else -1
                var x = line.start.x
                var y = line.start.y
                while (true) {
                    grid[x][y]++
                    if (x == line.end.x) break
                    x += deltaX
                    y += deltaY
                }
            }
        }
    }

    fun countOverlaps(): Int {
        var count = 0
        for (x in 0..maxX) {
            for (y in 0..maxY) {
                if (grid[x][y] > 1) {
                    count++
                }
            }
        }
        return count
    }

    inner class Point(input: String) {
        val x: Int
        val y: Int

        init {
            val temp = input.split(",").map { it.toInt() }
            x = temp[0]
            y = temp[1]
            maxX = maxOf(maxX, x)
            maxY = maxOf(maxY, y)
        }

        override fun toString(): String {
            return "x:$x,y:$y"
        }
    }

    inner class Line(input: String) {
        val start: Point
        val end: Point

        init {
            val temp = input.split(" -> ").map { Point(it) }
            start = temp[0]
            end = temp[1]
        }

        override fun toString(): String {
            return "$start -> $end"
        }
    }
}