package app.allever.android.lib.demo.ui.widget

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import kotlin.math.abs


//暂未适配wrap_content,请使用固定宽度或match_parent
//Bob代码
class CenterLayoutManager : LayoutManager() {
    private var mDecoratedChildWidth: Int = 0
    private var mPendingScrollPosition = RecyclerView.NO_POSITION
    private val maxScale = 0.15f
    private var parentWidth = 0
    private var itemSize = 0
    var scrollEnable = true
    var initCenterPosition = 0
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }


    override fun canScrollHorizontally(): Boolean {
        return scrollEnable
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler)
            return
        }
        //calculate the size of child
        if (childCount == 0) {
            val scrap = recycler.getViewForPosition(0)
            scrap.scaleY = 1f
            scrap.scaleX = 1f
            addView(scrap)
            measureChildWithMargins(scrap, 0, 0)
            mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap)
            detachAndScrapView(scrap, recycler)
        }
        if (state.isPreLayout) return
        parentWidth = width
        var layoutPosition: Int
        var startOffset: Int
        if (childCount != 0 && itemSize == itemCount) {
            val startChild = getChildAt(0)!!
            layoutPosition = getPosition(startChild)
            startOffset = getDecoratedLeft(startChild)
        } else {
            layoutPosition = initCenterPosition
            startOffset = parentWidth / 2 - mDecoratedChildWidth / 2
            while (startOffset > 0 && layoutPosition > 0) {
                startOffset -= mDecoratedChildWidth
                layoutPosition--
            }
        }
        if (mPendingScrollPosition != RecyclerView.NO_POSITION) {
            if (mPendingScrollPosition in 0 until itemCount) {
                layoutPosition = mPendingScrollPosition
            } else {
                mPendingScrollPosition = RecyclerView.NO_POSITION
            }
        }
        itemSize = itemCount
        detachAndScrapAttachedViews(recycler)
        while (startOffset < parentWidth && layoutPosition < itemCount) {
            val child = recycler.getViewForPosition(layoutPosition)
            changeItemScale(child, startOffset)
            measureChildWithMargins(child, 0, 0)
            addView(child)
            val childWidth = getDecoratedMeasuredWidth(child)
            layoutDecorated(
                child,
                startOffset,
                0,
                startOffset + childWidth,
                getDecoratedMeasuredHeight(child)
            )
            startOffset += childWidth
            layoutPosition++
        }
    }


    override fun scrollToPosition(position: Int) {
        if (position >= 0 || position < itemCount) {
            mPendingScrollPosition = position
            requestLayout()
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller = LinearSmoothScroller(recyclerView.context)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        mPendingScrollPosition = RecyclerView.NO_POSITION
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State?
    ): Int {
        val consumed = fill(dx, recycler)
        offsetChildrenHorizontal(-consumed)
        recycleAndFixScale(recycler)
        return consumed
    }

    private fun changeItemScale(child: View, childStart: Int) {
        val childScale = calculateChildScale(childStart)
        child.scaleY = childScale
        child.scaleX = childScale
    }

    private fun calculateChildScale(childStart: Int): Float {
        val centerStart = parentWidth / 2 - mDecoratedChildWidth / 2
        return 1 - abs(2 * maxScale * (centerStart - childStart) / parentWidth)
    }

    private fun fill(dx: Int, recycler: RecyclerView.Recycler): Int {
        //将要填充的position
        var fillPosition = RecyclerView.NO_POSITION
        //可用的空间，和onLayoutChildren中的totalSpace类似
        var availableSpace = abs(dx)
        //增加一个滑动距离的绝对值，方便计算
        val absDelta = abs(dx)

        //将要填充的View的左上右下
        var left = 0
        var right = 0

        //dx>0就是手指从右滑向左，所以就要填充尾部
        if (dx > 0) {
            val anchorView = getChildAt(childCount - 1) ?: return 0
            val anchorPosition = getPosition(anchorView)
            val anchorRight = getDecoratedRight(anchorView)

            left = anchorRight
            //填充尾部，那么下一个position就应该是+1
            fillPosition = anchorPosition + 1

            //如果要填充的position超过合理范围并且最后一个View的
            //right-移动的距离 < 右边缘(width)那就要修正真实能移动的距离
            if (fillPosition >= itemCount && anchorRight - absDelta < width / 2) {
                return anchorRight - width / 2
            }

            //如果尾部的锚点位置减去dx还是在屏幕外，就不填充下一个View
            if (anchorRight - absDelta > width) {
                return dx
            }
        }

        //dx<0就是手指从左滑向右，所以就要填充头部
        if (dx < 0) {
            val anchorView = getChildAt(0) ?: return 0
            val anchorPosition = getPosition(anchorView)
            val anchorLeft = getDecoratedLeft(anchorView)

            right = anchorLeft
            //填充头部，那么上一个position就应该是-1
            fillPosition = anchorPosition - 1

            //如果要填充的position超过合理范围并且第一个View的
            //left+移动的距离 > 左边缘(0)那就要修正真实能移动的距离
            if (fillPosition < 0 && anchorLeft + absDelta > width / 2) {
                return anchorLeft - width / 2
            }

            //如果头部的锚点位置加上dx还是在屏幕外，就不填充上一个View
            if (anchorLeft + absDelta < 0) {
                return dx
            }
        }

        //根据限定条件，不停地填充View进来
        while (availableSpace > 0 && (fillPosition in 0 until itemCount)) {
            val itemView = recycler.getViewForPosition(fillPosition)

            if (dx > 0) {
                addView(itemView)
            } else {
                addView(itemView, 0)
            }

            measureChild(itemView, 0, 0)

            if (dx > 0) {
                right = left + getDecoratedMeasuredWidth(itemView)
            } else {
                left = right - getDecoratedMeasuredWidth(itemView)
            }
            layoutDecorated(itemView, left, 0, right, getDecoratedMeasuredHeight(itemView))

            if (dx > 0) {
                left += getDecoratedMeasuredWidth(itemView)
                fillPosition++
            } else {
                right -= getDecoratedMeasuredWidth(itemView)
                fillPosition--
            }

            if (fillPosition in 0 until itemCount) {
                availableSpace -= getDecoratedMeasuredWidth(itemView)
            }
        }

        return dx
    }

    private fun recycleAndFixScale(recycler: RecyclerView.Recycler) {
        val recycleViews = hashSetOf<View>()
        for (i in 0 until childCount) {
            val child = getChildAt(i)!!
            //itemView的right<0就是要超出屏幕要回收View
            if (getDecoratedRight(child) < 0) {
                recycleViews.add(child)
                continue
            }
            val left = getDecoratedLeft(child)
            if (left > parentWidth) {
                recycleViews.add(child)
            } else {
                changeItemScale(child, left)
            }
        }
        for (view in recycleViews) {
            removeAndRecycleView(view, recycler)
        }
    }

    fun getCenterPosition(): Int {
        for (childPosition in 0 until childCount) {
            val child = getChildAt(childPosition)!!
            if (getDecoratedRight(child) > parentWidth / 2) {
                return getPosition(child)
            }
        }
        return -1
    }

    /**
     * 默认中心Item已经完成居中，没有偏移
     */
    fun getCenterOffset(position: Int): Int {
        return (position - getCenterPosition()) * mDecoratedChildWidth
    }

    fun isCenterReady(): Boolean {
        for (childPosition in 0 until childCount) {
            val child = getChildAt(childPosition)!!
            if (getDecoratedRight(child) == parentWidth / 2 + mDecoratedChildWidth / 2) {
                return true
            }
        }
        return false
    }
}