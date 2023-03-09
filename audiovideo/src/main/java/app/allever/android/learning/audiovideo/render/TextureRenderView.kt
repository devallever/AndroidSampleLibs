package app.allever.android.learning.audiovideo.render

import android.content.Context
import android.util.AttributeSet
import android.view.TextureView
import app.allever.android.learning.audiovideo.kernel.AbsPlayer
import app.allever.android.lib.core.ext.toast

class TextureRenderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextureView(context, attrs), IRenderView {

    override fun attachPlayer(player: AbsPlayer) {
        toast("TextureRenderView: 绑定播放器")
    }

    override fun setDisplaySize(width: Int, height: Int) {
    }
}