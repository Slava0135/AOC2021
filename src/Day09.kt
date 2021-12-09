fun main() {
    fun part1(input: List<String>): Int {
        val heightmap = input.map { it.map { it.digitToInt() } }
        var riskLevel = 0
        val h = heightmap.size
        val w = heightmap.first().size
        for (i in 0 until h) {
            for (j in 0 until w) {
                val height = heightmap[i][j]
                if (
                    (i-1 < 0 || heightmap[i-1][j] > height) &&
                    (j-1 < 0 || heightmap[i][j-1] > height) &&
                    (i+1 == h || heightmap[i+1][j] > height) &&
                    (j+1 == w || heightmap[i][j+1] > height)
                ) {
                    riskLevel += height + 1
                }
            }
        }
        return riskLevel
    }

    fun part2(input: List<String>): Int {
        val heightmap = input.map { it.map { it.digitToInt() } }
        val h = heightmap.size
        val w = heightmap.first().size

        val flows = MutableList(h) { MutableList(w) { mutableListOf<Pair<Int, Int>>() } }
        for (i in 0 until h) {
            for (j in 0 until w) {
                val height = heightmap[i][j]
                if (height == 9) continue
                if (i-1 >= 0 && heightmap[i-1][j] < height) {
                    flows[i-1][j].add(Pair(i, j))
                } else if (j-1 >= 0 && heightmap[i][j-1] < height) {
                    flows[i][j-1].add(Pair(i, j))
                } else if (i+1 < h && heightmap[i+1][j] < height) {
                    flows[i+1][j].add(Pair(i, j))
                } else if (j+1 < w && heightmap[i][j+1] < height) {
                    flows[i][j+1].add(Pair(i, j))
                }
            }
        }

        fun getFlowSize(p: Pair<Int, Int>): Int {
            if (flows[p.first][p.second].isEmpty()) return 1
            return 1 + flows[p.first][p.second].sumOf { getFlowSize(it) }
        }
        val basinSizes = mutableListOf<Int>()
        for (i in 0 until h) {
            for (j in 0 until w) {
                val height = heightmap[i][j]
                if (
                    (i-1 < 0 || heightmap[i-1][j] > height) &&
                    (j-1 < 0 || heightmap[i][j-1] > height) &&
                    (i+1 == h || heightmap[i+1][j] > height) &&
                    (j+1 == w || heightmap[i][j+1] > height)
                ) {
                    basinSizes.add(getFlowSize(Pair(i, j)))
                }
            }
        }
        basinSizes.sort()
        return basinSizes.takeLast(3).fold(1) { it, acc -> it * acc }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
