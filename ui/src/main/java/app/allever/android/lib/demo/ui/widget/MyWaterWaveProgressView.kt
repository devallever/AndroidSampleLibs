package app.allever.android.lib.demo.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.helper.DisplayHelper

class MyWaterWaveProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaint = Paint((Paint.ANTI_ALIAS_FLAG))

    private var mProgress = 8
    private var mMaxProgress = 10
    private var mPath = Path()

    init {
        initPaint()
    }

    private fun initPaint() {
        mPaint.color = Color.parseColor("#ff0000")
        mPaint.style = Paint.Style.STROKE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        log("height = $height")
        log("measureHeight = $measuredHeight")
        log("width = $width")
        log("measuredWidth = $measuredWidth")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawOneBlizerLine(canvas)


        drawCircle(canvas)


        drawProgressText(canvas)
    }

    private fun drawCircle(canvas: Canvas?) {
//        mPaint.reset()
        mPaint.color = Color.parseColor("#ff0000")
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = DisplayHelper.sp2px(1).toFloat()
        canvas?.drawCircle(
            width / 2f,
            height / 2f,
            width / 2f - DisplayHelper.sp2px(mPaint.strokeWidth.toInt()),
            mPaint
        )
    }

    private fun drawOneBlizerLine(canvas: Canvas?) {
        mPath.reset()
        mPaint.style = Paint.Style.FILL
        mPath.moveTo(0f, height.toFloat())
        mPath.lineTo(0f, height / 2f)
        mPath.quadTo(width / 4f, height / 4f, width / 2f, height / 2f)
        mPath.quadTo(width / 4f * 3, height / 4f * 3, width.toFloat(), height / 2f)
        mPath.lineTo(width.toFloat(), height.toFloat())


        mPath.close()
//        mPath.close()
        mPaint.color = Color.parseColor("#ffff00")
        canvas?.drawPath(mPath, mPaint)

        //
//        mPath.reset()
//        mPaint.style = Paint.Style.FILL
//        mPath.moveTo(width / 2f, height / 2f)
//        mPath.quadTo(width / 4f * 3, height / 4f * 3, width.toFloat(), height / 2f)
//        mPath.close()
//        canvas?.drawPath(mPath, mPaint)
    }

    /**
     * 10/100
     */
    private fun drawProgressText(canvas: Canvas?) {
//        mPaint.reset()
        mPaint.color = Color.parseColor("#000000")
        mPaint.style = Paint.Style.FILL
        mPaint.isFakeBoldText = true
        mPaint.textSize = DisplayHelper.sp2px(25).toFloat()
        val progressTextWidth = mPaint.measureText("$mProgress")
        log("progressTextWidth = $progressTextWidth")

        mPaint.isFakeBoldText = false
        mPaint.textSize = DisplayHelper.sp2px(14).toFloat()
        val maxProgressTextWidth = mPaint.measureText("/${mMaxProgress}")
        log("maxProgressTextWidth = $progressTextWidth")

        val allProgressTextWidth = progressTextWidth + maxProgressTextWidth
        log("allProgressTextWidth = $allProgressTextWidth")

        //文本开始x坐标
        mPaint.isFakeBoldText = true
        val startX = (width - allProgressTextWidth) / 2f
        mPaint.textSize = DisplayHelper.sp2px(25).toFloat()
        canvas?.drawText(mProgress.toString(), startX, height / 3f * 2, mPaint)

        mPaint.isFakeBoldText = false
        mPaint.textSize = DisplayHelper.sp2px(14).toFloat()
        canvas?.drawText("/$mMaxProgress", startX + progressTextWidth, height / 3f * 2, mPaint)
    }

    fun setProgress(progress: Int) {
        mProgress = progress
        invalidate()
    }

    fun setMax(maxProgress: Int) {
        mMaxProgress = maxProgress
    }

    private fun log(msg: String) {
        log("MyWaterWaveProgressView", msg)
    }
}