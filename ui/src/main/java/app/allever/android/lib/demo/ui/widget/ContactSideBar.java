package app.allever.android.lib.demo.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.allever.android.lib.core.helper.DisplayHelper;
import app.allever.android.lib.core.helper.VibratorHelper;
import app.allever.android.lib.demo.R;


/**
 * 右侧的字母索引View
 *
 * @author
 */
public class ContactSideBar extends View {

    public static String[] defaultData = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
    };
    // 26个字母
    private final List<String> data = new ArrayList<>();
    private final Paint mPaint = new Paint();
    private final int mItemHeight = DisplayHelper.INSTANCE.dip2px(20);
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 选中
    private int mChoose = -1;
    private TextView mTextDialog;
    private String mCurrentData = "";

    public ContactSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ContactSideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactSideBar(Context context) {
        this(context, null);
    }

    /**
     * 为SideBar显示字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    private void init() {
        data.addAll(Arrays.asList(defaultData));
    }

    public void setData(List<String> list) {
        data.clear();
        data.addAll(list);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), data.size() * mItemHeight);
    }

    /**
     * 重写的onDraw的方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight(); // 获取对应的高度
        int width = getWidth(); // 获取对应的宽度
        int singleHeight = mItemHeight; // 获取每一个字母的高度
        for (int i = 0; i < data.size(); i++) {
            mPaint.setColor(Color.parseColor("#858c94")); // 所有字母的默认颜色 目前为灰色(右侧字体颜色)
            mPaint.setTypeface(Typeface.DEFAULT); // (右侧字体样式)
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(30); // (右侧字体大小)
            // 选中的状态
            if (i == mChoose) {
                mPaint.setColor(Color.parseColor("#FFFFFF")); // 选中字母的颜色 目前为白
                mPaint.setFakeBoldText(true); // 设置是否为粗体文字
            }
            // x坐标等于=中间-字符串宽度的一般
            float xPos = width / 2 - mPaint.measureText(data.get(i)) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(data.get(i), xPos, yPos, mPaint);
            mPaint.reset(); // 重置画笔
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        final float y = event.getY(); // 点击y坐标
        if (y > data.size() * mItemHeight) {
            if (mTextDialog != null) {
                mTextDialog.setVisibility(View.INVISIBLE);
            }
            mChoose = -1;
            invalidate();
            return true;
        }
        final int oldChoose = mChoose;

        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;

        final int c = (int) (y / (data.size() * mItemHeight) * data.size()); // 点击y坐标所占高度的比例*b数组的长度就等于点击b中的个数

        switch (action) {
            case MotionEvent.ACTION_UP:
                VibratorHelper.INSTANCE.cancel();
                setBackgroundDrawable(new ColorDrawable(0x00000000)); // 设置背景颜色
                mChoose = -1;
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(R.drawable.seal_sidebar_background); // 点击字母条的背景颜色
                if (oldChoose != c) {
                    if (c >= 0 && c < data.size()) {
                        if (listener != null && !mCurrentData.equals(data.get(c))) {
                            listener.onTouchingLetterChanged(data.get(c));
                            mCurrentData = data.get(c);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(data.get(c));
                            mTextDialog.setVisibility(View.VISIBLE);
                            VibratorHelper.INSTANCE.start();
                        }
                        mChoose = c;
                        invalidate();
                    }
                }
                break;
        }

        return true;
    }

    /**
     * 向外松开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}
