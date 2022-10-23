package app.allever.android.lib.demo.util

import android.view.ViewGroup
import android.view.ViewOutlineProvider
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

fun BlurView.useShapeBackgroundBlur(background:ViewGroup, radius: Float = 20F) {
    setupWith(background, RenderScriptBlur(context)) // or RenderEffectBlur
        .setBlurRadius(radius)
    outlineProvider = ViewOutlineProvider.BACKGROUND
    clipToOutline = true
}