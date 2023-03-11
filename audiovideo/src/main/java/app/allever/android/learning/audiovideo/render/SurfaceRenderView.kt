package app.allever.android.learning.audiovideo.render

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import app.allever.android.learning.audiovideo.kernel.AbsPlayer

class SurfaceRenderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SurfaceView(context, attrs), IRenderView {

    private var mMeasureHelper: MeasureHelper? = null
    private var mPlayer: AbsPlayer? = null

    private val surfaceCallback = object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            mPlayer?.setDisplay(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        }

        override fun surfaceDestroyed(holder: SurfaceHolder) {
        }
    }

    init {
        mMeasureHelper = MeasureHelper()
        holder.addCallback(surfaceCallback)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val measuredSize = mMeasureHelper?.doMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredSize!![0], measuredSize[1])
    }

    override fun attachToPlayer(player: AbsPlayer) {
        mPlayer = player
    }

    override fun setVideoSize(videoWidth: Int, videoHeight: Int) {
        if (videoWidth > 0 && videoHeight > 0) {
            mMeasureHelper?.setVideoSize(videoWidth, videoHeight)
            requestLayout()
        }
    }

    override fun setVideoRotation(degree: Int) {
        mMeasureHelper?.setVideoRotation(degree)
        requestLayout()
    }

    override fun setScaleType(scaleType: Int) {
        mMeasureHelper?.setScreenScale(scaleType)
        requestLayout()
    }

    override fun getView(): View? {
        return this
    }

    override fun doScreenShot(): Bitmap? {
        return drawingCache
    }

    override fun release() {
        holder.removeCallback(surfaceCallback)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        holder.removeCallback(surfaceCallback)
    }

}