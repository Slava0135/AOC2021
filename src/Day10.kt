fun main() {

    val pairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')

    val scoresIllegal = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    fun part1(input: List<String>): Int {
        val stack = ArrayDeque<Char>()
        var score = 0
        for (line in input) {
            stack.clear()
            for (sym in line) {
                if (sym in pairs.keys) {
                    stack.addFirst(sym)
                } else {
                    val top = stack.removeFirst()
                    if (pairs[top] != sym) {
                        score += scoresIllegal[sym]!!
                        break
                    }
                }
            }
            stack.clear()
        }
        return score
    }

    val scoresMissing = mapOf('(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L)
    fun part2(input: List<String>): Long {
        val stack = ArrayDeque<Char>()
        val scores = mutableListOf<Long>()
        loop@ for (line in input) {
            stack.clear()
            for (sym in line) {
                if (sym in pairs.keys) {
                    stack.addFirst(sym)
                } else {
                    val top = stack.removeFirst()
                    if (pairs[top] != sym) {
                        continue@loop
                    }
                }
            }
            if (stack.isNotEmpty()) {
                val score = stack.fold(0L) { acc, it -> 5 * acc + scoresMissing[it]!! }
                scores.add(score)
            }
        }
        scores.sort()
        return scores[(scores.size - 1) / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
