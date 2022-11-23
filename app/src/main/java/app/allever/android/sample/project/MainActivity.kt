package app.allever.android.sample.project

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.UIMainActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.lib.widget.ripple.RippleHelper
import app.allever.android.sample.function.FunctionMainActivity
import app.allever.android.sample.jetpack.JetpackMainActivity
import app.allever.android.sample.kotlin.KotlinMainActivity
import app.allever.android.sample.project.databinding.ActivityMainBinding
import app.allever.android.sample.thirtypart.ThirtyPartMainActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun init() {
        initTopBar(getString(R.string.app_name), showBackIcon = false)

        RippleHelper.addRippleView(binding.btnUI)
        RippleHelper.addRippleView(binding.btnJetpack)
        RippleHelper.addRippleView(binding.btnKotlin)
        RippleHelper.addRippleView(binding.btnThirtyPart)
        RippleHelper.addRippleView(binding.btnFunction)

        binding.btnUI.setOnClickListener {
            ActivityHelper.startActivity<UIMainActivity> { }
        }

        binding.btnJetpack.setOnClickListener {
            ActivityHelper.startActivity<JetpackMainActivity> { }
        }

        binding.btnKotlin.setOnClickListener {
            ActivityHelper.startActivity<KotlinMainActivity> { }
        }

        binding.btnThirtyPart.setOnClickListener {
            ActivityHelper.startActivity<ThirtyPartMainActivity> { }
        }


        binding.btnFunction.setOnClickListener {
            ActivityHelper.startActivity<FunctionMainActivity> { }
        }
    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_main, BR.mainVM)
}

class MainViewModel : BaseViewModel() {
    override fun init() {
    }
}