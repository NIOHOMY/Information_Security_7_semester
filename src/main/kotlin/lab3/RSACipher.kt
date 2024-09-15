package lab3

class RSACipher(
    private val p: Int,
    private val q: Int
): IRSACipher {

    private var publicKey: Pair<Int, Int>
    private var secretKey: Pair<Int, Int>

    init {
        val (keyPublic, keySecret) = generateKeyPair(p, q)
        publicKey = keyPublic
        secretKey = keySecret
    }

    override fun getPublicKey() : Pair<Int, Int> = publicKey

    override fun getSecretKey() : Pair<Int, Int> = secretKey

    override fun encrypt(publicKey: Pair<Int, Int>, text: String): IntArray {
        val data = text.toByteArray()
        val (e, n) = publicKey
        return data.map { modularExponentiation(it.toInt(), e, n) }.toIntArray()
    }

    override fun decrypt(privateKey: Pair<Int, Int>, data: IntArray): String {
        val (d, n) = privateKey
        return data.map { modularExponentiation(it, d, n).toByte() }.toByteArray().toString(Charsets.UTF_8)
    }

    /**
     * This function is used to find the greatest common divisor **(GCD)** of two numbers
     *
     * It calculates the coefficients that allow to express the GCD as a **linear combination of these numbers**
     *
     * It is needed to find the modular inverse of a number
     *
     * @return Triple<Int, Int, Int> - **linear combination of these numbers**
     */
    private fun findGreatestCommonDivisorAsLinearCombination(
        r1: Int,
        r2: Int,
        s1: Int = 1,
        s2: Int = 0,
        t1: Int = 0,
        t2: Int = 1
    ): Triple<Int, Int, Int> {
        return if (r2 == 0) {
            Triple(r1, s1, t1)
        } else {
            val q = r1 / r2
            val r = r1 % r2
            val s = s1 - s2 * q
            val t = t1 - t2 * q
            findGreatestCommonDivisorAsLinearCombination(r2, r, s2, s, t2, t)
        }
    }

    /**
     * This function calculates the value of **base^exp%mod** effectively using the fast degree expansion method.
     *
     * It uses the bitwise method to speed up calculations: if the current degree is odd, multiply the result by the current value of base and take the modulus.
     *
     * The base is squared at each step, and the degree is divided by two. This allows you to quickly calculate a large number raised to a degree.
     *
     * - **Used to encrypt and decrypt text**
     *
     * @return **base^exp%mod**
     */
    private fun modularExponentiation(base: Int, exp: Int, mod: Int): Int {
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

    /**
     * This function creates a key pair for the RSA algorithm
     *
     * - **n** is the product of two prime numbers p and q, used as a module
     * - **phi** is the Euler function for n, important for computing the inverse element
     * - **e** is a public exponent, chosen to be mutually prime with phi
     * - **d** is the inverse of e modulo phi
     * @return Pair<Pair<Int, Int>, Pair<Int, Int>> key pair **Public** and **Secret**
     */
    private fun generateKeyPair(p: Int, q: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val n = p * q
        val phi = (p - 1) * (q - 1)
        val e =
            generateSequence(2) { it + 1 }.first { findGreatestCommonDivisor(it, phi) == 1 }
        val (findGreatestCommonDivisor, x, _) = findGreatestCommonDivisorAsLinearCombination(e, phi)
        val d =
            if (findGreatestCommonDivisor == 1) (x % phi + phi) % phi
            else throw IllegalArgumentException("No modular inverse exists")
        return Pair(Pair(e, n), Pair(d, n))
    }

    /**
     * This function is used to find the greatest common divisor **(GCD)** of two numbers
     */
    private fun findGreatestCommonDivisor(a: Int, b: Int): Int {
        return if (b == 0) a else findGreatestCommonDivisor(b, a % b)
    }

}