package app.allever.android.learning.audiovideo.textureviewplayer

import android.content.Intent
import app.allever.android.learning.audiovideo.databinding.ActivityTextureViewPlayerBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.mvvm.base.BaseViewModel

class TextureViewPlayerActivity :
    BaseActivity<ActivityTextureViewPlayerBinding, TextureViewPlayerViewModel>() {
    override fun init() {
        mViewModel.initExtra(intent)
        binding.videoPlayerView.setData(mViewModel.mediaBean ?: return)
    }

    override fun inflateChildBinding() = ActivityTextureViewPlayerBinding.inflate(layoutInflater)

    override fun showTopBar() = false

    override fun isSupportSwipeBack() = false
}

class TextureViewPlayerViewModel : BaseViewModel() {
    var mediaBean: MediaBean? = null
    override fun init() {
    }

    fun initExtra(intent: Intent?) {
        mediaBean = intent?.getParcelableExtra("MEDIA_BEAN") ?: return
    }
}