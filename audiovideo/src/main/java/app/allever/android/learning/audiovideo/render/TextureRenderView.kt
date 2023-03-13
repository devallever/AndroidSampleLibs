package app.allever.android.learning.audiovideo.render

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.TextureView
import android.view.View
import app.allever.android.lib.core.function.player.kernel.internal.AbsPlayer

class TextureRenderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextureView(context, attrs), IRenderView {

    override fun attachToPlayer(player: AbsPlayer) {

    }

    override fun setVideoSize(videoWidth: Int, videoHeight: Int) {
    }

    override fun setVideoRotation(degree: Int) {
    }

    override fun setScaleType(scaleType: Int) {
    }

    override fun getView(): View? {
        return null
    }

    override fun doScreenShot(): Bitmap? {
        return null
    }

    override fun release() {
    }
}