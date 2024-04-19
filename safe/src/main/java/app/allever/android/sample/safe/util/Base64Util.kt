package app.allever.android.sample.safe.util

import android.util.Base64

object Base64Util {
    fun decode2ByteArray(content: String): ByteArray{
        return Base64.decode(content, Base64.DEFAULT)
    }

    fun decode2String(content: String): String {
        return String(decode2ByteArray(content), Charsets.UTF_8)
    }

    fun encode2ByteArray(content: String): ByteArray {
        return Base64.encode(content.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
    }

    fun encode2String(content: String): String {
        return encode2String(content.toByteArray(Charsets.UTF_8))
    }

    fun encode2String(contentByteArray: ByteArray): String {
        return Base64.encodeToString(contentByteArray, Base64.DEFAULT)
    }
}

fun String.decode2Base64String() = Base64Util.decode2String(this)

fun String.decode2Base64ByteArray() = Base64Util.decode2ByteArray(this)

fun String.encode2Base64String(): String {
    return Base64.encodeToString(this.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
}

fun String.encode2Base64ByteArray(): ByteArray {
    return Base64.encode(this.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
}