package app.allever.android.learning.audiovideo.kernel

import app.allever.android.lib.core.function.player.kernel.internal.AbsPlayerFactory

/**
 * IJKPlayer工厂类
 */
class IJKPlayerFactory : AbsPlayerFactory() {
    override fun createPlayer() = IJKPlayer()
}