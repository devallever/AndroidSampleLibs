package app.allever.android.sample.safe.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesUtil {

    suspend fun aesDecode(contentByteArray: ByteArray, secretKey: String, iv: String) = withContext(
        Dispatchers.IO
    ) {
        try {
            val secretKeySpec = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(iv.toByteArray(Charsets.UTF_8))

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)

            return@withContext cipher.doFinal(contentByteArray)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }

    fun aesDecodeSync(contentByteArray: ByteArray, secretKey: String, iv: String): ByteArray? {
        try {
            val secretKeySpec = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(iv.toByteArray(Charsets.UTF_8))

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)

            return cipher.doFinal(contentByteArray)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun aesEncode(contentByteArray: ByteArray, secretKey: String, iv: String) = withContext(
        Dispatchers.IO
    ) {
        try {
            // 使用AES加密
            val secretKeySpec = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
            val ivSpec = IvParameterSpec(iv.toByteArray(Charsets.UTF_8))
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
            return@withContext cipher.doFinal(contentByteArray)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }
}