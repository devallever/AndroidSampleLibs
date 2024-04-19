package app.allever.android.sample.safe

import android.util.Base64
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.safe.databinding.FragmentGzipAesBase64Binding
import app.allever.android.sample.safe.util.AesUtil
import app.allever.android.sample.safe.util.Base64Util
import app.allever.android.sample.safe.util.GzipUtil
import app.allever.android.sample.safe.util.decode2Base64ByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class GzipAesBase64Fragment : BaseFragment<FragmentGzipAesBase64Binding, BaseViewModel>() {
    override fun inflate() = FragmentGzipAesBase64Binding.inflate(layoutInflater)

    override fun init() {
        val secretKey = "1234567890123456"
        val iv = "0987654321098765"

        val originContent = "Hello world!"
        mBinding.apply {
            tvOrigin.text = "原字符串：${originContent}"

            var gzipResult = ""
            var unGzipResult = ""
            btnGzipYaSuo.setOnClickListener {
                lifecycleScope.launch {
                    gzipResult = compressAndEncryptString(originContent,secretKey, iv)?:""
                    tvGzipJieYaResult.text = "压缩后：$gzipResult"
                }
            }

            btnUnGzip.setOnClickListener {
                lifecycleScope.launch {
                    unGzipResult = decryptAndUnzipString(gzipResult, secretKey, iv)?:""
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

    suspend fun compressAndEncryptString(input: String, secretKey: String, iv: String) = withContext(Dispatchers.IO) {
        try {
            // 压缩字符串
            val compressedBytes = GzipUtil.gzipString(input)

            // 使用AES加密
            val encryptedBytes = AesUtil.aesEncode(compressedBytes?: return@withContext null, secretKey, iv)

            // 加密后的字符串
            return@withContext Base64Util.encode2String(encryptedBytes?: return@withContext null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext ""
    }

    suspend fun decryptAndUnzipString(content: String, secretKey: String, iv: String) = withContext(Dispatchers.IO) {
        try {
            // 解码Base64编码的字符串
            val decodedBytes = content.decode2Base64ByteArray()

            // 解密数据
            val decryptedBytes = AesUtil.aesDecode(decodedBytes, secretKey, iv)

            //解压缩
            return@withContext GzipUtil.unGzipString(decryptedBytes?:return@withContext null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext ""
    }



}