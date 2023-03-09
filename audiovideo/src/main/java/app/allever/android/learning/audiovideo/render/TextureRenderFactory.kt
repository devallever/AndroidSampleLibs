package app.allever.android.learning.audiovideo.render

import android.content.Context

class TextureRenderFactory : AbsRenderFactory() {
    override fun createRender(context: Context): IRenderView {
        return TextureRenderView(context)
    }
}