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
                    gzipResult = compressString(originContent)
                    tvGzipJieYaResult.text = "压缩后：$gzipResult"
                }
            }

            btnUnGzip.setOnClickListener {
                lifecycleScope.launch {
                    unGzipResult = gzipDecompress(gzipResult)
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
}