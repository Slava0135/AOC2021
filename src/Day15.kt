import java.util.*
import kotlin.math.abs

fun main() {

    data class Position(val x: Int, val y: Int) {
        infix operator fun plus(other: Position): Position {
            return Position(x + other.x, y + other.y)
        }
    }
    data class Node(val pos: Position, val heuristicScore: Int, val realScore: Int) : Comparable<Node> {
        override fun compareTo(other: Node): Int {
            return heuristicScore.compareTo(other.heuristicScore)
        }
    }

    fun computeHeuristic(pos: Position, target: Position): Int {
        val dx = abs(pos.x - target.x)
        val dy = abs(pos.y - target.y)
        return dx + dy
    }

    val around = listOf(Position(-1, 0), Position(1, 0), Position(0, -1), Position(0, 1))

    fun solve(risks: List<List<Int>>): Int {
        val width = risks.size
        val height = risks.first().size

        val scores = Array(width) { IntArray(height) { Int.MAX_VALUE } }
        scores[0][0] = 0

        val start = Position(0, 0)
        val end = Position(width - 1, height - 1)

        val nodes = PriorityQueue(listOf(Node(start, 0, 0)))

        var node = nodes.poll()
        while (node != null && node.pos != end) {
            for (p in around) {
                val newPos = node.pos + p
                if (newPos.x in 0 until width && newPos.y in 0 until height) {
                    val realScore = node.realScore + risks[newPos.x][newPos.y]
                    if (scores[newPos.x][newPos.y] > realScore) {
                        scores[newPos.x][newPos.y] = realScore
                        val heuristicScore = realScore + computeHeuristic(newPos, end)
                        nodes.add(Node(newPos, heuristicScore, realScore))
                    }
                }
            }
            node = nodes.poll()
        }
        return scores[end.x][end.y]
    }

    fun part1(input: List<String>): Int {
        val risks = input.map { it.map { it.digitToInt() } }
        return solve(risks)
    }

    fun extend(input: List<String>): List<String> {
        val n = 5
        val width = input.size
        val height = input.first().length

        val tile = List(n * width) { i -> input.map { it.repeat(n) }[i % width] }
        val risks = tile.map { it.map { it.digitToInt() }.toMutableList() }
        for (x in 0 until n * width) {
            for (y in 0 until n * height) {
                val offset = x / width + y / height
                risks[x][y] += offset
                if (risks[x][y] > 9) {
                    risks[x][y] %= 10
                    risks[x][y]++
                }
            }
        }

        return risks.map { it.joinToString("") }
    }

    fun part2(input: List<String>): Int {
        val risks = extend(input).map { it.map { it.digitToInt() } }
        return solve(risks)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    // test field extension
    check(extend(testInput) == readInput("Day15_scale_test"))

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
