package app.allever.android.learning.audiovideo.render

import android.content.Context

class IJKRenderFactory : AbsRenderFactory() {
    override fun createRender(context: Context): IRenderView {
        return IJKRenderView(context)
    }
}