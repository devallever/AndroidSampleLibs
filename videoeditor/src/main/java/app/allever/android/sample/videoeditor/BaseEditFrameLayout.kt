package app.allever.android.sample.videoeditor

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.helper.ViewHelper

class BaseEditFrameLayout(
    context: Context, attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    companion object {

    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (ViewHelper.isInViewArea(child, event?.rawX ?: 0f, event?.rawY ?: 0f)) {
                log("触摸到子类：i = $i")
            } else {
                log("没触摸到子类")
            }
        }
        return super.onTouchEvent(event)
    }
}