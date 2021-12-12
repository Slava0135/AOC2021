fun main() {

    fun parseEdges(input: List<String>): Map<String, Set<String>> {
        val edges = mutableMapOf<String,MutableSet<String>>()
        for (line in input) {
            val (v1, v2) = line.split("-")
            edges.getOrPut(v1) { mutableSetOf(v2) }.add(v2)
            edges.getOrPut(v2) { mutableSetOf(v1) }.add(v1)
        }
        return edges
    }

    fun part1(input: List<String>): Int {
        val edges = parseEdges(input)
        val visited = mutableSetOf<String>()
        fun findPaths(v: String): Int {
            if (v == "end") return 1
            val verticesLeft = edges[v]!! - visited
            if (v.all { it.isLowerCase() }) visited.add(v)
            var paths = 0
            for (next in verticesLeft) {
                paths += findPaths(next)
            }
            visited.remove(v)
            return paths
        }
        return findPaths("start")
    }

    fun part2(input: List<String>): Int {
        val edges = parseEdges(input)
        var smallVisitedTwice: String? = null
        val visited = mutableSetOf<String>()
        fun findPaths(v: String): Int {
            if (v == "end") return 1
            if (v.all { it.isLowerCase() }) visited.add(v)
            var paths = 0
            for (next in edges[v]!!) {
                if (next !in visited) {
                    paths += findPaths(next)
                } else if (smallVisitedTwice == null && next != "start") {
                    smallVisitedTwice = next
                    paths += findPaths(next)
                    smallVisitedTwice = null
                }
            }
            if (v != smallVisitedTwice) visited.remove(v)
            return paths
        }
        return findPaths("start")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
