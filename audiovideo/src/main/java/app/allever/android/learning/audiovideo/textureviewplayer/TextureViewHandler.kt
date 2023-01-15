package app.allever.android.learning.audiovideo.textureviewplayer

import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import app.allever.android.learning.audiovideo.BasePlayerHandler
import app.allever.android.learning.audiovideo.StatusListener
import app.allever.android.lib.core.function.media.MediaBean

class TextureViewHandler : BasePlayerHandler(), TextureView.SurfaceTextureListener {

    private lateinit var mTextureView: TextureView
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

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mSurface = Surface(surface)
        try {
            mMediaBean.uri ?: return
            mMediaPlayer?.reset()
            mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer?.setDataSource(mTextureView.context, mMediaBean.uri ?: return)

            mMediaPlayer?.setSurface(mSurface)//添加渲染
            mMediaPlayer?.setOnPreparedListener(this)
            mMediaPlayer?.setOnCompletionListener(this)
            mMediaPlayer?.prepareAsync()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mSurface
        mMediaPlayer?.stop()
        mSurface.release()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

}