package app.allever.android.lib.demo.base

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.base.adapter.Pager2Adapter
import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.ActivityTabBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

abstract class TabActivity<DB, VM> : BaseActivity<ActivityTabBinding, TabViewModel>() {
    abstract fun getPageTitle(): String
    abstract fun getTabTitles(): MutableList<String>
    abstract fun getFragments(): MutableList<Fragment>
    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_tab, BR.tabViewModel)
    override fun init() {
        initTopBar(getPageTitle())
        binding.viewPager.adapter = Pager2Adapter(this, getFragments())
        binding.tabLayout.setViewPager2(binding.viewPager, getTabTitles() as ArrayList<String>?)
    }
}

class TabViewModel : BaseViewModel() {
    override fun init() {

    }
}