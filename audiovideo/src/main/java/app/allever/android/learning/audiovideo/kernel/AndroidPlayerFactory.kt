package app.allever.android.learning.audiovideo.kernel

/**
 * MediaPlayer工厂类
 */
class AndroidPlayerFactory : AbsPlayerFactory() {
    override fun createPlayer() = AndroidPlayer()
}