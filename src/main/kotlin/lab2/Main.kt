package lab2

import lab1.Cipher.Cipher
import lab2.Cipher.VigenereEncryptor
import java.util.*



fun main() {
    val scanner = Scanner(System.`in`)
    print("Input text: ")
    val text = scanner.nextLine().toUpperCase()
    print("Input key: ")
    val key = scanner.nextLine().toUpperCase()

    val encryptor : Cipher = VigenereEncryptor(key)

    val encoded = encryptor.encode(text)
    println("Encoded text: $encoded")

    val decoded = encryptor.decode(encoded)
    println("Decoded text: $decoded")
}
