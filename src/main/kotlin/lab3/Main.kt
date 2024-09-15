package lab3

fun extendedGcd(r1: Int, r2: Int, s1: Int = 1, s2: Int = 0, t1: Int = 0, t2: Int = 1): Triple<Int, Int, Int> {
    return if (r2 == 0) {
        Triple(r1, s1, t1)
    } else {
        val q = r1 / r2
        val r = r1 % r2
        val s = s1 - s2 * q
        val t = t1 - t2 * q
        extendedGcd(r2, r, s2, s, t2, t)
    }
}

fun modExp(base: Int, exp: Int, mod: Int): Int {
    var result = 1
    var b = base % mod
    var e = exp
    while (e > 0) {
        if (e % 2 == 1) result = (result * b) % mod
        b = (b * b) % mod
        e /= 2
    }
    return result
}

fun generateKeyPair(p: Int, q:Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
    val n = p * q
    val phi = (p - 1) * (q - 1)
    val e = generateSequence(2) { it + 1 }
        .first { gcd(it, phi) == 1 }
    val (gcd, x, _) = extendedGcd(e, phi)
    val d = if (gcd == 1) (x % phi + phi) % phi else throw IllegalArgumentException("No modular inverse exists")
    return Pair(Pair(e, n), Pair(d, n))
}

fun gcd(a: Int, b: Int): Int {
    return if (b == 0) a else gcd(b, a % b)
}

fun encrypt(publicKey: Pair<Int, Int>, data: ByteArray): IntArray {
    val (e, n) = publicKey
    return data.map { modExp(it.toInt(), e, n) }.toIntArray()
}

fun decrypt(privateKey: Pair<Int, Int>, data: IntArray): ByteArray {
    val (d, n) = privateKey
    return data.map { modExp(it, d, n).toByte() }.toByteArray()
}

fun main() {
    print("Input p: ")
    val p: Int = readLine()?.toInt() ?: 0

    print("Input q: ")
    val q: Int = readLine()?.toInt() ?: 0

    val (publicKey, privateKey) = generateKeyPair(p, q)
    println("Public key: $publicKey; Private key: $privateKey")

    print("Input text: ")
    val text = readLine() ?: ""
    val textBytes = text.toByteArray()

    val encryptedMessage = encrypt(publicKey, textBytes)
    println("Encoded message: ${encryptedMessage.joinToString(", ")}")

    val decryptedMessage = decrypt(privateKey, encryptedMessage).toString(Charsets.UTF_8)
    println("Decoded message: $decryptedMessage")
}
