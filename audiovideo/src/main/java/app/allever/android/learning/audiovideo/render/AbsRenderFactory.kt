package app.allever.android.learning.audiovideo.render

import android.content.Context

abstract class AbsRenderFactory {
    companion object {
        inline fun <reified F> create(): F = F::class.java.newInstance()
    }

    abstract fun createRender(context: Context): IRenderView
}