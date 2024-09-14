package labs.com.lab1

import lab1.Cipher.Cipher
import lab1.Cipher.ShuffleCipher

fun readKey() : List<Int> {
    return readLine()!!.split(" ").map { it.toInt() }
}

fun main() {
    print("Input text: ")
    val text = readLine()!!.trim()

    print("Input first key: ")
    val key1 = readKey()

    print("Input second key: ")
    val key2 = readKey()

    val cipher : Cipher = ShuffleCipher(key1, key2)

    val encoded = cipher.encode(text)
    println("Encoded text: $encoded")

    val decoded = cipher.decode(encoded)
    println("Decoded text: $decoded")
}