package app.allever.android.sample.safe

import android.util.Base64
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.safe.databinding.FragmentGzipAesBase64Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class GzipAesBase64Fragment : BaseFragment<FragmentGzipAesBase64Binding, BaseViewModel>() {
    override fun inflate() = FragmentGzipAesBase64Binding.inflate(layoutInflater)

    override fun init() {
        val originContent = "Hello world!"
        mBinding.apply {
            tvOrigin.text = "原字符串：${originContent}"

            var gzipResult = ""
            var unGzipResult = ""
            btnGzipYaSuo.setOnClickListener {
                lifecycleScope.launch {
                    gzipResult = compressAndEncryptString(originContent,"1234567890123456")
                    tvGzipJieYaResult.text = "压缩后：$gzipResult"
                }
            }

            btnUnGzip.setOnClickListener {
                lifecycleScope.launch {
                    unGzipResult = decryptAndUnzipString(gzipResult, "1234567890123456")
                    tvUnGzipResult.text = "解压缩后：$unGzipResult"
                }
            }

        }
    }


    suspend fun compressString(input: String) = withContext(Dispatchers.IO) {
        try {
            val byteOutputStream = ByteArrayOutputStream()
            val gzipOutputStream = GZIPOutputStream(byteOutputStream)
            val inputStream = input.byteInputStream()

            inputStream.copyTo(gzipOutputStream)

            inputStream.close()
            gzipOutputStream.close()

            val compressedBytes = byteOutputStream.toByteArray()
            return@withContext Base64.encodeToString(compressedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext ""
    }

    suspend fun gzipDecompress(compressedString: String) = withContext(Dispatchers.IO) {
        try {
            val compressed = Base64.decode(compressedString, Base64.DEFAULT)
            val inputStream = ByteArrayInputStream(compressed)
            val gzipInputStream = GZIPInputStream(inputStream)
            val outputStream = ByteArrayOutputStream()

            val buffer = ByteArray(1024)
            var len = gzipInputStream.read(buffer)
            while (len != -1) {
                outputStream.write(buffer, 0, len)
                len = gzipInputStream.read(buffer)
            }

            return@withContext String(outputStream.toByteArray(), Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return@withContext ""

    }

    suspend fun compressAndEncryptString(input: String, key: String) = withContext(Dispatchers.IO) {
        try {
            // 压缩字符串
            val compressedBytes = ByteArrayOutputStream().use { byteOut ->
                GZIPOutputStream(byteOut).use { gzipOut ->
                    gzipOut.write(input.toByteArray())
                }
                byteOut.toByteArray()
            }

            // 将压缩后的字节转换为Base64字符串
            val compressedString = Base64.encodeToString(compressedBytes, Base64.DEFAULT)

            // 使用AES加密
            val secretKeySpec = SecretKeySpec(key.toByteArray(charset("utf-8")), "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            val encryptedBytes = cipher.doFinal(compressedBytes)

            // 加密后的字符串
            return@withContext Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext ""
    }

    suspend fun decryptAndUnzipString(encryptedString: String, key: String) = withContext(Dispatchers.IO) {
        try {
            // 解码Base64编码的字符串
            val decodedBytes = Base64.decode(encryptedString, Base64.DEFAULT)

            // 创建AES解密密钥
            val secretKeySpec = SecretKeySpec(key.toByteArray(charset("utf-8")), "AES")

            // 实例化Cipher
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)

            // 解密数据
            val decryptedBytes = cipher.doFinal(decodedBytes)

            // 解压缩数据
            val outputStream = ByteArrayOutputStream()
            val gzipInputStream = GZIPInputStream(ByteArrayInputStream(decryptedBytes))
            val buffer = ByteArray(1024)
            var bytesRead = gzipInputStream.read(buffer)
            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bytesRead)
                bytesRead = gzipInputStream.read(buffer)
            }
            gzipInputStream.close()

            // 将字节转换为字符串
            return@withContext String(outputStream.toByteArray(), Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext ""
    }

}