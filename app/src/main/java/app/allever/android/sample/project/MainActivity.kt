package app.allever.android.sample.project

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.core.util.TimeUtils
import app.allever.android.lib.demo.UIMainActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.lib.widget.ripple.RippleHelper
import app.allever.android.sample.function.FunctionMainActivity
import app.allever.android.sample.jetpack.JetpackMainActivity
import app.allever.android.sample.kotlin.KotlinMainActivity
import app.allever.android.sample.project.databinding.ActivityMainBinding
import app.allever.android.sample.thirtypart.ThirtyPartMainFragment

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
//            ActivityHelper.startActivity<ThirtyPartMainActivity> { }
            FragmentActivity.start<ThirtyPartMainFragment>("ThirtyPart")
        }


        binding.btnFunction.setOnClickListener {
            ActivityHelper.startActivity<FunctionMainActivity> { }
        }

        val time1 = "2022-11-25 18:00:00:001"
        val time2 = "2022-11-25 18:00:00:201"
        val time1Ms = TimeUtils.getTimeStamp(time1, TimeUtils.FORMAT_yyyy_MM_dd_hh_mm_ss_SSS)
        val time2Ms = TimeUtils.getTimeStamp(time2, TimeUtils.FORMAT_yyyy_MM_dd_hh_mm_ss_SSS)
        val interval = time2Ms - time1Ms
        log("时间间隔 = $interval")
    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_main, BR.mainVM)
}

class MainViewModel : BaseViewModel() {
    override fun init() {
    }
}