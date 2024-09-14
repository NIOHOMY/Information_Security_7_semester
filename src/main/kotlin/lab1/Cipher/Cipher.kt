package lab1.Cipher

interface Cipher {

    fun encode(text: String): String

    fun decode(text: String): String

}