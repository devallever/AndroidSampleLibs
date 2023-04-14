package app.allever.android.learning.audiovideo.render

import android.content.Context
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView
import android.view.View
import app.allever.android.lib.core.function.player.kernel.internal.AbsPlayer

class TextureRenderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextureView(context, attrs), IRenderView {

    private var mMeasureHelper: MeasureHelper? = null
    private var mPlayer: AbsPlayer? = null
    private var mSurface: Surface? = null
    private var mSurfaceTexture: SurfaceTexture? = null

    private val surfaceTextureListener = object : SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(
            surface: SurfaceTexture,
            width: Int,
            height: Int
        ) {
            if (mSurfaceTexture != null) {
                setSurfaceTexture(mSurfaceTexture!!)
            } else {
                mSurfaceTexture = surfaceTexture
                mSurface = Surface(surfaceTexture)
                if (mPlayer != null) {
                    mPlayer?.setSurface(mSurface)
                }
            }
        }

        override fun onSurfaceTextureSizeChanged(
            surface: SurfaceTexture,
            width: Int,
            height: Int
        ) {
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
            return false
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

        }

    }
    init {
        mMeasureHelper = MeasureHelper()
        setSurfaceTextureListener(surfaceTextureListener)
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

    override fun getView(): View {
        return this
    }

    override fun doScreenShot(): Bitmap? {
        return bitmap
    }

    override fun release() {
        if (mSurface != null) {
            mSurface?.release()
        }
        if (mSurfaceTexture != null) {
            mSurfaceTexture?.release()
        }
    }
}