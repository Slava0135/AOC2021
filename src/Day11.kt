fun main() {

    class Point(val x: Int, val y: Int)
    val neighbours = listOf(
        Point(-1, -1), Point(-1, 0), Point(0, -1),
        Point(1, -1), Point(-1, 1),
        Point(1, 1), Point(1, 0), Point(0, 1))
    val steps = 100

    fun part1(input: List<String>): Int {
        val grid =  input.map { it.map { it.digitToInt() }.toMutableList() }.toMutableList()
        var flashes = 0
        val h = grid.size
        val w = grid.first().size
        repeat(steps) {
            for (i in 0 until h) {
                for (j in 0 until w) {
                    grid[i][j]++
                }
            }
            var flashed: Boolean
            do {
                flashed = false
                for (i in 0 until h) {
                    for (j in 0 until w) {
                        if (grid[i][j] > 9) {
                            flashed = true
                            flashes++
                            grid[i][j] = 0
                            for (n in neighbours) {
                                val pos = Point(i + n.x, j + n.y)
                                if (pos.x in 0 until h && pos.y in 0 until w) {
                                    if (grid[pos.x][pos.y] in 1..9) {
                                        grid[pos.x][pos.y]++
                                    }
                                }
                            }
                        }
                    }
                }
            } while (flashed)
        }
        return flashes
    }

    fun part2(input: List<String>): Int {
        val grid =  input.map { it.map { it.digitToInt() }.toMutableList() }.toMutableList()
        var step = 0
        val h = grid.size
        val w = grid.first().size
        do {
            step++
            for (i in 0 until h) {
                for (j in 0 until w) {
                    grid[i][j]++
                }
            }
            var flashed: Boolean
            do {
                flashed = false
                for (i in 0 until h) {
                    for (j in 0 until w) {
                        if (grid[i][j] > 9) {
                            flashed = true
                            grid[i][j] = 0
                            for (n in neighbours) {
                                val pos = Point(i + n.x, j + n.y)
                                if (pos.x in 0 until h && pos.y in 0 until w) {
                                    if (grid[pos.x][pos.y] in 1..9) {
                                        grid[pos.x][pos.y]++
                                    }
                                }
                            }
                        }
                    }
                }
            } while (flashed)
        } while (grid.any { it.any { it != 0 } })
        return step
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
