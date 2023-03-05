package app.allever.android.sample.cleaner.function

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

abstract class FileScanner<T> {

    private val resultList by lazy {
        mutableListOf<File>()
    }

    open fun getResult() = resultList

    abstract suspend fun scan(
        context: Context,
        block: ((path: String) -> Unit)? = null
    ): List<T>

    suspend fun scanFiles(
        file: File,
        extensionList: List<String>,
        block: ((path: String) -> Unit)? = null
    ): List<File> = withContext(Dispatchers.IO) {
        //在某个目录下查找某个后缀结尾的文件路径
        if (file.isFile) {
            val fileName = file.name
            //判断后缀
            extensionList.map { extension->
                if (fileName.endsWith(extension, true)) {
                    getResult().add(file)
                }
            }

        } else if (file.isDirectory) {
            //回调正在扫描的路径
            block?.invoke(file.absolutePath)
            val files = file.listFiles() //获取下级子目录
            if (files != null && files.isNotEmpty()) {
                for (sFile in files) {
                    scanFiles(sFile, extensionList, block)
                }
            }
        }
        return@withContext getResult()
    }
}