package app.allever.android.lib.demo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.core.function.work.TimerTask2
import app.allever.android.lib.core.helper.DisplayHelper

/**
 * RecyclerView 自动循环滚动, 滚动一个item
 * https://blog.csdn.net/zhanghuaiwang/article/details/123223178
 */
class AutoScrollRecyclerView(context: Context, attrs: AttributeSet?) :
    RecyclerView(context, attrs) {

    private var mIsTouching = false
    private var mIsAlreadyScrollToBottom = false
    private var mInterval = 1000L
    private var mAutoTask: TimerTask2 = createTimer()
    private var mScrollOffset = DisplayHelper.dip2px(20)

    init {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    // 第一个可见位置
                    val firstItem = getChildLayoutPosition(getChildAt(0))
                    // 最后一个可见位置
                    val lastItem = getChildLayoutPosition(getChildAt(childCount - 1))
//                    log("第一个可见position = $firstItem")
//                    log("最后一个可见position = $lastItem")
                }
            }
        })
    }

    fun getLastVisiblePosition() = getChildLayoutPosition(getChildAt(childCount - 1))

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        val action = e?.action
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            mIsTouching = true
        }
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            mIsTouching = false
        }
        return super.onTouchEvent(e)
    }

    fun start() {
        mAutoTask.cancel()
        mAutoTask = createTimer()
        mAutoTask.start()
    }

    fun stop() {
        mAutoTask.cancel()
    }

    fun setScrollOffset(value: Int) {
        mScrollOffset = value
    }

    fun setInterval(interval: Long, autoStart: Boolean = false) {
        mInterval = interval
        if (autoStart) {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    private fun createTimer() = TimerTask2(context as? LifecycleOwner, mInterval, true) {
//            smoothScrollBy(0, mScrollOffset)

        // 跳转位置在第一个可见项之后，最后一个可见项之前
        // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
//            val movePosition = position - mFirstVisiblePosition;
        val movePosition = 1
        if (movePosition in 0 until childCount) {
            val top = getChildAt(movePosition).top
            if (!mIsTouching) {
                val itemCount = adapter?.itemCount ?: 0
//                log("测试滚动到底部 itemCount = $itemCount")
//                log("测试滚动到底部 最后可见Position = ${getLastVisiblePosition()}")
                if (itemCount <= getLastVisiblePosition() + 1) {
                    if (!mIsAlreadyScrollToBottom) {
                        smoothScrollBy(0, top)
                    }
                    mIsAlreadyScrollToBottom = true
                    return@TimerTask2
                }
                smoothScrollBy(0, top)
                mIsAlreadyScrollToBottom = false
            }
        }
    }

    //禁止手动滑动
//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        return true
//    }
}
