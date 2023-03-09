package app.allever.android.learning.audiovideo.render

import app.allever.android.learning.audiovideo.kernel.AbsPlayer

/**
 * 渲染接口
 */
interface IRenderView {

    /***
     *  绑定播放器
     */
    fun attachPlayer(player: AbsPlayer)

    /***
     * 设置显示宽高
     */
    fun setDisplaySize(width: Int, height: Int)
}