package lab1

import common.ciphers.Cipher

class ShuffleCipher(
    private val key1: List<Int>,
    private val key2: List<Int>
) : Cipher {

    var rows = key1.size
    var cols = key2.size

    init {
        rows = key1.size
        cols = key2.size
    }

    override fun encode(text: String): String {
        val matrix = Array(rows) { CharArray(cols) { ' ' } }
        var index = 0

        for (i in key2.indices) {
            for (j in key1.indices) {
                if (index < text.length) {
                    matrix[i][key1[j]-1] = text[index++]
                }
            }
        }
        val encodedText = StringBuilder()
        for (i in key1.indices) {
            for (j in key2.indices) {
                encodedText.append(matrix[key2[j]-1][i])
            }
        }

        return encodedText.toString()
    }

    override fun decode(text: String): String {
        val matrix = Array(rows) { CharArray(cols) { ' ' } }
        var index = 0

        for (i in key1.indices) {
            for (j in key2.indices) {
                if (index < text.length) {
                    matrix[key2[j]-1][i] = text[index++]
                }
            }
        }
        val decodedText = StringBuilder()
        for (i in key2.indices) {
            for (j in key1.indices) {
                decodedText.append(matrix[i][key1[j]-1])
            }
        }

        return decodedText.toString()
    }

}