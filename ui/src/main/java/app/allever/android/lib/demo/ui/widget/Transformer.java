package app.allever.android.lib.demo.ui.widget;


import android.view.View;

//滑动过程中的缩放
public class Transformer implements GalleryLayoutManager.ItemTransformer {

    @Override
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        //以圆心进行缩放
        item.setPivotX(item.getWidth() / 2.0f);
        item.setPivotY(item.getHeight() / 2.0f);
        float scale = 1 - 0.2f * Math.abs(fraction);
        item.setScaleX(scale);
        item.setScaleY(scale);
    }
}