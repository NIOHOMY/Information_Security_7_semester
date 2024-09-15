package lab3

fun main() {
    print("Input p: ")
    val p: Int = readlnOrNull()?.toInt() ?: 0

    print("Input q: ")
    val q: Int = readlnOrNull()?.toInt() ?: 0

    val cipherRSA: IRSACipher = RSACipher(p, q)

    val publicKey = cipherRSA.getPublicKey()
    val secretKey = cipherRSA.getSecretKey()
    println("Public key: $publicKey; Secret key: $secretKey")

    print("Input text: ")
    val text = readlnOrNull() ?: ""

    val encryptedMessage = cipherRSA.encrypt(publicKey, text)
    println("Encoded message: ${encryptedMessage.joinToString(", ")}")

    val decryptedMessage = cipherRSA.decrypt(secretKey, encryptedMessage)
    println("Decoded message: $decryptedMessage")
}
