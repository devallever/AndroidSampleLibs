package app.allever.android.sample.jni

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.jni.databinding.ActivityJniMainBinding

class JniMainActivity : BaseActivity<ActivityJniMainBinding, BaseViewModel>() {
    override fun init() {
        initTopBar("JNI")
        FragmentHelper.addToContainer(
            supportFragmentManager,
            JniMainFragment(),
            R.id.fragmentContainer
        )
    }

    override fun inflateChildBinding() = ActivityJniMainBinding.inflate(layoutInflater)
}