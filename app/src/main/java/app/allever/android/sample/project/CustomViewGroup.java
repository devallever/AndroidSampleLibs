package app.allever.android.sample.project;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomViewGroup extends ViewGroup {
    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        int usedWidth = 0;
        int availableWidth = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            //获取开发者对子view对要求（layout_属性）
            LayoutParams layoutParams = child.getLayoutParams();
            int widthSpec = 0;

            //根据开发者对size要求，分三种情况
            switch (layoutParams.width) {
                case LayoutParams.MATCH_PARENT:
                    //layout_width = "match_parent
                    //顶满父控件, 那么父控件有多大？？？，算 ！！
                    //求可用空间
                    //根据父view的mode, 分三种情况
                    switch (MeasureSpec.getMode(widthMeasureSpec)) {
                        case MeasureSpec.EXACTLY:
                        case MeasureSpec.AT_MOST:
                            //顶满父控件，所以mode也是EXACTLY
                            widthSpec = MeasureSpec.makeMeasureSpec(availableWidth - usedWidth, MeasureSpec.EXACTLY);
                            break;
                        default:
                            widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                            break;
                    }
                    break;
                case LayoutParams.WRAP_CONTENT:
                    switch (MeasureSpec.getMode(widthMeasureSpec)) {
                        case MeasureSpec.EXACTLY:
                        case MeasureSpec.AT_MOST:
                            widthSpec = MeasureSpec.makeMeasureSpec(availableWidth - usedWidth, MeasureSpec.AT_MOST);
                            break;
                        default:
                            widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                            break;
                    }
                    break;
                default:
                    //layout_width = "xx_dp"
                    widthSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
                    break;
            }


            int heightSpec = 0;
            child.measure(widthSpec, heightSpec);
        }
    }
}
