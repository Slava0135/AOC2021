fun main() {

    fun solve(input: List<String>, days: Int): Long {
        val maxAge = 8
        val resetAge = 6

        val ages = LongArray(maxAge + 1)
        val initAges = input.first().split(",").map { it.toInt() }
        for (age in initAges) {
            ages[age]++
        }
        repeat (days) {
            val ready = ages[0]
            for (age in 1..maxAge) {
                ages[age - 1] = ages[age]
            }
            ages[resetAge] += ready
            ages[maxAge] = ready
        }
        return ages.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(solve(testInput, 80) == 5934L)
    check(solve(testInput, 256) == 26984457539L)

    val input = readInput("Day06")
    println(solve(input, 80))
    println(solve(input, 256))
}
