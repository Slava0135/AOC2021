fun main() {

    fun printGrid(g: Array<BooleanArray>) {
        for (y in g.first().indices) {
            for (x in g.indices) {
                if (g[x][y]) print("#") else print(".")
            }
            println()
        }
        println()
    }

    fun part1(input: List<String>): Int {
        val sep = input.indexOfFirst { it.isEmpty() }
        val dots = input.subList(0, sep).map { it.split(",").map { it.toInt() } }
        val folds = input.subList(sep + 1, input.size).map {
                it.split(" ").drop(2).first().split("=")
            }

        var width: Int
        var height: Int
        if (folds[0][0] == "x") {
            width = 2 * folds[0][1].toInt() + 1
            height = 2 * folds[1][1].toInt() + 1
        } else {
            height = 2 * folds[0][1].toInt() + 1
            width = 2 * folds[1][1].toInt() + 1
        }

        var grid = Array(width) { BooleanArray(height) { false } }
        for (dot in dots) {
            grid[dot[0]][dot[1]] = true
        }

        if (folds[0][0] == "y") {
            val oldHeight = height
            height /= 2
            grid = Array(width) { x -> BooleanArray(height) { y -> grid[x][y] || grid[x][oldHeight - y - 1] } }
        } else {
            val oldWidth = width
            width /= 2
            grid = Array(width) { x -> BooleanArray(height) { y -> grid[x][y] || grid[oldWidth - x - 1][y] } }
        }
        return grid.fold(0) { acc, booleans -> acc + booleans.count { it } }
    }

    fun part2(input: List<String>): Int {
        val sep = input.indexOfFirst { it.isEmpty() }
        val dots = input.subList(0, sep).map { it.split(",").map { it.toInt() } }
        val folds = input.subList(sep + 1, input.size).map {
            it.split(" ").drop(2).first().split("=")
        }

        var width: Int
        var height: Int
        if (folds[0][0] == "x") {
            width = 2 * folds[0][1].toInt() + 1
            height = 2 * folds[1][1].toInt() + 1
        } else {
            height = 2 * folds[0][1].toInt() + 1
            width = 2 * folds[1][1].toInt() + 1
        }

        var grid = Array(width) { BooleanArray(height) { false } }
        for (dot in dots) {
            grid[dot[0]][dot[1]] = true
        }

        for (fold in folds) {
            if (fold[0] == "y") {
                val oldHeight = height
                height /= 2
                grid = Array(width) { x -> BooleanArray(height) { y -> grid[x][y] || grid[x][oldHeight - y - 1] } }
            } else {
                val oldWidth = width
                width /= 2
                grid = Array(width) { x -> BooleanArray(height) { y -> grid[x][y] || grid[oldWidth - x - 1][y] } }
            }
        }

        printGrid(grid)
        return grid.fold(0) { acc, booleans -> acc + booleans.count { it } }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    check(part2(testInput) == 16)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
