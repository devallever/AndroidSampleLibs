package app.allever.android.sample.toolbox.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import app.allever.android.sample.toolbox.R;
import app.allever.android.sample.toolbox.util.DisplayUtil;


/**
 * Created by 67045 on 2018/2/26.
 * 自定义View实现指南针转盘
 */
public class CompassView extends View {

    private Canvas mCanvas;
    private final Rect mTextRect;

    private float directionAngle = 0;
    private float oldDirectionAngle = 0;

    /**
     * dp 转 px，尺寸适配
     */
    private final int textDirSize = DisplayUtil.sp2px(getContext(), 11);
    private final int textMidAngleSize = DisplayUtil.sp2px(getContext(), 60);
    private final int textRudAngleSize = DisplayUtil.sp2px(getContext(), 7);
    private final int inOvalSize = DisplayUtil.dp2px(getContext(), 113);
    private final int outOvalSize = inOvalSize + DisplayUtil.dp2px(getContext(), 34);
    private final int inOvalStrokeWidth = DisplayUtil.dp2px(getContext(), 2);
    private final int outOvalStrokeWidth = DisplayUtil.dp2px(getContext(), 1);
    private final int scaleLength = DisplayUtil.dp2px(getContext(), 11);
    private final int normalScaleWidth = DisplayUtil.dp2px(getContext(), 1.1f);
    private final int specialScaleWidth = DisplayUtil.dp2px(getContext(), 1.3f);
    private final int triangleSize = DisplayUtil.dp2px(getContext(), 19);
    private final int blackTriangleSize = DisplayUtil.dp2px(getContext(), 22);
    private final int spaceSize = DisplayUtil.dp2px(getContext(), 6);


    public CompassView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);

        mTextRect = new Rect();
    }

    public void setDirectionAngle(float directionAngle) {
        this.directionAngle = directionAngle;
        if (oldDirectionAngle != directionAngle) {
            postInvalidateDelayed(1 / 60);
            oldDirectionAngle = directionAngle;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;

        mCanvas.drawColor(getResources().getColor(R.color.backgroundColor));
        mCanvas.translate(mCanvas.getWidth() / 2, mCanvas.getHeight() / 2);

        // 绘制最外部两个圆弧
        drawOutSideArc();

        // 画灰色三角形
        drawGrayTriangle();

        // 画中间角度值
        drawMidAngle();

        // 画内部圆弧
        drawInSideArc();

        mCanvas.rotate(-directionAngle, 0f, 0f);

        // 画红色三角形
        drawRedTriangle();

        // 画表盘内的刻度线，刻度值及方向
        drawOthers();
    }

    // 绘制最外部两个圆弧
    private void drawOutSideArc() {
        Paint outsideArcPaint = new Paint();
        outsideArcPaint.setAntiAlias(true);
        outsideArcPaint.setStyle(Paint.Style.STROKE);
        outsideArcPaint.setColor(Color.argb(255, 50, 50, 50));
        outsideArcPaint.setStrokeWidth(outOvalStrokeWidth);
        RectF outsideOval = new RectF(-outOvalSize, -outOvalSize, outOvalSize, outOvalSize);
        mCanvas.drawArc(outsideOval, -83, 140, false, outsideArcPaint);
        mCanvas.drawArc(outsideOval, 123, 140, false, outsideArcPaint);
    }

    // 画灰色三角形
    private void drawGrayTriangle() {
        Paint grayTrianglePaint = new Paint();
        grayTrianglePaint.setAntiAlias(true);
        grayTrianglePaint.setColor(Color.argb(255, 50, 50, 50));
        Path grayTriangle = new Path();
        grayTriangle.moveTo(-triangleSize / 2, -outOvalSize + outOvalStrokeWidth);
        grayTriangle.lineTo(triangleSize / 2, -outOvalSize + outOvalStrokeWidth);
        grayTriangle.lineTo(0, -outOvalSize - (int) (Math.sqrt(triangleSize * triangleSize - triangleSize / 2 * triangleSize / 2) + 0.5) + outOvalStrokeWidth);
        grayTriangle.close();
        mCanvas.drawPath(grayTriangle, grayTrianglePaint);
    }

    // 画中间角度值
    private void drawMidAngle() {
        String angle = String.valueOf((int) directionAngle);
        Paint midAnglePaint = new Paint();
        /*Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/fontnumber_light.ttf");
        midAnglePaint.setTypeface(typeface);*/
        midAnglePaint.setAntiAlias(true);
        midAnglePaint.setColor(getResources().getColor(R.color.editTextColor));
        midAnglePaint.setTextSize(textMidAngleSize);
        midAnglePaint.getTextBounds(angle, 0, angle.length(), mTextRect);
        Paint.FontMetricsInt fm = midAnglePaint.getFontMetricsInt();
        float height = -fm.descent + (fm.descent - fm.ascent) / 2;
        float width = midAnglePaint.measureText(angle);
        mCanvas.drawText(angle + "°", -width / 2, height, midAnglePaint);
    }


    // 画内部圆弧
    private void drawInSideArc() {
        mCanvas.rotate(-90, 0f, 0f);

        // 红色画笔
        Paint redArcPaint = new Paint();
        redArcPaint.setAntiAlias(true);
        redArcPaint.setColor(Color.argb(255, 253, 57, 0));
        redArcPaint.setStyle(Paint.Style.STROKE);
        redArcPaint.setStrokeWidth(inOvalStrokeWidth);

        // 灰色画笔
        Paint grayArcPaint = new Paint();
        grayArcPaint.setAntiAlias(true);
        grayArcPaint.setColor(Color.argb(255, 155, 155, 155));
        grayArcPaint.setStyle(Paint.Style.STROKE);
        grayArcPaint.setStrokeWidth(inOvalStrokeWidth);

        RectF insideOval = new RectF(-inOvalSize, -inOvalSize, inOvalSize, inOvalSize);

        // 手机方向传感器采集到的角度为逆时针，转为顺时针，便于绘图
        float angle = 360 - directionAngle;
        // 圆环中间顶部缺口大小
        float space = 1;

        if (angle < 180) {
            mCanvas.drawArc(insideOval, 0 + space, angle, false, redArcPaint);
            mCanvas.drawArc(insideOval, angle, 360 - space - angle, false, grayArcPaint);
        } else {
            mCanvas.drawArc(insideOval, angle, 360 - space - angle, false, redArcPaint);
            mCanvas.drawArc(insideOval, 0 + space, angle, false, grayArcPaint);
        }

        mCanvas.rotate(90, 0f, 0f);
    }

    // 画红色三角形
    private void drawRedTriangle() {
        // 在红色三角形背后画一个稍微大一点的黑色三角形，实现 padding 效果
        Paint blackTrianglePaint = new Paint();
        blackTrianglePaint.setAntiAlias(true);
        blackTrianglePaint.setColor(Color.argb(255, 255, 255, 255));
        Path blackTriangle = new Path();
        blackTriangle.moveTo(-blackTriangleSize / 2, -inOvalSize + inOvalStrokeWidth);
        blackTriangle.lineTo(blackTriangleSize / 2, -inOvalSize + inOvalStrokeWidth);
        blackTriangle.lineTo(0, -inOvalSize - (int) (Math.sqrt(blackTriangleSize * blackTriangleSize - blackTriangleSize / 2 * blackTriangleSize / 2) + 0.5) + inOvalStrokeWidth);
        blackTriangle.close();
        mCanvas.drawPath(blackTriangle, blackTrianglePaint);

        Paint redTrianglePaint = new Paint();
        redTrianglePaint.setAntiAlias(true);
        redTrianglePaint.setColor(Color.argb(255, 253, 57, 0));
        Path redTriangle = new Path();
        redTriangle.moveTo(-triangleSize / 2, -inOvalSize + inOvalStrokeWidth);
        redTriangle.lineTo(triangleSize / 2, -inOvalSize + inOvalStrokeWidth);
        redTriangle.lineTo(0, -inOvalSize - (int) (Math.sqrt(triangleSize * triangleSize - triangleSize / 2 * triangleSize / 2) + 0.5) + inOvalStrokeWidth);
        redTriangle.close();
        mCanvas.drawPath(redTriangle, redTrianglePaint);
    }

    // 画表盘内的刻度线，刻度值及方向
    private void drawOthers() {
        // 普通刻度
        Paint normalScalePaint = new Paint();
        normalScalePaint.setAntiAlias(true);
        normalScalePaint.setStyle(Paint.Style.FILL);
        normalScalePaint.setStrokeWidth(normalScaleWidth);
        normalScalePaint.setColor(Color.argb(255, 107, 107, 107));
        // 特殊刻度
        Paint specialScalePaint = new Paint();
        specialScalePaint.setAntiAlias(true);
        specialScalePaint.setStyle(Paint.Style.FILL);
        specialScalePaint.setStrokeWidth(specialScaleWidth);
        specialScalePaint.setColor(Color.argb(255, 155, 155, 155));

        // 表盘圈内的东西南北
        Paint dirTextPaint = new Paint();
        dirTextPaint.setAntiAlias(true);
        dirTextPaint.setTextSize(textDirSize);
        // 表盘圈内30整倍数刻度值
        Paint roundAngleTextPaint = new Paint();
        roundAngleTextPaint.setAntiAlias(true);
        roundAngleTextPaint.setColor(Color.argb(255, 107, 107, 107));
        roundAngleTextPaint.setTextSize(textRudAngleSize);

        for (int i = 0; i < 360; i++) {
            if (i % 90 == 0) {
                mCanvas.drawLine(0, -inOvalSize + spaceSize, 0, -inOvalSize + scaleLength + spaceSize, specialScalePaint);
                dirTextPaint.setColor(Color.argb(255, 107, 107, 107));
                String text = "北";
                dirTextPaint.getTextBounds(text, 0, text.length(), mTextRect);
                int textWidth = mTextRect.width();
                int textHeight = mTextRect.height();

                if (i == 0) {
                    String direction = "北";
                    dirTextPaint.setColor(Color.argb(255, 253, 57, 0));
                    mCanvas.drawText(direction, -textWidth / 2, textHeight - inOvalSize + scaleLength + (int) (spaceSize * 1.6 + 0.5), dirTextPaint);
                } else {
                    dirTextPaint.setColor(Color.argb(255, 107, 107, 107));
                    if ((i == 90)) {
                        String direction = "东";
                        mCanvas.drawText(direction, -textWidth / 2, textHeight - inOvalSize + scaleLength + (int) (spaceSize * 1.6 + 0.5), dirTextPaint);
                    } else if (i == 180) {
                        String direction = "南";
                        mCanvas.drawText(direction, -textWidth / 2, textHeight - inOvalSize + scaleLength + (int) (spaceSize * 1.6 + 0.5), dirTextPaint);
                    } else if (i == 270) {
                        String direction = "西";
                        mCanvas.drawText(direction, -textWidth / 2, textHeight - inOvalSize + scaleLength + (int) (spaceSize * 1.6 + 0.5), dirTextPaint);
                    }
                }
            } else if (i % 30 == 0) {
                mCanvas.drawLine(0, -inOvalSize + spaceSize, 0, -inOvalSize + scaleLength + spaceSize, specialScalePaint);
                String angleValue = String.valueOf(i);
                roundAngleTextPaint.getTextBounds(angleValue, 0, angleValue.length(), mTextRect);
                int textWidth = mTextRect.width();
                int textHeight = mTextRect.height();
                mCanvas.drawText(angleValue, -textWidth / 2, textHeight - inOvalSize + scaleLength + (int) (spaceSize * 1.6 + 0.5), roundAngleTextPaint);
            } else if (i % 2 == 0) {
                mCanvas.drawLine(0, -inOvalSize + spaceSize, 0, -inOvalSize + scaleLength + spaceSize, normalScalePaint);
            }
            mCanvas.rotate(1, 0f, 0f);
        }
    }

}
