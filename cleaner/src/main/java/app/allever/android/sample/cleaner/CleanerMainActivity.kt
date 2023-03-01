package app.allever.android.sample.cleaner

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.cleaner.databinding.ActivityCleanerMainBinding

class CleanerMainActivity : BaseActivity<ActivityCleanerMainBinding, BaseViewModel>() {
    override fun init() {
        initTopBar("清理大师(Demo)")
        FragmentHelper.addToContainer(
            supportFragmentManager,
            CleanerMainFragment(),
            binding.fragmentContainer.id
        )
    }

    override fun inflateChildBinding() = ActivityCleanerMainBinding.inflate(layoutInflater)
}