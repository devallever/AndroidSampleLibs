package app.allever.android.lib.demo.ui.dialog.commondialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import app.allever.android.lib.demo.R


class BottomDialog(context: Context) :
    Dialog(context, com.google.android.material.R.style.Theme_Design_BottomSheetDialog) {
    init {
        //宽度占满屏
        val window = window
        window?.setGravity(Gravity.BOTTOM)
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window?.decorView?.setPadding(0, 0, 0, 0)
        val layoutParams = window?.attributes
        // 设置宽度, bottomDialog 要设置宽度，否则不显示
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = layoutParams
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        setContentView(R.layout.dialog_bottom)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}