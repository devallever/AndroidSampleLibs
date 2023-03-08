package app.allever.android.learning.audiovideo.kernel

/**
 * IJKPlayer工厂类
 */
class IJKPlayerFactory : AbsPlayerFactory() {
    override fun createPlayer() = IJKPlayer()
}