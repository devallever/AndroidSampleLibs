package app.allever.android.learning.audiovideo.render

import android.content.Context
import android.util.AttributeSet
import app.allever.android.learning.audiovideo.ijkplayer.widget.media.IjkVideoView
import app.allever.android.learning.audiovideo.kernel.AbsPlayer
import app.allever.android.lib.core.ext.toast

class IJKRenderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : IjkVideoView(context, attrs), IRenderView {
    override fun attachPlayer(player: AbsPlayer) {
        toast("IJKRenderView: 绑定播放器")
    }

    override fun setDisplaySize(width: Int, height: Int) {
    }
}