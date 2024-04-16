package app.allever.android.lib.demo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;

public class CenterLayoutManager2 extends RecyclerView.LayoutManager {

    private int mDecoratedChildWidth = 0;
    private int mPendingScrollPosition = RecyclerView.NO_POSITION;
    private float maxScale = 0.15f;
    private int parentWidth = 0;
    private int itemSize = 0;
    public boolean scrollEnable = true;
    public int initCenterPosition = 0;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return scrollEnable;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }

        //calculate size of child
        if (getChildCount() == 0) {
            View scrap = recycler.getViewForPosition(0);
            scrap.setScaleY(1f);
            scrap.setScaleX(1f);
            addView(scrap);
            measureChildWithMargins(scrap, 0, 0);
            mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
            detachAndScrapView(scrap, recycler);
        }
        if (state.isPreLayout()) {
            return;
        }
        parentWidth = getWidth();
        int layoutPosition;
        int startOffset;
        if (getChildCount() != 0 && itemSize == getItemCount()) {
            View startChild = getChildAt(0);
            layoutPosition = getPosition(startChild);
            startOffset = getDecoratedLeft(startChild);
        } else {
            layoutPosition = initCenterPosition;
            startOffset = parentWidth / 2 - mDecoratedChildWidth / 2;
            while (startOffset > 0 && layoutPosition > 0) {
                startOffset -= mDecoratedChildWidth;
                layoutPosition--;
            }
        }
        if (mPendingScrollPosition != RecyclerView.NO_POSITION) {
            if (mPendingScrollPosition >=0 && mPendingScrollPosition < getItemCount()) {
                layoutPosition = mPendingScrollPosition;
            } else {
                mPendingScrollPosition = RecyclerView.NO_POSITION;
            }
            itemSize = getItemCount();
            detachAndScrapAttachedViews(recycler);
            while (startOffset < parentWidth && layoutPosition < getItemCount()) {
                View child = recycler.getViewForPosition(layoutPosition);
                changeItemScale(child, startOffset);
                measureChildWithMargins(child, 0, 0);
                addView(child);
                int childWidth = getDecoratedMeasuredWidth(child);
                layoutDecorated(child, startOffset, 0, startOffset + childWidth, getDecoratedMeasuredHeight(child));
                startOffset += childWidth;
                layoutPosition++;
            }
        }
    }

    @Override
    public void scrollToPosition(int position) {
        if (position > 0 || position < getItemCount()) {
            mPendingScrollPosition = position;
            requestLayout();
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        mPendingScrollPosition = RecyclerView.NO_POSITION;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int consumed = fill(dx, recycler);
        offsetChildrenHorizontal(-consumed);
        recycleAndFixScale(recycler);
        return consumed;
    }

    private void changeItemScale(View child, int childStart) {
        float childScale = calculateChildScale(childStart);
        child.setScaleY(childScale);
        child.setScaleX(childScale);
    }

    private float calculateChildScale(int childStart) {
        int centerStart = parentWidth / 2 - mDecoratedChildWidth / 2;
        return 1 - Math.abs(2 * maxScale * (centerStart - childStart) / parentWidth);
    }

    private int fill(int dx, RecyclerView.Recycler recycler) {
        int fillPosition = RecyclerView.NO_POSITION;
        int availableSpace = Math.abs(dx);
        int absDelta = Math.abs(dx);
        int left = 0;
        int right = 0;
        if (dx > 0) {
            View anchorView = getChildAt(getChildCount() - 1);
            if (anchorView == null) {
                return 0;
            }
            int anchorPosition = getPosition(anchorView);
            int anchorRight = getDecoratedRight(anchorView);

            left = anchorRight;
            fillPosition = anchorPosition + 1;

            if (fillPosition > getItemCount() && anchorRight - absDelta < getWidth() / 2) {
                return anchorRight - getWidth() / 2;
            }

            if (anchorRight - absDelta > getWidth()) {
                return dx;
            }
        }

        if (dx < 0) {
            View anchorView = getChildAt(0);
            if (anchorView == null) {
                return 0;
            }
            int anchorPosition = getPosition(anchorView);
            int anchorLeft = getDecoratedLeft(anchorView);

            right = anchorLeft;
            fillPosition = anchorPosition - 1;

            if (fillPosition < 0 && anchorLeft + absDelta > getWidth() / 2) {
                return anchorLeft - getWidth() / 2;
            }

            if (anchorLeft + absDelta < 0) {
                return dx;
            }
        }

        while (availableSpace > 0 && (fillPosition >= 0 && fillPosition < getItemCount())) {
            View itemView = recycler.getViewForPosition(fillPosition);
            if (dx > 0) {
                addView(itemView);
            } else {
                addView(itemView, 0);
            }

            measureChild(itemView, 0, 0);

            if (dx > 0) {
                right = left + getDecoratedMeasuredWidth(itemView);
            } else {
                left = right - getDecoratedMeasuredWidth(itemView);
            }
            layoutDecorated(itemView, left, 0, right, getDecoratedMeasuredHeight(itemView));

            if (dx > 0) {
                left += getDecoratedMeasuredWidth(itemView);
                fillPosition++;
            } else  {
                right -= getDecoratedMeasuredWidth(itemView);
                fillPosition--;
            }
            if (fillPosition >= 0 && fillPosition < getItemCount()) {
                availableSpace -= getDecoratedMeasuredWidth(itemView);
            }
        }

        return dx;
    }

    private void recycleAndFixScale(RecyclerView.Recycler recycler) {
        HashSet<View> recycleViews = new HashSet<>();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (getDecoratedRight(child) < 0) {
                recycleViews.add(child);
                continue;
            }
            int left = getDecoratedLeft(child);
            if (left > parentWidth) {
                recycleViews.add(child);
            } else {
                changeItemScale(child, left);
            }
        }

        for (View view: recycleViews) {
            removeAndRecycleView(view, recycler);
        }
    }

    public int getCenterPosition() {
        for(int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (getDecoratedRight(child) > parentWidth / 2) {
                return getPosition(child);
            }
        }
        return -1;
    }

    public int getCenterOffset(int position) {
        return (position - getCenterPosition()) * mDecoratedChildWidth;
    }

    public boolean isCenterReady() {
        for (int childPosition = 0; childPosition < getChildCount(); childPosition ++) {
            View child = getChildAt(childPosition);
            if (getDecoratedRight(child) == parentWidth / 2 + mDecoratedChildWidth / 2) {
                return true;
            }
        }
        return false;
    }
}
