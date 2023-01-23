package app.allever.android.sample.jetpack.ui

import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.jetpack.databinding.FragmentNavigationBinding
import app.allever.android.sample.jetpack.navigation.NavigationMainActivity

class NavigationFragment : BaseFragment<FragmentNavigationBinding, BaseViewModel>() {
    override fun inflate() = FragmentNavigationBinding.inflate(layoutInflater)
    override fun init() {
        mBinding.btnNavigation.setOnClickListener {
            ActivityHelper.startActivity<NavigationMainActivity> {  }
        }
    }
}