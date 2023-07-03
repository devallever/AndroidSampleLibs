package app.allever.android.sample.toolbox.util;

import android.content.Context;

/**
 * dp、sp 与 px 互相转化的工具类
 * 实测在同样1080*1920屏幕大小相同的一加，小米手机上，
 * context.getResources().getDisplayMetrics().density 获得的值不同
 * 小米为 2.75
 * 一加为 2.625
 *
 * nexus 1440*2560分辨率，density值为 3.5
 */
public class DisplayUtil {

    /**
     * 将px值转换为dp值，保证尺寸大小不变
     *
     * @param pxValue 待转换的px值
     * @return 转换完成的dp值
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        if (scale == 2.625) { // TODO: 2018/3/7 适配一加机型
            scale = (float) 3.0;
        }
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dp值转换为px值，保证尺寸大小不变
     *
     * @param dpValue 待转换的dp值
     * @return 转换完成的px值
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        if (scale == 2.625) { // TODO: 2018/3/7 适配一加机型
            scale = (float) 3.0;
        }
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue 待转换的px值
     * @return 转换完成的sp值
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue 待转换的sp值
     * @return 转换完成的px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
} 