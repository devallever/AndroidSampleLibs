package app.allever.android.lib.demo.ui.dialog.activitydialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import app.allever.android.lib.demo.R

class BottomActivityDialog : AppCompatActivity() {

    companion object {
        fun show(context: Context) {
            val intent = Intent(context, BottomActivityDialog::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""

        setContentView(R.layout.dialog_bottom)
//        setFinishOnTouchOutside(false) //禁止点击区域外消失
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        window.attributes.gravity = Gravity.BOTTOM
    }
}