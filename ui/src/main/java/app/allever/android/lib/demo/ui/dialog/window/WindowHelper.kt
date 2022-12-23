package app.allever.android.lib.demo.ui.dialog.window

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.toast


object WindowHelper {

    private val windowManager by lazy {
        App.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    fun addView(
        view: View,
        layoutParams: ViewGroup.LayoutParams? = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    ) {

        view.layoutParams = layoutParams
        windowManager.addView(view, layoutParams)

    }

    fun removeView(view: View) {
        windowManager.removeView(view)
    }

    fun createFloatWindow(context: Context): View {
        val btn = Button(context)
        btn.text = "关闭日志"
        btn.setOnClickListener {
            toast("hello")
        }

        // 获取WindowManager

        // 获取WindowManager
        // 创建布局参数
        // 创建布局参数
        val params = WindowManager.LayoutParams()
        /** 设置参数 */
        /** 设置参数  */
//        params.type =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY else WindowManager.LayoutParams.TYPE_PHONE
        params.format = PixelFormat.RGBA_8888
        // 设置窗口的行为准则
        // 设置窗口的行为准则
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        //设置透明度
        //设置透明度
        params.alpha = 1.0f
        //设置内部视图对齐方式，这边位置为左边靠上
        //设置内部视图对齐方式，这边位置为左边靠上
        params.gravity = Gravity.LEFT or Gravity.TOP
        //窗口的左上角坐标
        //窗口的左上角坐标
        params.x = 0
        params.y = 0
        //设置窗口的宽高,这里为自动
        //设置窗口的宽高,这里为自动
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.type = WindowManager.LayoutParams.TYPE_BASE_APPLICATION

        // 添加进WindowManager

        // 添加进WindowManager
        windowManager.addView(btn, params)
        return btn
    }
}
