package app.allever.android.lib.demo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.core.function.work.TimerTask2
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.core.helper.ViewHelper
import kotlin.math.abs

/**
 * 自动滚动RecyclerView
 * https://blog.csdn.net/zhanghuaiwang/article/details/123223178
 */
class SmartAutoScrollRecyclerView(context: Context, attrs: AttributeSet?) :
    RecyclerView(context, attrs) {

    companion object {
        const val DIRECTION_TOP = 0
        const val DIRECTION_BOTTOM = 1
    }

    private var mIsTouching = false
    private var mIsAlreadyScrollToBottom = false
    private var mInterval = 1000L
    private var mAutoTask: TimerTask2 = createTimer()
    private var mScrollOffset = DisplayHelper.dip2px(20)

    var nextPageListener: (() -> Unit)? = null
    var lastPageListener: (() -> Unit)? = null
    var direction = DIRECTION_TOP

    private var mStop = false

    init {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    //主动停止就不需要触发了
                    if (mStop) {
                        return
                    }

                    // 第一个可见位置
                    val firstVisibleItem = getChildLayoutPosition(getChildAt(0))
                    // 最后一个可见位置
                    val lastVisibleItem = getChildLayoutPosition(getChildAt(childCount - 1))
                    log("第一个可见position = $firstVisibleItem Activity: ${context.javaClass.simpleName}")
                    if (firstVisibleItem == 0) {
                        lastPageListener?.invoke()
                    }
                    val lastItemValue = lastVisibleItem
                    val totalItem = (adapter?.itemCount?:0 - 1)
                    val lastIndex = totalItem - 1
                    if (lastItemValue == lastIndex) {
                        nextPageListener?.invoke()
                    }

                    //计算间隔， 启动下一个
                    try {
                        val interval = (400L..2000L).random()
                        log("聊群消息滚动时间间隔：= $interval")
                        setInterval(interval, true)
                    } catch (e: Exception) {
                        setInterval(400, true)
                        //加载下一页
                        nextPageListener?.invoke()
                    }
                }
            }
        })
    }

    //这个应该是底部的
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
        mStop = false
    }

    fun stop() {
        mAutoTask.cancel()
        mStop = true
    }

    fun setScrollOffset(value: Int) {
        mScrollOffset = value
    }

    fun setInterval(interval: Long, autoStart: Boolean = true) {
        mInterval = interval
        if (autoStart) {
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
//        stop()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        start()
    }

    private fun createTimer() = TimerTask2(context as? LifecycleOwner, mInterval, false) {
        if (mStop) {
            return@TimerTask2
        }

        if (direction == DIRECTION_TOP) {
            val movePosition = 1
            val top = getChildAt(movePosition).top
            val itemCount = adapter?.itemCount ?: 0

            if (itemCount <= getLastVisiblePosition() + 1) {
                if (!mIsAlreadyScrollToBottom) {
                    scrollToOffset(top)
                }
                mIsAlreadyScrollToBottom = true
                return@TimerTask2
            }
            scrollToOffset(top)
            mIsAlreadyScrollToBottom = false
        } else {
            val movePosition = 1
            if (movePosition in 0 until childCount) {

                //屏幕可见的item数
                log("childCount = $childCount")
                //最后一个可见view
                val lastView = getChildAt(childCount - 1)
                val childAdapterPosition = getChildAdapterPosition(lastView)
                log("lastView childAdapterPosition = $childAdapterPosition")
                val childLayoutPosition = getChildLayoutPosition(lastView)
                log("lastView childLayoutPosition = $childLayoutPosition")
                val lastViewOnScreen = ViewHelper.getViewLocationOnScreen(lastView)
                log("lastView in Window y = ${lastViewOnScreen[1]}")

                val bottomHeight =
                    DisplayHelper.getFullScreenHeight(context) - ViewHelper.getGlobalVisibleRect(
                        this
                    ).bottom
                log("bottomHeight = $bottomHeight")
                val lastViewY = lastViewOnScreen[1]
                var offsetY =
                    lastViewY + lastView.height - (DisplayHelper.getFullScreenHeight(context) - bottomHeight) + DisplayHelper.dip2px(
                        2
                    ) + lastView.marginBottom + lastView.marginTop

                log("offsetY = $offsetY")

                if (offsetY <= 0) {
                    offsetY = DisplayHelper.dip2px(1)
                }
                scrollToOffset(abs(offsetY))

            }
        }

    }

    private fun scrollToOffset(offsetY: Int) {
        if (!mIsTouching) {
            smoothScrollBy(0, offsetY)
            mIsAlreadyScrollToBottom = false
        }
    }

    fun isStop() = mStop

    private fun log(msg: String) {
        app.allever.android.lib.core.ext.log("SmartAutoScroll", msg)
    }

    //禁止手动滑动
//    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
//        return true
//    }
}