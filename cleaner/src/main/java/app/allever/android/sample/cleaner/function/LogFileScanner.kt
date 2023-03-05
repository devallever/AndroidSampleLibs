package app.allever.android.sample.cleaner.function

import android.content.Context
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object LogFileScanner : FileScanner<File>() {
    override suspend fun scan(context: Context, block: ((path: String) -> Unit)?): List<File> =
        withContext(Dispatchers.IO) {
            getResult().clear()
            val rubList = ArrayList<File>()

            // 扫描sd卡目录中的log【应用日志】
            val sdCard = Environment.getExternalStorageDirectory()
            val logFiles = scanFiles(sdCard, ".log", block)
            val tempFiles = scanFiles(sdCard, ".temp", block)
            val tmpFiles = scanFiles(sdCard, ".tmp", block)
            rubList.addAll(logFiles)
            rubList.addAll(tempFiles)
            rubList.addAll(tmpFiles)
            return@withContext rubList
        }
}