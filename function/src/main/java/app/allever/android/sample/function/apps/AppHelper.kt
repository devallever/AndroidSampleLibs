package app.allever.android.sample.function.apps

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import app.allever.android.lib.core.helper.CoroutineHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 *@Description
 *@author: zq
 *@date: 2024/2/1
 */
@SuppressLint("StaticFieldLeak")
object AppHelper {

    private var localAppList = mutableListOf<AppItem>()

    private var isLoadingApp = false

    private var loadingJob: Job? = null
    
    private lateinit var context: Context
    
    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun preLoad() {
        CoroutineHelper.IO.launch {
            fetchLocalAppList2()
        }
    }

    fun fetchLocalAppList(): List<AppItem> {
        if (!isLoadingApp && localAppList.isNotEmpty()) {
            return localAppList
        }

        if (isLoadingApp) {
            loadingJob?.cancel()
        }

        val list = mutableListOf<AppItem>()
        synchronized(localAppList) {
            isLoadingApp = true
            localAppList.clear()
            val packageManager: PackageManager = context.packageManager
            val allPackages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.getInstalledApplications(
                    PackageManager.ApplicationInfoFlags.of(
                        0L
                    )
                )
            } else {
                packageManager.getInstalledApplications(0)
            }
            allPackages.forEach {
                it.flags
                if (it.packageName == context.packageName) {
                    return@forEach
                }

                if ((it.flags and ApplicationInfo.FLAG_SYSTEM) != 0) {
                    return@forEach
                }

                val name = it.loadLabel(packageManager).toString()
                val icon = it.loadIcon(packageManager)

                /**
                 *    val pkg: String,
                val name: String,
                val icon: Drawable,
                val path: String,
                val support: Boolean,
                val installed: Boolean
                 */

                list.add(
                    AppItem(
                        pkg = it.packageName,
                        name = name,
                        iconDrawable = icon,
                    )
                )
            }

            list.sortBy { it.name }
        }
        localAppList.addAll(list)

        isLoadingApp = false
        return localAppList
    }

    fun fetchLocalAppList2(): List<AppItem> {
        if (!isLoadingApp && localAppList.isNotEmpty()) {
            return localAppList
        }

        if (isLoadingApp) {
            loadingJob?.cancel()
        }

        val list = mutableListOf<AppItem>()
        synchronized(localAppList) {
            isLoadingApp = true
            localAppList.clear()
// MainActivity完整名

// 快捷方式名
//        String title = "unknown";
// MainActivity完整名
            var mainAct: String? = null
// 应用图标标识
            // 应用图标标识
            var iconIdentifier = 0
// 根据包名寻找MainActivity
            // 根据包名寻找MainActivity
            val pkgMag = context.packageManager

            val queryIntent = Intent(Intent.ACTION_MAIN, null)

            queryIntent.addCategory(Intent.CATEGORY_LAUNCHER)

            val appList = pkgMag.queryIntentActivities(
                queryIntent,
                PackageManager.GET_ACTIVITIES
            )
            appList.map {
                val name  = it.loadLabel(pkgMag).toString()
                val pkg = it.activityInfo.packageName
                val mainAct = it.activityInfo.name
                val iconDrawable = it.loadIcon(pkgMag)
                val iconRes = it.iconResource

                list.add(
                    AppItem(
                        pkg = pkg,
                        name = name,
                        iconDrawable = iconDrawable,
                        launchActivity = mainAct,
                        icon = iconRes
                    )
                )
            }

            list.sortBy { it.name }
        }
        localAppList.addAll(list)

        isLoadingApp = false
        return localAppList
    }

    fun createShortcut(
        pkgName: String,
        launchActivity: String,
        title: String,
        iconRes: Int
    ): Boolean {
        var result = false
        val hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.INSTALL_SHORTCUT)
        if (hasPermission !=
            PackageManager.PERMISSION_GRANTED) {
            return result
        }


        val packageManager = context.packageManager
        val supported = ShortcutManagerCompat.isRequestPinShortcutSupported(context)
        if (!supported) {
            Toast.makeText(context, "Can not create shortcut!", Toast.LENGTH_LONG).show()
            return result
        }

        val component = ComponentName(
            pkgName,
            launchActivity
        )
        val shortcutIntent = Intent()
        shortcutIntent.component = component
        shortcutIntent.action = "android.intent.action.MAIN"
        shortcutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        try {
            val activityInfo = packageManager.getActivityInfo(component, PackageManager.MATCH_ALL)
            //            Logger.d(TAG, activityInfo.toString())
        } catch (e: Exception) {
            Toast.makeText(context, "App not exists.", Toast.LENGTH_LONG).show()
            return result
        }

        val shortcut = ShortcutInfoCompat.Builder(context, "${pkgName}${System.currentTimeMillis()}")
            .setShortLabel(title)
            .setLongLabel(title)
            .setIcon(IconCompat.createWithResource(context, iconRes))
            .setIntent(shortcutIntent)
            .build()

        result = ShortcutManagerCompat.requestPinShortcut(context, shortcut, null)
        return result
    }
}

/**
 *@Description
 *@author: zq
 *@date: 2024/2/1
 */
data class AppItem(
    var name: String = "",
    var pkg: String = "",
    var iconBitmap: Bitmap? = null,
    var iconDrawable: Drawable? = null,
    var icon: Int = 0,
    var launchActivity: String = ""
)