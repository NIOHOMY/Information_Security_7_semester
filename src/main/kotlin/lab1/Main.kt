package labs.com.lab1

fun encode(text: String, key1: List<Int>, key2: List<Int>): String {
    val matrix = Array(key1.size) { CharArray(key2.size) }
    val textLength = text.length
    var index = 0

    // Заполняем матрицу по ключу key1
    for (i in key1.indices) {
        for (j in key2.indices) {
            matrix[i][j] = if (index < textLength) { text[index++] } else { '_' }
        }
    }

    // Считываем матрицу по ключу key2
    val encodedText = StringBuilder()
    for (key in key2) {
        for (i in key1.indices) {
            encodedText.append(matrix[i][key - 1])
        }
    }

    return encodedText.toString()
}

fun decode(text: String, key1: List<Int>, key2: List<Int>): String {
    val rows = key1.size
    val cols = key2.size
    val matrix = Array(rows) { CharArray(cols) }
    val textLength = text.length
    var index = 0

    // Заполняем матрицу по ключу key2
    for (key in key2) {
        for (i in key1.indices) {
            matrix[i][key - 1] = if (index < textLength) { text[index++] } else { ' ' }
        }
    }

    // Считываем матрицу по ключу key1
    val decodedText = StringBuilder()
    for (i in key1) {
        for (j in key2.indices) {
            decodedText.append(matrix[i - 1][j])
        }
    }

    return decodedText.toString()
}

fun main() {
    print("Input text: ")
    val text = readLine()!!.trim()

    print("Input first key: ")
    val key1 = readLine()!!.split(" ").map { it.toInt() }

    print("Input second key: ")
    val key2 = readLine()!!.split(" ").map { it.toInt() }

    val encoded = encode(text, key1, key2)
    println("Encoded text: $encoded")

    val decoded = decode(encoded, key1, key2)
    println("Decoded text: $decoded")
}