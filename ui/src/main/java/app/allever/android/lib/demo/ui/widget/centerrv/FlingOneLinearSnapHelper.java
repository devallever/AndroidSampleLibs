package app.allever.android.lib.demo.ui.widget.centerrv;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

@Deprecated
public class FlingOneLinearSnapHelper extends LinearSnapHelper {
    private RecyclerView mRecyclerView;

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        mRecyclerView = recyclerView;
        super.attachToRecyclerView(recyclerView);
    }

    /**
     * 惯性滑动获取RecyclerView的最终位置
     *
     * @param layoutManager RecyclerView的布局管理器
     * @return 返回中心位置对应的控件
     */
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int resultPosition = RecyclerView.NO_POSITION;
        View centerView = findSnapView(layoutManager);
        if (centerView != null) {
            int centerX = mRecyclerView.getMeasuredWidth() / 2;
            int viewCenterX = centerView.getLeft() + centerView.getWidth() / 2;
            int centerPotion = layoutManager.getPosition(centerView);
            if (velocityX > 0) {
                // 向左滑
                if (viewCenterX < centerX) {
                    // 当前控件已经位于中线左边，则移动至下个位置
                    resultPosition = centerPotion + 1;
                } else {
                    resultPosition = centerPotion;
                }
            } else {
                // 向右滑
                if (viewCenterX > centerX) {
                    // 当前控件已经位于中线右边，则移动至上个位置
                    resultPosition = centerPotion - 1;
                } else {
                    resultPosition = centerPotion;
                }
            }
        }
        if (resultPosition < layoutManager.getItemCount()) {
            return resultPosition;
        } else {
            return RecyclerView.NO_POSITION;
        }
    }

    /**
     * 获取RecyclerView的中心位置对应的控件
     *
     * @param layoutManager RecyclerView的布局管理器
     * @return 返回中心位置对应的控件
     */
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        int count = layoutManager.getChildCount();
        if (mRecyclerView != null && count > 0) {
            int centerX = mRecyclerView.getMeasuredWidth() / 2;
            if (centerX > 0) {
                int distance = 0;
                for (int i = 0; i < count; i++) {
                    View view = layoutManager.getChildAt(i);
                    if (view.getRight() < centerX) {
                        distance = centerX - view.getRight();
                    } else {
                        if (view.getLeft() <= centerX) {
                            return view;
                        } else {
                            int currentDistance = view.getLeft() - centerX;
                            if (currentDistance > distance) {
                                return layoutManager.getChildAt(i - 1);
                            } else if (currentDistance < distance) {
                                return view;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取RecyclerView的中心位置对应的item位置
     *
     * @param layoutManager RecyclerView的布局管理器
     * @return 返回中心位置对应的item位置
     */
    public int getCenterPotion(RecyclerView.LayoutManager layoutManager) {
        View centerView = findSnapView(layoutManager);
        return centerView == null ? RecyclerView.NO_POSITION : layoutManager.getPosition(centerView);
    }
}
