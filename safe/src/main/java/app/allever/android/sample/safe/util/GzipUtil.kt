package app.allever.android.sample.safe.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

object GzipUtil {

    suspend fun gzipString(content: String) = withContext(Dispatchers.IO) {
        try {
            val compressedBytes = ByteArrayOutputStream().use { byteOut ->
                GZIPOutputStream(byteOut).use { gzipOut ->
                    gzipOut.write(content.toByteArray(Charsets.UTF_8))
                }
                byteOut.toByteArray()
            }
            return@withContext compressedBytes
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }

    suspend fun unGzipString(contentByteArray: ByteArray) = withContext(Dispatchers.IO) {
        try {
            val outputStream = ByteArrayOutputStream()
            val gzipInputStream = GZIPInputStream(ByteArrayInputStream(contentByteArray))
            val buffer = ByteArray(1024)
            var bytesRead = gzipInputStream.read(buffer)
            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bytesRead)
                bytesRead = gzipInputStream.read(buffer)
            }
            gzipInputStream.close()

            return@withContext String(outputStream.toByteArray(), Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return@withContext null
    }

    fun unGzipStringSync(contentByteArray: ByteArray): String? {
        try {
            val outputStream = ByteArrayOutputStream()
            val gzipInputStream = GZIPInputStream(ByteArrayInputStream(contentByteArray))
            val buffer = ByteArray(1024)
            var bytesRead = gzipInputStream.read(buffer)
            while (bytesRead != -1) {
                outputStream.write(buffer, 0, bytesRead)
                bytesRead = gzipInputStream.read(buffer)
            }
            gzipInputStream.close()

            return String(outputStream.toByteArray(), Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


}