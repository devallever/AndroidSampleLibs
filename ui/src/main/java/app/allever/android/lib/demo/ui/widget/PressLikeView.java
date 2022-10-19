package app.allever.android.lib.demo.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.allever.android.lib.core.helper.DisplayHelper;
import app.allever.android.lib.demo.R;

/**
 * Android/安卓仿淘宝直播点赞效果/qq空间点赞效果动画
 * https://blog.csdn.net/qq_36009027/article/details/76995301
 */
public class PressLikeView extends ViewGroup {
    private List<Integer> images;//图片
    private List<Interpolator> inters;//插值器
    private Random random;
    private int defaultSize = 90;//图片默认尺寸（px）

    public PressLikeView(Context context) {
        super(context);
    }

    public PressLikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    //初始化数据
    private void initData() {
        defaultSize = DisplayHelper.INSTANCE.dip2px(30);
        random = new Random();
        images = new ArrayList<>();
        inters = new ArrayList<>();
        images.add(R.drawable.default_emoji);
        //....
        inters.add(new LinearInterpolator());
//        inters.add(new AccelerateInterpolator());
//        inters.add(new AccelerateDecelerateInterpolator());
//        inters.add(new DecelerateInterpolator());
        //....
    }

    public PressLikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    //使用预置的随机图片
    public void show() {//这个方法是开放出去的，就是按钮点击时调用，出现一个水果动画
        ImageView view = new ImageView(getContext());
        view.setImageResource(images.get(random.nextInt(images.size())));//随机设置一张图片
        view.setLayoutParams(new LayoutParams(defaultSize, defaultSize));//设置大小
        addView(view);//添加到容器中
        view.layout(getWidth() / 2 - defaultSize, (int) (getHeight() - defaultSize * 1.5), getWidth() / 2, (int) (getHeight() - 0.5 * defaultSize));//计算位置
        startAnim(view);//开始动画
    }

    //使用自定义的图片 -- 也可以修改成传入一个ImageView
    public void show(Drawable drawable) {
        ImageView view = new ImageView(getContext());
        view.setImageDrawable(drawable);
        view.setLayoutParams(new LayoutParams(defaultSize, defaultSize));
        addView(view);
        view.layout(getWidth() / 2 - defaultSize, (int) (getHeight() - defaultSize * 1.5), getWidth() / 2, (int) (getHeight() - 0.5 * defaultSize));
        startAnim(view);
    }

    private void startAnim(final ImageView view) {
        AnimatorSet animatorSet = new AnimatorSet();
        //淡入动画
        ValueAnimator inAnim = ValueAnimator.ofFloat(0.1f, 1f);
        inAnim.setDuration(1000);
        inAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setAlpha(value);
                view.setScaleX(value);
                view.setScaleY(value);
                view.setY(value);
            }
        });
        //淡出动画
        ValueAnimator outAnim = ValueAnimator.ofFloat(1, 0);
        outAnim.setDuration(1000);
        outAnim.setStartDelay(1500);//延迟启动，保证水果飞到一大半再淡出
        outAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha((float) animation.getAnimatedValue());
            }
        });
        //位移动画
        ValueAnimator transAnim = ValueAnimator.ofObject(new BezierValue(), new Point(getWidth() / 2, getHeight()), new Point(new Random().nextInt(getWidth()), 0));
        transAnim.setDuration(3000);
        transAnim.setInterpolator(inters.get(random.nextInt(inters.size())));//随机设置插值器
        transAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                view.setX(point.x);
                view.setY(point.y);
            }
        });
        //组合动画
        //三个动画同时执行
        animatorSet.playTogether(inAnim, transAnim, outAnim);
        //前面两个动画延迟执行，最后一个同时执行
        /*animatorSet.playSequentially(inAnim,transAnim);
        animatorSet.play(outAnim);*/
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(view);//动画结束移除ImageView
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    //自定义插值器
    static class BezierValue implements TypeEvaluator<Point> {
        private final Random random = new Random();
        private int ctrlPX1, ctrlPX2, ctrlPY1, ctrlPY2;
        private boolean isInit;//只需要初始化一次

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            Point point = new Point();
            point.x = (int) cubicPointX(fraction, startValue.x, endValue.x);
            point.y = (int) cubicPointY(fraction, startValue.y, endValue.y);
            return point;
        }

        //贝塞尔计算x
        private double cubicPointX(float fraction, int start, int end) {
            if (!isInit) {
                //初始化控制点y左边
                ctrlPY1 = random.nextInt(start + end / 2);
                ctrlPY2 = random.nextInt(start + end / 2) + (start + end / 2);
                //初始化控制点x坐标
                if (random.nextBoolean()) {//先左后右
                    ctrlPX1 = (int) (random.nextInt(start) - start / 4f);//减去start/4 是为了运动曲线更明显
                    ctrlPX2 = (int) (random.nextInt(start) + start * 1.25f);//start是宽度的一半，为了保证后面往右运动，应该是随机数加上start。现在乘1.25是为了让曲线更明显
                } else {//先右后左
                    ctrlPX1 = (int) (random.nextInt(start) + start * 1.25f);
                    ctrlPX2 = (int) (random.nextInt(start) - start / 4f);
                }
                isInit = true;
            }
            return start * Math.pow((1 - fraction), 3) + 3 * ctrlPX1 * fraction * Math.pow((1 - fraction), 2)
                    + 3 * ctrlPX2 * Math.pow(fraction, 2) * (1 - fraction) + end * Math.pow(fraction, 3);
        }

        //贝塞尔计算y
        private double cubicPointY(float fraction, int start, int end) {
            return start * Math.pow((1 - fraction), 3) + 3 * ctrlPY1 * fraction * Math.pow((1 - fraction), 2)
                    + 3 * ctrlPY2 * Math.pow(fraction, 2) * (1 - fraction) + end * Math.pow(fraction, 3);
        }
    }
}
