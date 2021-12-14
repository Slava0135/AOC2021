fun main() {

    fun parse(input: List<String>): Pair<String, Map<String, Char>> {
        val template = input.first()
        val rules = mutableMapOf<String, Char>()
        for (rule in input.drop(2)) {
            val (k, v) = rule.split(" -> ")
            rules[k] = v.first()
        }
        return Pair(template, rules)
    }

    fun part1(input: List<String>): Int {
        var (template, rules) = parse(input)
        repeat(10) {
            val newTemplate = CharArray(template.length + template.length - 1)
            var index = 0
            for (i in template.indices) {
                newTemplate[index++] = template[i]
                if (i < template.length - 1) {
                    rules["${template[i]}${template[i+1]}"]?.let {
                        newTemplate[index++] = it
                    }
                }
            }
            template = newTemplate.concatToString()
        }
        val rates = template.toSet().map { Pair(it, template.count { c -> c == it }) }
        return rates.maxByOrNull { it.second }!!.second - rates.minByOrNull { it.second }!!.second
    }

    fun part2(input: List<String>): Long {
        val (template, rules) = parse(input)
        val pairs = mutableMapOf<String, Long>()
        for (p in template.windowed(2)) {
            pairs[p] = pairs[p]?.plus(1) ?: 1
        }
        val rates = mutableMapOf<Char, Long>()
        for (c in template) {
            rates[c] = rates[c]?.plus(1) ?: 1
        }
        repeat(40) {
            val newPairs = mutableMapOf<String, Long>()
            for (p in pairs) {
                if (p.value < 1) continue
                rules[p.key]?.let {
                    rates[it] = rates[it]?.plus(p.value) ?: p.value
                    val a = "${p.key.first()}$it"
                    val b = "$it${p.key.last()}"
                    newPairs[p.key] = newPairs[p.key]?.minus(p.value) ?: -p.value
                    newPairs[a] = newPairs[a]?.plus(p.value) ?: p.value
                    newPairs[b] = newPairs[b]?.plus(p.value) ?: p.value
                }
            }
            for (p in newPairs) {
                pairs[p.key] = pairs[p.key]?.plus(p.value) ?: p.value
            }
        }
        return rates.maxByOrNull { it.value }!!.value - rates.minByOrNull { it.value }!!.value
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
