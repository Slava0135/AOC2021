fun main() {

    fun part1(input: List<String>): Int {
        val easyDigits = intArrayOf(2, 3, 4, 7) // 1, 7, 4, 8
        var digits = 0
        for (line in input) {
            digits += line.split(" | ")[1].split(" ").count { it.length in easyDigits }
        }
        return digits
    }

    // 1,4,7,8
    // 0-6-9: 4 in 9, 7 in 0, 6 is left
    // 2-3-5: 1 in 3, 5 in 6, 2 is left
    val segments = listOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")
    fun part2(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val temp = line.split(" | ")
            val patterns = temp[0].split(" ")
            val output = temp[1].split(" ")

            val table = Array(10) { "" }
            for (n in listOf(1, 4, 7, 8)) {
                table[n] = patterns.find { it.length == segments[n].length }!!
            }

            table[9] = patterns.find { d -> d !in table && d.length == 6 && table[4].all { it in d } }!!
            table[0] = patterns.find { d -> d !in table && d.length == 6 && table[7].all { it in d } }!!
            table[6] = patterns.find { d -> d !in table && d.length == 6 }!!

            table[3] = patterns.find { d -> d !in table && d.length == 5 && table[1].all { it in d } }!!
            table[5] = patterns.find { d -> d !in table && d.length == 5 && d.all { it in table[6] } }!!
            table[2] = patterns.find { d -> d !in table && d.length == 5 }!!

            val digits = List(output.size) {
                    i -> table.indexOfFirst { d -> output[i].length == d.length && output[i].all { it in d } }
            }.joinToString("")
            sum += digits.toInt()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
