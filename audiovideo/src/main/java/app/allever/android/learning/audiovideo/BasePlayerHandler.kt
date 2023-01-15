package app.allever.android.learning.audiovideo

import android.media.MediaPlayer
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.function.work.TimerTask2

abstract class BasePlayerHandler : MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener {

    protected var mMediaPlayer: MediaPlayer? = null
    protected lateinit var mMediaBean: MediaBean
    protected var mStatusListener: StatusListener? = null

    private val timerTask = TimerTask2(null, 1000L, true) {
        mStatusListener?.onVideoPlaying(mMediaPlayer?.currentPosition ?: 0)
    }

    fun isPlaying(): Boolean = mMediaPlayer?.isPlaying ?: false

    fun getMediaPlayer() = mMediaPlayer

    open fun play() {
        mStatusListener?.onVideoPlay()
        timerTask.start()
    }

    open fun pause() {
        timerTask.cancel()
        mStatusListener?.onVideoPause()
    }

    open fun stop() {
        timerTask.cancel()
        mStatusListener?.onVideoPause()
    }

    open fun seekTo(value: Int) {
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mStatusListener?.onVideoError()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        if (mMediaPlayer == null) {
            mMediaPlayer = mp
        }
        //适应屏幕显示
        mMediaPlayer?.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
        //显示第一帧
        seekTo(1)
        mStatusListener?.onPrepare(mMediaBean.duration.toInt())
        log("duration = ${mMediaPlayer?.duration}")
    }
}