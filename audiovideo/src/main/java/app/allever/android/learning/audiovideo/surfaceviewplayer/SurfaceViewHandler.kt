package app.allever.android.learning.audiovideo.surfaceviewplayer

import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import app.allever.android.learning.audiovideo.BasePlayerHandler
import app.allever.android.learning.audiovideo.StatusListener
import app.allever.android.lib.core.function.media.MediaBean

class SurfaceViewHandler : BasePlayerHandler(), SurfaceHolder.Callback {
    private lateinit var mSurfaceView: SurfaceView
    private lateinit var mSurface: Surface
    private lateinit var mSurfaceHolder: SurfaceHolder

    fun initVideoView(
        surfaceView: SurfaceView,
        mediaBean: MediaBean,
        statusListener: StatusListener? = null
    ) {
        mMediaBean = mediaBean
        mSurfaceView = surfaceView
        mStatusListener = statusListener
        mMediaPlayer = MediaPlayer()
        mSurfaceHolder = mSurfaceView.holder
        mSurfaceHolder.addCallback(this)
    }

    override fun play() {
        super.play()
        mMediaPlayer?.start()
    }

    override fun pause() {
        super.pause()
        mMediaPlayer?.pause()
    }

    override fun stop() {
        super.stop()
        mMediaPlayer?.stop()
    }

    override fun seekTo(value: Int) {
        super.seekTo(value)
        mMediaPlayer?.seekTo(value)
    }

    fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mSurface = Surface(surface)
        try {
            mMediaBean.uri ?: return
            mMediaPlayer?.reset()
            mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer?.setDataSource(mSurfaceView.context, mMediaBean.uri ?: return)

            mMediaPlayer?.setOnPreparedListener(this)
            mMediaPlayer?.setOnCompletionListener(this)
            mMediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            mMediaPlayer?.setDisplay(mSurfaceHolder)
            mMediaBean.uri ?: return
            mMediaPlayer?.reset()
            mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer?.setDataSource(mSurfaceView.context, mMediaBean.uri ?: return)

            mMediaPlayer?.setOnPreparedListener(this)
            mMediaPlayer?.setOnCompletionListener(this)
            // 通过异步的方式装载媒体资源
            mMediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }
}