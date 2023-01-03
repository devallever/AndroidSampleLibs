package app.allever.android.learning.audiovideo.surfaceviewplayer

import android.content.Intent
import app.allever.android.learning.audiovideo.BR
import app.allever.android.learning.audiovideo.R
import app.allever.android.learning.audiovideo.databinding.ActivitySurfaceViewPlayerBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class SurfaceViewPlayerActivity :
    BaseActivity<ActivitySurfaceViewPlayerBinding, SurfaceViewPlayerViewModel>() {
    override fun init() {
        mViewModel.initExtra(intent)
        binding.videoPlayerView.setData(mViewModel.mediaBean ?: return)
    }

    override fun getContentMvvmConfig() =
        MvvmConfig(R.layout.activity_surface_view_player, BR.surfaceViewPlayerVM)

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