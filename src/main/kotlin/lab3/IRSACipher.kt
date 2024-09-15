package lab3

interface IRSACipher {

    fun getPublicKey() : Pair<Int, Int>

    fun getSecretKey() : Pair<Int, Int>

    fun encrypt(publicKey: Pair<Int, Int>, text: String): IntArray

    fun decrypt(privateKey: Pair<Int, Int>, data: IntArray): String

}