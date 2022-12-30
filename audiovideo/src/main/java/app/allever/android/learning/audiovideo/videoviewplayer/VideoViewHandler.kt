package app.allever.android.learning.audiovideo.videoviewplayer

import android.graphics.Color
import android.media.MediaPlayer
import android.widget.MediaController
import android.widget.VideoView
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.function.work.TimerTask2

class VideoViewHandler : MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private lateinit var mMediaPlayer: MediaPlayer
    private lateinit var mVideoView: VideoView
    private var mStatusListener: StatusListener? = null
    private var mMediaBean: MediaBean? = null

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

    override fun onCompletion(mediaPlayer: MediaPlayer) {
        mStatusListener?.onVideoError()
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        this.mMediaPlayer = mediaPlayer

        //适应屏幕显示
        mediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)

        //显示第一帧
        seekTo(1)
        mediaPlayer.setOnInfoListener { mp, what, extra ->
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                mVideoView.setBackgroundColor(Color.TRANSPARENT)
            }
            return@setOnInfoListener true
        }

        mStatusListener?.onPrepare(mMediaBean?.duration?.toInt() ?: 0)
        log("duration = ${mVideoView.duration}")
    }

    private val timerTask = TimerTask2(null, 1000L, true) {
        mStatusListener?.onVideoPlaying(mVideoView.currentPosition)
    }

    fun isPlaying() = mMediaPlayer.isPlaying

    fun play() {
        mVideoView.start()
        mStatusListener?.onVideoPlay()
        timerTask.start()
    }

    fun pause() {
        mVideoView.pause()
        timerTask.cancel()
        mStatusListener?.onVideoPause()
    }

    fun stop() {
        timerTask.cancel()
        mVideoView.pause()
        mMediaPlayer.release()
    }

    fun seekTo(value: Int) {
        mVideoView.seekTo(value)
    }

    interface StatusListener {
        fun onPrepare(duration: Int)
        fun onVideoPlay()
        fun onVideoPause()
        fun onVideoError()
        fun onVideoPlaying(currentPosition: Int)
    }
}