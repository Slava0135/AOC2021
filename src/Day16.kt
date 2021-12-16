fun main() {

    data class Packet(val version: Int, val ID: Int, val other: String)
    data class Literal(val value: Long, val bitsRead: Int)

    fun parsePacket(input: String): Packet {
        return Packet(
            input.substring(0..2).toInt(2),
            input.substring(3..5).toInt(2),
            input.substring(6)
        )
    }

    fun parseLiteral(input: String): Literal {
        var binary = ""
        var bitsRead = 0
        for (group in input.chunked(5)) {
            binary += group.substring(1)
            bitsRead += 5
            if (group.first() == '0') break
        }
        return Literal(binary.toLong(2), bitsRead)
    }

    fun part1(input: List<String>): Int {
        val binary = input[0].map {
            it.digitToInt(16).toString(2).padStart(4, '0')
        }.joinToString("")

        var versionSum = 0
        var leftover = binary

        do {
            val p = parsePacket(leftover)
            versionSum += p.version
            leftover = p.other

            leftover = if (p.ID == 4) {
                val l = parseLiteral(p.other)
                leftover.drop(l.bitsRead)
            } else {
                if (leftover.first() == '0') {
                    //val bits = leftover.substring(1..15).toInt(2)
                    leftover.drop(16)
                } else {
                    //val packets = leftover.substring(1..11).toInt(2)
                    leftover.drop(12)
                }
            }
        } while ('1' in leftover)
        return versionSum
    }

    fun part2(input: String): Long {
        val binary = input.map {
            it.digitToInt(16).toString(2).padStart(4, '0')
        }.joinToString("")

        fun parse(binary: String): Literal {
            val values = mutableListOf<Long>()

            var innerBits = 0
            var outerBits = 0
            var packetsRead = 0

            var bits: Int? = null
            var packets: Int? = null

            var leftover = binary

            val p = parsePacket(leftover)
            leftover = p.other
            outerBits += 6
            if (p.ID == 4) {
                val l = parseLiteral(leftover)
                return Literal(l.value, outerBits + l.bitsRead)
            } else {
                outerBits++
                if (leftover.first() == '0') {
                    bits = leftover.substring(1..15).toInt(2)
                    leftover = leftover.drop(1 + 15)
                    outerBits += 15
                } else {
                    packets = leftover.substring(1..11).toInt(2)
                    leftover = leftover.drop(1 + 11)
                    outerBits += 11
                }
            }

            while (true) {
                val lit = parse(leftover)
                values.add(lit.value)
                innerBits += lit.bitsRead
                leftover = leftover.drop(lit.bitsRead)
                packetsRead++
                if (bits != null && innerBits >= bits) break
                if (packets != null && packetsRead >= packets) break
            }
            val v = when (p.ID) {
                0 -> values.sum()
                1 -> values.fold(1L) { acc, l -> acc * l }
                2 -> values.minOf { it }
                3 -> values.maxOf { it }
                5 -> if (values[0] > values[1]) 1 else 0
                6 -> if (values[0] < values[1]) 1 else 0
                7 -> if (values[0] == values[1]) 1 else 0
                else -> 0
            }
            return Literal(v, innerBits + outerBits)
        }

        return parse(binary).value
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 31)
    val testInput2 = readInput("Day16_test2")
    check(part2(testInput2[0]) == 3L)
    check(part2(testInput2[1]) == 54L)
    check(part2(testInput2[2]) == 7L)
    check(part2(testInput2[3]) == 9L)
    check(part2(testInput2[4]) == 1L)
    check(part2(testInput2[5]) == 0L)
    check(part2(testInput2[6]) == 0L)
    check(part2(testInput2[7]) == 1L)


    val input = readInput("Day16")
    println(part1(input))
    println(part2(input[0]))
}
