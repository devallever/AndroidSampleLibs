package app.allever.android.sample.learning.android

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.learning.android.databinding.ActivityLearningAndroidMainBinding

class LearningAndroidMainActivity :
    BaseActivity<ActivityLearningAndroidMainBinding, BaseViewModel>() {
    override fun init() {
        initTopBar("LearningAndroid")
        FragmentHelper.addToContainer(
            supportFragmentManager,
            LearningAndroidMainFragment(),
            binding.fragmentContainer.id
        )
    }

    override fun inflateChildBinding() = ActivityLearningAndroidMainBinding.inflate(layoutInflater)
}