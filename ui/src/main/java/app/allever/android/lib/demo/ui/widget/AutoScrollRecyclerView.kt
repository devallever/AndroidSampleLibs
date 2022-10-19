package app.allever.android.lib.demo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.core.helper.DisplayHelper
import coil.request.Disposable
import java.util.concurrent.TimeUnit

/**
 * https://blog.csdn.net/zhanghuaiwang/article/details/123223178
 */
class AutoScrollRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {
    private var mAutoTask: Disposable? = null
    private var mScrollOffset = DisplayHelper.dip2px(20)
    fun start() {
        if (mAutoTask != null && !mAutoTask!!.isDisposed) {
            mAutoTask?.dispose()
        }
        /**
         * @param initialDelay 第一次执行的延迟时间
         * @param period 每次执行的间隔的时间
         * @param unit 时间单位
         * @param scheduler 线程调度器
         * @return Observable对象
         */
        mAutoTask = Observable.interval(3000, 1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { //垂直自动滑动y轴40
                smoothScrollBy(0, mScrollOffset)
            }
    }

    fun stop() {
        if (mAutoTask != null &&!mAutoTask!!.isDisposed) {
            mAutoTask?.dispose()
            mAutoTask = null
        }
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
