package app.allever.android.sample.cleaner.function

import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApkFileScanner : FileScanner<ApkInfo>() {

//    private val fileList by lazy {
//        mutableListOf<File>()
//    }
//
//    override fun getResult(): MutableList<File> {
//        return fileList
//    }

    override suspend fun scan(context: Context, block: ((path: String) -> Unit)?): List<ApkInfo> =
        withContext(Dispatchers.IO) {
            getResult().clear()
            val apkInfoList: MutableList<ApkInfo> = ArrayList()

            //扫描APK【扫描sd卡所有目录】
            val sdCard = Environment.getExternalStorageDirectory()
            val apkFiles = scanFiles(sdCard, ".apk", block)
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
            return@withContext apkInfoList
        }

}