package app.allever.android.learning.audiovideo

import app.allever.android.learning.audiovideo.databinding.ActivityMainBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.stickytop.StickyTopMainActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.lib.widget.ripple.RippleHelper

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun init() {
        initTopBar(getString(R.string.app_name), showBackIcon = false)

        RippleHelper.addRippleView(binding.btnStickyTop)

        binding.btnStickyTop.setOnClickListener {
            ActivityHelper.startActivity<StickyTopMainActivity> {  }
        }
    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_main, BR.mainVM)
}

class MainViewModel : BaseViewModel() {
    override fun init() {
    }
}