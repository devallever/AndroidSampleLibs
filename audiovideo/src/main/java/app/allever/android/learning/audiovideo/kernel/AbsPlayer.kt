package app.allever.android.learning.audiovideo.kernel

/***
 * 抽象播放器类
 */
abstract class AbsPlayer {
    abstract fun play()
    abstract fun pause()
    abstract fun stop()
    abstract fun release()
}