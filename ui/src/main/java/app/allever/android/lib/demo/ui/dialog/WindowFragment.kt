package app.allever.android.lib.demo.ui.dialog

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.ui.dialog.window.PermissionCheckUtil
import com.yhao.floatwindow.FloatWindow
import com.yhao.floatwindow.MoveType
import com.yhao.floatwindow.PermissionListener
import com.yhao.floatwindow.ViewStateListener
import java.util.*

class WindowFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "请求悬浮窗权限",
        "应用内弹窗",
        "系统内弹窗"
    )

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                if (!checkDrawOverlaysPermission(false)) {
                    showOpenOverlaysPermissionDialog()
                } else {
                    toast("已经授权")
                }
            }
            1 -> {

            }
            2 -> {
                if (!checkDrawOverlaysPermission(false)) {
                    showOpenOverlaysPermissionDialog()
                } else {
                    showInAppFloatWindow(item)
                    //显示弹窗后要finish才能显示???
                    requireActivity().finish()
                }
            }
        }
    }

    private fun checkDrawOverlaysPermission(needOpenPermissionSetting: Boolean): Boolean {
        return if (Build.BRAND.lowercase(Locale.getDefault()).contains("xiaomi")) {
            if (PermissionCheckUtil.canDrawOverlays(context, needOpenPermissionSetting)) {
                true
            } else {
                if (needOpenPermissionSetting && !Build.BRAND.lowercase(Locale.getDefault())
                        .contains("xiaomi")
                ) {
                }
                false
            }
        } else {
            PermissionCheckUtil.canDrawOverlays(context, needOpenPermissionSetting)
        }
    }

    private fun showOpenOverlaysPermissionDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("打开悬浮窗权限")
            .setPositiveButton("好的") { dialog, which ->
                val intent = Intent(
                    "android.settings.action.MANAGE_OVERLAY_PERMISSION",
                    Uri.parse("package:" + activity?.packageName)
                )
                activity?.startActivity(intent)
            }
            .setNegativeButton("取消") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showInAppFloatWindow(tag: String) {
        if (FloatWindow.get(tag)?.isShowing == true) {
            return
        }

        val context = App.context

        val view =
            LayoutInflater.from(context).inflate(R.layout.float_circle_image_view, null, false)

        view?.setOnClickListener {
            if (FloatWindow.get(tag)?.isShowing == true) {
                FloatWindow.destroy(tag)
            }
        }


        val maxY: Int = (DisplayHelper.getScreenHeight()
                - DisplayHelper.getNavigationBarHeight(context)
                - DisplayHelper.dip2px(100))
        FloatWindow.with(App.context)
            .setTag(tag)
            .setView(view)
            .setWidth(DisplayHelper.dip2px(100))
            .setHeight(DisplayHelper.dip2px(100))
//            .setX(Screen.width, 0.7f)
//            .setY(maxY)
            .setMoveType(MoveType.slide)
            .setDesktopShow(true)
            .setPermissionListener(object : PermissionListener {
                override fun onSuccess() {
                    toast("授权成功")
                }

                override fun onFail() {
                    toast("授权失败")
                }

            })
            .setViewStateListener(object : ViewStateListener {
                override fun onPositionUpdate(x: Int, y: Int) {
                    val window =
                        FloatWindow.get(tag)
                            ?: return
                    if (y < 0) {
                        window.updateY(0)
                    }
                    if (y > maxY) {
                        window.updateY(maxY)
                    }
                }

                override fun onShow() {}
                override fun onHide() {}
                override fun onDismiss() {}
                override fun onMoveAnimStart() {}
                override fun onMoveAnimEnd() {}
                override fun onBackToDesktop() {}
            })
            .build()
    }
}