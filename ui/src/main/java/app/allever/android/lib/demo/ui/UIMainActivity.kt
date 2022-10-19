package app.allever.android.lib.demo.ui

import android.view.ViewOutlineProvider
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.ActivityUiMainBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import eightbitlab.com.blurview.RenderScriptBlur

class UIMainActivity : BaseActivity<ActivityUiMainBinding, UIMainViewModel>() {
    override fun init() {
        initTopBar("UI交互")

        binding.pressLikeView.setOnClickListener {
            binding.pressLikeView.show()
        }

        //毛玻璃效果
        binding.blurView.setupWith(binding.blurBg, RenderScriptBlur(this)) // or RenderEffectBlur
            .setBlurRadius(20f)
        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true

        binding.blurBg.setOnClickListener {
            ActivityHelper.startActivity<BlurActivity>()
        }
    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_ui_main, BR.uiMainVM)
}

class UIMainViewModel : BaseViewModel() {
    override fun init() {

    }
}