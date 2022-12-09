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

import app.allever.android.lib.core.helper.VibratorHelper;
import app.allever.android.lib.demo.R;


/**
 * 右侧的字母索引View
 *
 * @author
 */
public class ContactSideBar extends View {

    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    // 26个字母
    public static String[] data = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
        "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
    };
    // 选中
    private int mChoose = -1;

    private final Paint mPaint = new Paint();

    private TextView mTextDialog;

    private String mCurrentData = "";

    /**
     * 为SideBar显示字母的TextView
     *
     * @param mTextDialog
     */
    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public ContactSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ContactSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactSideBar(Context context) {
        super(context);
    }
    /** 重写的onDraw的方法 */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight(); // 获取对应的高度
        int width = getWidth(); // 获取对应的宽度
        int singleHeight = height / data.length; // 获取每一个字母的高度
        for (int i = 0; i < data.length; i++) {
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
            float xPos = width / 2 - mPaint.measureText(data[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(data[i], xPos, yPos, mPaint);
            mPaint.reset(); // 重置画笔
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        final float y = event.getY(); // 点击y坐标
        final int oldChoose = mChoose;

        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;

        final int c = (int) (y / getHeight() * data.length); // 点击y坐标所占高度的比例*b数组的长度就等于点击b中的个数

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
                    if (c >= 0 && c < data.length) {
                        if (listener != null && !mCurrentData.equals(data[c])) {
                            listener.onTouchingLetterChanged(data[c]);
                            mCurrentData = data[c];
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(data[c]);
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