package app.allever.android.learning.audiovideo.render

import android.graphics.Bitmap
import android.view.View
import app.allever.android.lib.core.function.player.kernel.internal.AbsPlayer

/**
 * 渲染接口
 */
interface IRenderView {

    /**
     * 关联AbstractPlayer
     * @param player                        player
     */
    fun attachToPlayer(player: AbsPlayer)

    /**
     * 设置视频宽高
     * @param videoWidth                    宽
     * @param videoHeight                   高
     */
    fun setVideoSize(videoWidth: Int, videoHeight: Int)

    /**
     * 设置视频旋转角度
     * @param degree                        角度值
     */
    fun setVideoRotation(degree: Int)

    /**
     * 设置screen scale type
     * @param scaleType                     类型
     */
    fun setScaleType(scaleType: Int)

    /**
     * 获取真实的RenderView
     * @return                              view
     */
    fun getView(): View?

    /**
     * 截图
     * @return                              bitmap
     */
    fun doScreenShot(): Bitmap?

    /**
     * 释放资源
     */
    fun release()

}