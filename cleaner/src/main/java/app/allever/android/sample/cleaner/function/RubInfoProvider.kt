package app.allever.android.sample.cleaner.function

import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

//获取某个后缀的垃圾文件
//获取名称包含（download、temp）等的文件，并删除其中内容
//获取长度为0的文件，或者没有任何子目录的文件夹，并删除
object RubInfoProvider {
    // 扫描sd卡目录中的log【应用日志】
    suspend fun getLogFiles(): List<File> = withContext(Dispatchers.IO) {
        val rubList = ArrayList<File>()

        // 扫描sd卡目录中的log【应用日志】
        val sdCard = Environment.getExternalStorageDirectory()
        val logFiles = getFiles(sdCard, ".log")
        val tempFiles = getFiles(sdCard, ".temp")
        val tmpFiles = getFiles(sdCard, ".tmp")
        rubList.addAll(logFiles)
        rubList.addAll(tempFiles)
        rubList.addAll(tmpFiles)
        return@withContext rubList
    }

    suspend fun getApkFiles(context: Context): List<ApkInfo> = withContext(Dispatchers.IO) {
        val apkInfoList: MutableList<ApkInfo> = ArrayList()

        //扫描APK【扫描sd卡所有目录】
        val sdCard = Environment.getExternalStorageDirectory()
        val apkFiles = getFiles(sdCard, ".apk")
        if (apkFiles != null) {
            //遍历apk文件的集合
            for (file in apkFiles) {
                val apkInfo = ApkInfo()
                val apkPath = file.absolutePath
                apkInfo.path = apkPath
                apkInfo.size = file.length()
                val pm = context.packageManager
                val packageInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES)
                if (packageInfo != null) {
                    val appInfo = packageInfo.applicationInfo
                    appInfo.sourceDir = apkPath
                    appInfo.publicSourceDir = apkPath
                    apkInfo.icon = appInfo.loadIcon(pm)
                    apkInfo.packageName = appInfo.packageName
                    apkInfo.name = appInfo.loadLabel(pm).toString()
                    apkInfoList.add(apkInfo)
                }
            }
        }
        return@withContext apkInfoList
    }

    private val fileList: MutableList<File> = ArrayList()
    suspend fun getFiles(file: File, extension: String?): List<File> = withContext(Dispatchers.IO) {
        //在某个目录下查找某个后缀结尾的文件路径
        if (file.isFile) {
            val fileName = file.name
            //判断后缀
            if (fileName.toLowerCase().endsWith(extension!!)) {
                fileList.add(file)
            }
        } else if (file.isDirectory) {
            val files = file.listFiles() //获取下级子目录
            if (files != null && files.size > 0) {
                for (sFile in files) {
                    getFiles(sFile, extension)
                }
            }
        }
        return@withContext fileList
    }
}