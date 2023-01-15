package app.allever.android.learning.audiovideo.surfaceviewplayer

import android.content.Intent
import app.allever.android.learning.audiovideo.databinding.ActivitySurfaceViewPlayerBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.mvvm.base.BaseViewModel

class SurfaceViewPlayerActivity :
    BaseActivity<ActivitySurfaceViewPlayerBinding, SurfaceViewPlayerViewModel>() {
    override fun init() {
        mViewModel.initExtra(intent)
        binding.videoPlayerView.setData(mViewModel.mediaBean ?: return)
    }

    override fun inflateChildBinding() = ActivitySurfaceViewPlayerBinding.inflate(layoutInflater)

    override fun showTopBar() = false

    override fun isSupportSwipeBack() = false
}

class SurfaceViewPlayerViewModel : BaseViewModel() {
    var mediaBean: MediaBean? = null
    override fun init() {
    }

    fun initExtra(intent: Intent?) {
        mediaBean = intent?.getParcelableExtra("MEDIA_BEAN") ?: return
    }
}