package app.allever.android.sample.videoeditor

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.videoeditor.databinding.ActivityVideoEditorMainBinding

class VideoEditorMainActivity : BaseActivity<ActivityVideoEditorMainBinding, BaseViewModel>() {

    override fun inflateChildBinding() = ActivityVideoEditorMainBinding.inflate(layoutInflater)

    override fun init() {
        initTopBar("视频编辑")
        FragmentHelper.addToContainer(
            supportFragmentManager,
            VideoEditorMainFragment(),
            binding.fragmentContainer.id
        )
    }

}