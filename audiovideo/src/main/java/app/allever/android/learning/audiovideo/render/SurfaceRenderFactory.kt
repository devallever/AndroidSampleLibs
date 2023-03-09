package app.allever.android.learning.audiovideo.render

import android.content.Context

class SurfaceRenderFactory : AbsRenderFactory() {

    override fun createRender(context: Context): IRenderView {
        return SurfaceRenderView(context)
    }
}