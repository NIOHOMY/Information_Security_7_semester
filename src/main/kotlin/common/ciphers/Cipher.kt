package common.ciphers

interface Cipher {

    fun encode(text: String): String

    fun decode(text: String): String

}