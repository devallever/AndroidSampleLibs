package app.allever.android.learning.audiovideo

import app.allever.android.learning.audiovideo.databinding.ActivityMainBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.widget.ripple.RippleHelper

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun init() {
        initTopBar("音视频", showBackIcon = false)
        RippleHelper.addRippleView(binding.btnVideoViewPlayer)

        binding.btnVideoViewPlayer.setOnClickListener {
            ActivityHelper.startActivity(SelectMediaActivity::class.java)
        }


    }

    override fun inflateChildBinding() = ActivityMainBinding.inflate(layoutInflater)
}

class MainViewModel : BaseViewModel() {
    override fun init() {
    }
}