package app.allever.android.sample.cleaner.function

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import app.allever.android.lib.core.R
import app.allever.android.lib.core.ext.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.FileReader

object ProcessInfoProvider {
    //获取正在运行的进程总数
    fun getProcessCount(ctx: Context): Int {
        val am = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningAppProcesses = am.runningAppProcesses
        return runningAppProcesses.size
    }

    //获取可用内存空间大小，单位byte
    suspend fun getAvailSpace(ctx: Context): Long = withContext(Dispatchers.IO) {
        val am = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        am.getMemoryInfo(memoryInfo)
        return@withContext memoryInfo.availMem
    }

    //获取全部内存空间大小，单位byte
    suspend fun totalSpace(): Long = withContext(Dispatchers.IO) {
        try {
            val fileReader = FileReader("proc/meminfo")
            val bufferedReader = BufferedReader(fileReader)
            val readLine = bufferedReader.readLine()
            val charArray = readLine.toCharArray()
            val sb = StringBuilder()
            for (c in charArray) {
                if (c >= '0' && c <= '9') {
                    sb.append(c)
                }
            }
            bufferedReader.close()
            return@withContext sb.toString().toLong() * 1024
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext 0
    }

    //获取所有正在运行的进程的集合
    suspend fun getProcessList(context: Context): List<ProcessInfo> = withContext(Dispatchers.IO) {
        val processInfoList: MutableList<ProcessInfo> = ArrayList()
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val pm = context.packageManager
        val runningAppProcesses = am.runningAppProcesses
        if (runningAppProcesses != null) {
            for (runningAppProcessInfo in runningAppProcesses) {
                val processInfo = ProcessInfo()
                processInfo.packageName = runningAppProcessInfo.processName
                val processMemoryInfo =
                    am.getProcessMemoryInfo(intArrayOf(runningAppProcessInfo.pid))
                val memoryInfo = processMemoryInfo[0]
                processInfo.itemSize = (memoryInfo.totalPrivateDirty * 1024).toLong() //单位byte
                try {
                    val applicationInfo = pm.getApplicationInfo(processInfo.packageName ?: "", 0)
                    processInfo.itemName = applicationInfo.loadLabel(pm).toString()
                    processInfo.itemIcon = applicationInfo.loadIcon(pm)

                    //判读是否系统应用
                    processInfo.isSystem =
                        applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == ApplicationInfo.FLAG_SYSTEM
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                    processInfo.itemName = processInfo.packageName
                    processInfo.itemIcon = context.resources.getDrawable(R.drawable.ic_back)
                }
                if (!processInfo.isSystem) {
                    processInfoList.add(processInfo)
                    log("================" + processInfoList.size)
                }
            }
        }
        return@withContext processInfoList
    }

    //杀死进程
    suspend fun killProcess(context: Context) = withContext(Dispatchers.IO) {
        val activityManger = context.getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager
        val runProcessList = activityManger.runningAppProcesses
        if (runProcessList != null) {
            for (runnProcessInfo in runProcessList) {
                if (runnProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE) {
                    val packageName = runnProcessInfo.processName
                    if (packageName == context.packageName) {
                        continue
                    }
                    activityManger.killBackgroundProcesses(packageName)
                }
            }
        }
    }
}