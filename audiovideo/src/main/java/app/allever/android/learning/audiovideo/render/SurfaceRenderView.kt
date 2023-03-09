package app.allever.android.learning.audiovideo.render

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import app.allever.android.learning.audiovideo.kernel.AbsPlayer
import app.allever.android.lib.core.ext.toast

class SurfaceRenderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : SurfaceView(context, attrs), IRenderView {
    override fun attachPlayer(player: AbsPlayer) {
        toast("SurfaceRenderView: 绑定播放器")
    }

    override fun setDisplaySize(width: Int, height: Int) {

    }
}