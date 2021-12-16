fun main() {

    data class Packet(val version: Int, val ID: Int, val other: String)
    data class Literal(val value: Long, val bitsRead: Int)

    fun parsePacket(input: String): Packet {
        return Packet(
            input.substring(0..2).toInt(2),
            input.substring(3..5).toInt(2),
            input.substring(6))
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

//    fun part2(input: List<String>): Long {
//        val binary = input[0].map {
//            it.digitToInt(16).toString(2).padStart(4, '0')
//        }.joinToString("")
//
//        fun parse(binary: String, ID: Int, bits: Int?, packets: Int?): Literal {
//            val values = mutableListOf<Long>()
//            var leftover = binary
//            var bitsRead = 0
//            if (bits != null) {
//                while (bitsRead < bits) {
//                    val p = parsePacket(leftover)
//                    if (p.ID == 4) {
//                        val l = parseLiteral(p.other)
//                        values.add(l.value)
//                        bitsRead += 6 + l.bitsRead
//                    } else {
//
//                    }
//                }
//            } else {
//
//            }
//            val v = when (ID) {
//                0 -> values.sum()
//                1 -> values.fold(1L) { acc, l -> acc * l }
//                2 -> values.minOf { it }
//                3 -> values.maxOf { it }
//                5 -> if (values[0] < values[1]) 1 else 0
//                6 -> if (values[0] > values[1]) 1 else 0
//                7 -> if (values[0] == values[1]) 1 else 0
//                else -> 0
//            }
//            return Literal(v, bitsRead)
//        }
//
//        return parse(binary, 0,null, 1)
//    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 31)
    //check(part2(testInput) == 5)

    val input = readInput("Day16")
    println(part1(input))
    //println(part2(input))
}
