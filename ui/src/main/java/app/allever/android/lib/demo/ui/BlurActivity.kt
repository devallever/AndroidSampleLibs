package app.allever.android.lib.demo.ui

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.ActivityBlurBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class BlurActivity : BaseActivity<ActivityBlurBinding, BlurViewModel>(){
    override fun init() {
        initTopBar("毛玻璃效果")
    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_blur, BR.blurViewModel)
}

class BlurViewModel: BaseViewModel() {
    override fun init() {

    }

}