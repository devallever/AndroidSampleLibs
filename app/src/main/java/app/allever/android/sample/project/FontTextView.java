package app.allever.android.sample.project;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class FontTextView extends androidx.appcompat.widget.AppCompatTextView {
    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //重写设置字体方法
    @Override
    public void setTypeface(@Nullable Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/emojione-android.ttf");

        super.setTypeface(tf);
    }
}
