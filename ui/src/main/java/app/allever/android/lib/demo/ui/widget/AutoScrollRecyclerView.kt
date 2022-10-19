package app.allever.android.lib.demo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.core.function.work.TimerTask2
import app.allever.android.lib.core.helper.DisplayHelper

/**
 * https://blog.csdn.net/zhanghuaiwang/article/details/123223178
 */
class AutoScrollRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    private var mAutoTask: TimerTask2 =  TimerTask2(lifecycleOwner = null, 1000, true) {
        smoothScrollBy(0, mScrollOffset)
    }
    private var mScrollOffset = DisplayHelper.dip2px(20)

    fun start() {
        mAutoTask.start()
    }

    fun stop() {
        mAutoTask.cancel()
    }

    fun setScrollOffset(value: Int) {
        mScrollOffset = value
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    //禁止手动滑动
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return true
    }
}
