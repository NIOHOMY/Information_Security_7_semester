package lab2.Cipher

import common.ciphers.Cipher

class VigenereEncryptor(private val key: String) : Cipher {

    private val alphabet: List<Char> = ('A'..'Z').toList()

    override fun encode(text: String): String {
        return getTextByKeyFromTable(text, key)
    }

    override fun decode(text: String): String {
        val decodeKey = key.map { alphabet[(alphabet.size - indexInAlphabet(it)) % alphabet.size] }.joinToString("")
        return getTextByKeyFromTable(text, decodeKey)
    }

    private fun getTextByKeyFromTable(text: String, deKey: String): String {
        return text.indices.joinToString("") { i ->
            val textChar = text[i]
            val keyChar = deKey[i % deKey.length]
            alphabet[(indexInAlphabet(textChar) + indexInAlphabet(keyChar)) % alphabet.size].toString()
        }
    }

    private fun indexInAlphabet(value: Char): Int {
        return alphabet.indexOf(value)
    }

}