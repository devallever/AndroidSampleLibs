package app.allever.android.learning.audiovideo.videoviewplayer

import android.content.Intent
import android.widget.MediaController
import app.allever.android.learning.audiovideo.BR
import app.allever.android.learning.audiovideo.R
import app.allever.android.learning.audiovideo.databinding.ActivityVideoViewPlayerBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class VideoViewPlayerActivity :
    BaseActivity<ActivityVideoViewPlayerBinding, VideoViewPlayerViewModel>(),
    VideoViewHandler.StatusListener {
    private val videoViewHandler: VideoViewHandler by lazy {
        VideoViewHandler()
    }

    override fun getContentMvvmConfig() =
        MvvmConfig(R.layout.activity_video_view_player, BR.videoViewPlayerVM)

    override fun enableAdaptStatusBar() = false

    override fun init() {
        mViewModel.initExtra(intent)
        initListener()
        videoViewHandler.initVideoView(binding.videoView, mViewModel.mediaBean ?: return, MediaController(this), this)
        binding.tvTitle.text = mViewModel.mediaBean?.name
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivPlayPause.setOnClickListener {
            if (videoViewHandler.isPlaying()) {
                videoViewHandler.pause()
            } else {
                videoViewHandler.play()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoViewHandler.stop()
    }

    override fun showTopBar() = false
    override fun onVideoPlaying() {
        binding.ivPlayPause.setImageResource(app.allever.android.lib.widget.R.drawable.icon_album_video_preview_pause)
    }

    override fun onVideoPause() {
        binding.ivPlayPause.setImageResource(app.allever.android.lib.widget.R.drawable.icon_album_video_preview_play)
    }

    override fun onVideoError() {
        binding.ivPlayPause.setImageResource(app.allever.android.lib.widget.R.drawable.icon_album_video_preview_play)
    }

    override fun isSupportSwipeBack() = false


}


class VideoViewPlayerViewModel : BaseViewModel() {
    var mediaBean: MediaBean? = null
    override fun init() {

    }

    fun initExtra(intent: Intent?) {
        mediaBean = intent?.getParcelableExtra("MEDIA_BEAN")
    }
}