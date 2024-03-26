package app.allever.android.sample.jni.mk

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.jni.mk.databinding.ActivityJniMkMainBinding

class JniMkMainActivity : BaseActivity<ActivityJniMkMainBinding, BaseViewModel>() {
    override fun init() {
        initTopBar("JNI-mk")
        FragmentHelper.addToContainer(
            supportFragmentManager,
            JniMkMainFragment(),
            R.id.fragmentContainer
        )
    }

    override fun inflateChildBinding() = ActivityJniMkMainBinding.inflate(layoutInflater)
}