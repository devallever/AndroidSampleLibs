package app.allever.android.learning.audiovideo.videoviewplayer

import android.content.Intent
import app.allever.android.learning.audiovideo.databinding.ActivityVideoViewPlayerBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.mvvm.base.BaseViewModel

class VideoViewPlayerActivity :
    BaseActivity<ActivityVideoViewPlayerBinding, VideoViewPlayerViewModel>() {

    override fun inflateChildBinding() = ActivityVideoViewPlayerBinding.inflate(layoutInflater)

    override fun enableAdaptStatusBar() = false

    override fun init() {
        mViewModel.initExtra(intent)
        binding.videoPlayerView.setData(mViewModel.mediaBean ?: return)
    }

    override fun showTopBar() = false

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