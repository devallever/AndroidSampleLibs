package app.allever.android.lib.demo.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;

import app.allever.android.lib.demo.ui.widget.VerticalImageSpan;

/**
 * 标签和换行
 * textview 垂直居中_在Textview左边或右边添加图标 ，换行不错位
 * https://blog.csdn.net/weixin_39645041/article/details/111109697
 */
public class TextAndPictureUtil {
    private TextAndPictureUtil mTextAndPictureUtil;

    private TextAndPictureUtil() {
    }

    public static SpannableString getText(Context mcontext, String text, int drawId, int width, int height) {
        SpannableString spannableString = new SpannableString("  " + text);
        Drawable drawable = mcontext.getResources().getDrawable(drawId);
        drawable.setBounds(0, 0, width, height);
        spannableString.setSpan(new VerticalImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public TextAndPictureUtil getInstance() {
        if (mTextAndPictureUtil == null) {
            synchronized (TextAndPictureUtil.class) {
                if (mTextAndPictureUtil == null) {
                    mTextAndPictureUtil = new TextAndPictureUtil();
                }
            }
        }
        return mTextAndPictureUtil;
    }
}