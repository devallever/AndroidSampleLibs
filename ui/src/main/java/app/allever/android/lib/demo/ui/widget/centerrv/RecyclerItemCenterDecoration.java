package app.allever.android.lib.demo.ui.widget.centerrv;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


import app.allever.android.lib.core.helper.DisplayHelper;

@Deprecated
public class RecyclerItemCenterDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 获得Item的数量
        int itemCount = parent.getAdapter().getItemCount();
        if (itemCount > 0) {
            // 获取当前Item的position
            int position = parent.getChildAdapterPosition(view);
            int endPosition = itemCount - 1;
            if (position == 0 || position == endPosition) {
                // 测量view的宽度
                view.measure(0, 0);
                // 默认值
                int childViewWidth = DisplayHelper.INSTANCE.dip2px(120);
                if (view.getMeasuredWidth() > 0) {
                    childViewWidth = view.getMeasuredWidth();
                }
                int parentWidth = parent.getWidth();
                if (parentWidth <= 0) {
                    parentWidth = DisplayHelper.INSTANCE.getScreenWidth();
                }
                //所需的最小边距
                int minMargin = (parentWidth / 2 - childViewWidth / 2);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                // 第1个item左边距不够剧中
                if (position == 0 && layoutParams.leftMargin < minMargin) {
                    layoutParams.setMargins(minMargin, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.bottomMargin);
                    view.setLayoutParams(layoutParams);
                }
                // 最后的item右边距不够剧中
                if (position == endPosition && layoutParams.rightMargin < minMargin) {
                    layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, minMargin, layoutParams.bottomMargin);
                    view.setLayoutParams(layoutParams);
                }
            }
        }
        super.getItemOffsets(outRect, view, parent, state);
    }
}