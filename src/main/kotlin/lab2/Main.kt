package lab2

import java.util.*

class VigenereEncryptor(private val alphabet: List<Char>) {

    fun encode(text: String, key: String): String {
        return text.indices.joinToString("") { i ->
            val textChar = text[i]
            val keyChar = key[i % key.length]
            alphabet[(indexInAlphabet(textChar) + indexInAlphabet(keyChar)) % alphabet.size].toString()
        }
    }

    fun decode(text: String, key: String): String {
        return encode(text, key.map { alphabet[(alphabet.size - indexInAlphabet(it)) % alphabet.size] }.joinToString(""))
    }

    private fun indexInAlphabet(value: Char): Int {
        return alphabet.indexOf(value)
    }
}

fun main() {
    val alphabet = ('A'..'Z').toList()

    val scanner = Scanner(System.`in`)
    print("Input text: ")
    val text = scanner.nextLine().toUpperCase()
    print("Input key: ")
    val key = scanner.nextLine().toUpperCase()

    val encryptor = VigenereEncryptor(alphabet)

    val encoded = encryptor.encode(text, key)
    println("Encoded text: $encoded")

    val decoded = encryptor.decode(encoded, key)
    println("Decoded text: $decoded")
}
