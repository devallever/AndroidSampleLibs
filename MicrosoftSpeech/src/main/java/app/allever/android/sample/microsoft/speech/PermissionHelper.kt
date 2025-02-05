package app.allever.android.sample.microsoft.speech

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.core.app.AppOpsManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX

object PermissionHelper {
    fun hasPermissions(context: Context?, permission: String): Boolean {
        context ?: return false
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        var result = ContextCompat.checkSelfPermission(context, permission)
        if (result == PackageManager.PERMISSION_DENIED) {
            return false
        }

        val op = AppOpsManagerCompat.permissionToOp(permission)
        if (TextUtils.isEmpty(op)) {
            return false
        }

        result = AppOpsManagerCompat.noteProxyOp(context, op!!, context.packageName)
        if (result != AppOpsManagerCompat.MODE_ALLOWED) {

            return false
        }

        return true
    }

    fun hasPermissions(context: Context?, permissions: List<String>): Boolean {
        context ?: return false
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        for (permission in permissions) {
            var result = ContextCompat.checkSelfPermission(context, permission)
            if (result == PackageManager.PERMISSION_DENIED) {
                return false
            }

            val op = AppOpsManagerCompat.permissionToOp(permission)
            if (TextUtils.isEmpty(op)) {
                continue
            }
            result = AppOpsManagerCompat.noteProxyOp(context, op!!, context.packageName)
            if (result != AppOpsManagerCompat.MODE_ALLOWED) {
                return false
            }
        }
        return true
    }

//
//    fun jumpSetting(context: Context, requestCode: Int) {
//        PermissionUtil.GoToSetting(context)
//    }

    fun request(
        context: FragmentActivity,
        listener: PermissionListener,
        vararg permissions: String
    ) {
        PermissionX.init(context).permissions(*permissions)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    log("同意了所有权限")
                    listener.onAllGranted()
                } else {
                    handleDenied(permissions, context, listener, deniedList)
                }
            }
    }

    fun request(
        context: FragmentActivity,
        listener: PermissionListener,
        permissions: List<String>
    ) {
        PermissionX.init(context).permissions(permissions)
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    log("同意了所有权限")
                    listener.onAllGranted()
                } else {
                    handleDenied(permissions.toTypedArray(), context, listener, deniedList)
                }
            }
    }

    private fun handleDenied(permissions: Array<out String>, context: Context, listener: PermissionListener, deniedList: MutableList<String>) {
        //判断是否总是拒绝
        if (PermissionHelper.hasAlwaysDeniedPermissionOrigin(
                context,
                deniedList
            )
        ) {
            permissions.map {
                log("总是拒绝权限：$it")
            }
            listener.alwaysDenied(deniedList)
//            var jumpSettingDialog = listener.getSettingDialog()
//            if (jumpSettingDialog == null) {
//                jumpSettingDialog = JumpPermissionSettingDialog(context)
//            }
//            jumpSettingDialog?.show()
        } else {
            permissions.map {
                log( "拒绝权限：$it")
            }
            listener.onDenied(deniedList)
        }
    }


//    fun gotoSettingOrigin(context: Context? = null) {
//        PermissionUtil.GoToSetting(context ?: ActivityHelper.getTopActivity())
//    }

    private fun hasAlwaysDeniedPermissionOrigin(
        context: Context? = null,
        deniedPermissions: List<String>
    ): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return false
        }

        if (deniedPermissions.isEmpty()) {
            return false
        }

        val activity = when (context) {
            is Activity -> {
                context
            }
            is Fragment -> {
                context.activity
            }
            else -> {
                null
            }
        }
        for (permission in deniedPermissions) {
            val rationale = activity?.shouldShowRequestPermissionRationale(permission)
            if (rationale == false) {
                return true
            }
        }
        return false
    }

    private fun log(msg: String) {
        Log.d("PermissionHelper", msg)
    }

}