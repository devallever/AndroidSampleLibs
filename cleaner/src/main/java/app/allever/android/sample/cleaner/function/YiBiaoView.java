package app.allever.android.sample.cleaner.function;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class YiBiaoView extends View{

    private Context context;

    //传进来的得分
    public int score = 100;
    private Paint outPaint;             //外圈圆弧
    private Paint midPaint;             //中间圆弧
    private Paint progressPaint;        //中间进度圆弧
    private Paint inPaint;              //内圈圆弧
    private Paint kePaint;              //刻度
    private Paint scroePaint;           //分数
    private Paint textPaint;            //文字

    public YiBiaoView(Context context) {
        super(context);
        this.context = context;
    }

    public YiBiaoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        this.outPaint = new Paint();
        this.midPaint = new Paint();
        this.progressPaint = new Paint();
        this.inPaint = new Paint();
        this.kePaint = new Paint();
        this.scroePaint = new Paint();
        this.textPaint = new Paint();

        //为画笔设置属性
        initVariable();
    }

    public void initVariable() {


        //外圈弧度画笔
        outPaint.setAntiAlias(true);
        outPaint.setColor(Color.WHITE);
        midPaint.setAlpha(130);
        outPaint.setStrokeWidth(3.0f);
        outPaint.setStyle(Style.STROKE);

        //中间弧度画笔
        midPaint.setAntiAlias(true);
        midPaint.setColor(Color.WHITE);
        midPaint.setAlpha(130);
        //midPaint.setStrokeWidth(15.0f);
        midPaint.setStyle(Style.STROKE);
        midPaint.setStrokeCap(Paint.Cap.ROUND);   //圆弧两头为半圆的属性

        //中间进度弧度画笔
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(Color.WHITE);
        //progressPaint.setStrokeWidth(15.0f);
        progressPaint.setStyle(Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND); //圆弧两头为半圆的属性

        //内圈弧度画笔
        inPaint.setAntiAlias(true);
        inPaint.setColor(Color.WHITE);
        inPaint.setAlpha(100);
        inPaint.setStrokeWidth(3.0f);
        inPaint.setStyle(Style.STROKE);

        //刻度的画
        kePaint.setAntiAlias(true);
        kePaint.setColor(Color.WHITE);
        kePaint.setAlpha(100);
        kePaint.setStrokeWidth(3.0f);
        kePaint.setStyle(Style.STROKE);


        //分数的画
        scroePaint.setAntiAlias(true);
        scroePaint.setColor(Color.WHITE);
        scroePaint.setStyle(Style.FILL);
        //scroePaint.setTextSize(mRadius/2.5f);
        scroePaint.setTextAlign(Paint.Align.CENTER); //水平居中的属性

        //文字的画
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        kePaint.setAlpha(100);
        //textPaint.setTextSize(mRadius / 10);
        textPaint.setStyle(Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画画板view的背景色
        canvas.drawColor(Color.TRANSPARENT);

        //获取的是画画板view的宽和高
        //x轴圆心坐标
        int mXCenter = getMeasuredWidth() / 2;
        //y轴圆心坐标
        int mYCenter = getMeasuredHeight() / 2;
        //半径
        float mRadius = getMeasuredHeight() / 2 - 10;

        //根据半径做字体大小和宽度的
        midPaint.setStrokeWidth(mRadius / 16);
        progressPaint.setStrokeWidth(mRadius / 16);

        scroePaint.setTextSize(mRadius / 2.2f);
        textPaint.setTextSize(mRadius / 6);

        //画外圈圆
        RectF ovalOut = new RectF();
        ovalOut.left = mXCenter - mRadius;
        ovalOut.top = mYCenter - mRadius;
        ovalOut.right = mXCenter + mRadius;
        ovalOut.bottom = mYCenter + mRadius;
        canvas.drawArc(ovalOut, -225, 270, false, outPaint);

        //画中间圆
        RectF ovalMid = new RectF();
        ovalMid.left = mXCenter - mRadius + mRadius / 10;
        ovalMid.top = mYCenter - mRadius + mRadius / 10;
        ovalMid.right = mXCenter + mRadius - mRadius / 10;
        ovalMid.bottom = mYCenter + mRadius - mRadius / 10;
        canvas.drawArc(ovalMid, -225, 270, false, midPaint);

        //画中间进度圆弧
        RectF ovalProgress = new RectF();
        ovalProgress.left = mXCenter - mRadius + mRadius / 10;
        ovalProgress.top = mYCenter - mRadius + mRadius / 10;
        ovalProgress.right = mXCenter + mRadius - mRadius / 10;
        ovalProgress.bottom = mYCenter + mRadius - mRadius / 10;
        //圆弧进度分数，大于零的时候才会重绘
        if (score > 0) {
            canvas.drawArc(ovalProgress, -225, (float) (2.7 * score), false, progressPaint);
        }

        //画内圈圆弧
        RectF ovalIn = new RectF();
        ovalIn.left = mXCenter - mRadius + mRadius / 6;
        ovalIn.top = mYCenter - mRadius + mRadius / 6;
        ovalIn.right = mXCenter + mRadius - mRadius / 6;
        ovalIn.bottom = mYCenter + mRadius - mRadius / 6;
        canvas.drawArc(ovalIn, -225, 270, false, inPaint);


        //刻度画笔
        for (int i = 0; i <= 100; i++) {
            canvas.save();
            canvas.rotate((float) (2.7 * i - 45), mXCenter, mYCenter);
            if (i % 5 == 0) {
                canvas.drawLine(mXCenter - mRadius + mRadius / 6, mYCenter,
                        mXCenter - mRadius + mRadius / 6 + mRadius / 10, mYCenter, kePaint);
            } else {
                canvas.drawLine(mXCenter - mRadius + mRadius / 6, mYCenter,
                        mXCenter - mRadius + mRadius / 6 + mRadius / 15, mYCenter, kePaint);
            }
            canvas.restore();
        }


        //数字的画笔
        //获取字体宽高
        Paint.FontMetrics fm = scroePaint.getFontMetrics();
        int mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
        String text = String.valueOf(score);
        int mTxtWidth = (int) scroePaint.measureText(text, 0, text.length());
        //字位
        canvas.drawText(text, mXCenter, mYCenter + mTxtHeight / 4, scroePaint);

        //文字的画笔
        canvas.drawText("%", mXCenter + mTxtWidth / 2, mYCenter - mTxtHeight / 4, textPaint);

    }


    /**
     * 每次扣减的分数
     * @param kou 扣减的分数
     */
    public void kouScore(int kou) {

        if (thread == null) {
            animationRunnable = new AnimationRunnable(handler);
            thread = new Thread(animationRunnable);
            thread.start();
        }
        sum += kou;
        animationRunnable.setTotle(kou);
    }

    public void scanComplete(){
        animationRunnable.setSum(sum);
    }

    private Thread thread;
    private int sum;
    private AnimationRunnable animationRunnable;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                score --;
                if (score < 10){
                    score = 10;
                }
                postInvalidate();
            }else if(msg.what == 1){
                thread = null;
            }
        }
    };

    private class AnimationRunnable implements Runnable{

        public Boolean isStop = false;
        public void setTotle(int num){
            totle += num;
        }

        public AnimationRunnable(Handler _handler){
            handler = _handler;
        }

        public void setSum(int _sum){
            sum = _sum;
        }

        private int totle = 0;
        private int sum = -1;
        private int count = 0;
        private Handler handler;

        @Override
        public void run() {
            while (!isStop){
                if (totle != 0){
                    totle --;
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);

                    count ++;
                }

                if (count == sum){
                    isStop = true;
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    return;
                }

                try {
                    Thread.sleep(20);
                }catch (Exception e){

                }
            }
        }
    }

    /**
     * 每次增加的分数
     * @param add 增加的分数
     */
    public void addScore(int add) {
        this.score = score + add;
        if (score > 100){
            this.score = 100;
        }
        postInvalidate();
    }

}
