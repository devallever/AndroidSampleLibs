package app.allever.android.learning.audiovideo.textureviewplayer

import android.graphics.Color
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.ThumbnailUtils
import android.view.Surface
import android.view.TextureView
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.function.work.TimerTask2

class TextureViewHandler : MediaPlayer.OnCompletionListener, TextureView.SurfaceTextureListener,
    MediaPlayer.OnPreparedListener {

    private lateinit var mTextureView: TextureView
    private lateinit var mMediaBean: MediaBean
    private var mStatusListener: StatusListener? = null
    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mSurface: Surface

    fun initVideoView(
        textureView: TextureView,
        mediaBean: MediaBean,
        statusListener: StatusListener? = null
    ) {
        mMediaBean = mediaBean
        mTextureView = textureView
        mStatusListener = statusListener
        mMediaPlayer = MediaPlayer()
        mTextureView.surfaceTextureListener = this
    }


    private val timerTask = TimerTask2(null, 1000L, true) {
        mStatusListener?.onVideoPlaying(mMediaPlayer.currentPosition)
    }

    fun getMediaPlayer() = mMediaPlayer

    fun isPlaying() = mMediaPlayer.isPlaying

    fun play() {
        mMediaPlayer.start()
        mStatusListener?.onVideoPlay()
        timerTask.start()
    }

    fun pause() {
        mMediaPlayer.pause()
        timerTask.cancel()
        mStatusListener?.onVideoPause()
    }

    fun stop() {
        timerTask.cancel()
        mMediaPlayer.stop()
    }

    fun seekTo(value: Int) {
        mMediaPlayer.seekTo(value)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mStatusListener?.onVideoError()
    }

    interface StatusListener {
        fun onPrepare(duration: Int)
        fun onVideoPlay()
        fun onVideoPause()
        fun onVideoError()
        fun onVideoPlaying(currentPosition: Int)
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mSurface = Surface(surface)
        try {
            mMediaBean.uri ?: return
            mMediaPlayer.reset()
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer.setDataSource(mTextureView.context, mMediaBean.uri ?: return)

            mMediaPlayer.setSurface(mSurface)//添加渲染
            mMediaPlayer.setOnPreparedListener(this)
            mMediaPlayer.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mSurface
        mMediaPlayer.stop()
        mSurface.release()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
    }

    override fun onPrepared(mp: MediaPlayer?) {
        //适应屏幕显示
        mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
        //显示第一帧
        seekTo(1)
        mStatusListener?.onPrepare(mMediaBean.duration.toInt())
        log("duration = ${mMediaPlayer.duration}")
    }


}