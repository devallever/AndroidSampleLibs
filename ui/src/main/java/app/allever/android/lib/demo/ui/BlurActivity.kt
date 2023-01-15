package app.allever.android.lib.demo.ui

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.demo.databinding.ActivityBlurBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class BlurActivity : BaseActivity<ActivityBlurBinding, BlurViewModel>() {
    override fun init() {
        initTopBar("毛玻璃效果")
    }

    override fun inflateChildBinding() = ActivityBlurBinding.inflate(layoutInflater)
}

class BlurViewModel : BaseViewModel() {
    override fun init() {

    }

}