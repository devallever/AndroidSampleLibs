package app.allever.android.lib.demo

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.databinding.ActivityDemoMainBinding
import app.allever.android.lib.demo.ui.AutoScrollActivity
import app.allever.android.lib.demo.ui.FlingScrollTabActivity
import app.allever.android.lib.demo.ui.UIMainActivity
import app.allever.android.lib.demo.ui.sticktop.StickyTopMainActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class UIMainActivity : BaseActivity<ActivityDemoMainBinding, DemoMainViewModel>() {
    override fun getContentMvvmConfig(): MvvmConfig =
        MvvmConfig(R.layout.activity_demo_main, BR.demoMainVM)

    override fun init() {
        initTopBar("UI交互")

        binding.btnUI.setOnClickListener {
            ActivityHelper.startActivity<UIMainActivity>()
        }

        binding.btnStickyTop.setOnClickListener {
            ActivityHelper.startActivity<StickyTopMainActivity>()
        }

        binding.btnUserCenter.setOnClickListener {
            ActivityHelper.startActivity<UserCenterActivity>()
        }

        binding.btnFlingScroll.setOnClickListener {
            ActivityHelper.startActivity<FlingScrollTabActivity>()
        }

        binding.btnAutoScroll.setOnClickListener {
            ActivityHelper.startActivity<AutoScrollActivity>()
        }

    }
}

class DemoMainViewModel : BaseViewModel() {
    override fun init() {

    }
}