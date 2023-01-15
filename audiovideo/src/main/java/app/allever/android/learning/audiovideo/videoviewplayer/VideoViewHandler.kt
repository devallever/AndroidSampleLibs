package app.allever.android.learning.audiovideo.videoviewplayer

import android.media.MediaPlayer
import android.widget.MediaController
import android.widget.VideoView
import app.allever.android.learning.audiovideo.BasePlayerHandler
import app.allever.android.learning.audiovideo.StatusListener
import app.allever.android.lib.core.function.media.MediaBean

class VideoViewHandler : BasePlayerHandler(), MediaPlayer.OnPreparedListener {

    private lateinit var mVideoView: VideoView

    fun initVideoView(
        videoView: VideoView,
        mediaBean: MediaBean,
        mediaController: MediaController? = null,
        statusListener: StatusListener? = null
    ) {
        this.mVideoView = videoView
        mMediaBean = mediaBean
        videoView.setOnCompletionListener(this)
        //处理开始播放时的短暂黑屏
        videoView.setOnPreparedListener(this)
        videoView.setOnErrorListener { mediaPlayer, i, i2 ->
            return@setOnErrorListener true
        }
//        mediaController?.setAnchorView(videoView)
//        videoView.setMediaController(mediaController)
        videoView.setVideoURI(mediaBean.uri)
        mStatusListener = statusListener
    }

    override fun play() {
        super.play()
        mVideoView.start()
    }

    override fun pause() {
        super.pause()
        mVideoView.pause()
    }

    override fun stop() {
        super.stop()
        mVideoView.pause()
        mMediaPlayer?.release()
    }

    override fun seekTo(value: Int) {
        super.seekTo(value)
        mVideoView.seekTo(value)
    }
}