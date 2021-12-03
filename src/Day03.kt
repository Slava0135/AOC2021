fun main() {
    fun part1(input: List<String>): Int {
        val len = input.first().length
        val rate = IntArray(len)
        for (line in input) {
            for (i in line.indices) {
                if (line[i] == '1') {
                    rate[i]++
                }
            }
        }
        rate.reverse()
        var gamma = 0
        var epsilon = 0
        for (i in rate.indices) {
            if (2 * rate[i] > input.size) {
                gamma += 1 shl i
            } else {
                epsilon += 1 shl i
            }
        }
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val len = input.first().length

        val readings = input.toMutableList()
        for (i in 0 until len) {
            val ones = readings.count { it[i] == '1' }
            if (2 * ones >= readings.size) {
                readings.removeAll { it[i] == '0' }
            } else {
                readings.removeAll { it[i] == '1' }
            }
            if (readings.size == 1) break
        }
        val oxygenRating = Integer.parseInt(readings.first(), 2)

        readings.clear()
        readings.addAll(input)
        for (i in 0 until len) {
            val ones = readings.count { it[i] == '1' }
            if (2 * ones >= readings.size) {
                readings.removeAll { it[i] == '1' }
            } else {
                readings.removeAll { it[i] == '0' }
            }
            if (readings.size == 1) break
        }
        val CO2_Rating = Integer.parseInt(readings.first(), 2)

        return oxygenRating * CO2_Rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
