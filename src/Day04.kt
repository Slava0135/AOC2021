fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(WinGame(testInput).solve() == 4512)
    check(LoseGame(testInput).solve() == 1924)

    val input = readInput("Day04")
    println(WinGame(input).solve())
    println(LoseGame(input).solve())
}

const val size = 5

abstract class Game(lines: List<String>) {
    protected val numbers: Iterator<Int>
    protected val boards: List<Board>

    init {
        numbers = lines.first().split(",").map { it.toInt() }.iterator()
        val mBoards = mutableListOf<Board>()
        for (i in 2 until lines.size step (size + 1)) {
            mBoards.add(Board(lines.subList(i, i + size)))
        }
        boards = mBoards.toList()
    }

    abstract fun solve(): Int
}

class WinGame(lines: List<String>) : Game(lines) {

    override fun solve(): Int {
        while (numbers.hasNext()) {
            val num = numbers.next()
            for (board in boards) {
                if (board.markNumber(num)) {
                    return board.getScore(num)
                }
            }
        }
        return 0
    }
}

class LoseGame(lines: List<String>) : Game(lines) {

    override fun solve(): Int {
        val boards = boards.toMutableList()
        while (numbers.hasNext()) {
            val num = numbers.next()
            if (boards.size > 1) {
                boards.removeAll { it.markNumber(num) }
            } else {
                if (boards.first().markNumber(num)) {
                    return boards.first().getScore(num)
                }
            }
        }
        return 0
    }
}

class Board(lines: List<String>) {

    private val numbers = Array(size) { IntArray(size) }
    private val marked = Array(size) { BooleanArray(size) { false } }

    init {
        for (i in 0 until size) {
            val nums = lines[i].trimStart().split("""\s+""".toRegex()).map { it.toInt() }
            for (j in 0 until size) {
                numbers[i][j] = nums[j]
            }
        }
    }

    fun markNumber(num: Int): Boolean {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (numbers[i][j] == num) {
                    marked[i][j] = true
                    if (checkRow(i) || checkCol(j)) return true
                }
            }
        }
        return false
    }

    private fun checkRow(row: Int): Boolean {
        for (i in 0 until size) {
            if (!marked[row][i]) return false
        }
        return true
    }

    private fun checkCol(col: Int): Boolean {
        for (i in 0 until size) {
            if (!marked[i][col]) return false
        }
        return true
    }

    fun getScore(lastNum: Int): Int {
        var sum = 0
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (!marked[i][j]) {
                    sum += numbers[i][j]
                }
            }
        }
        return lastNum * sum
    }
}