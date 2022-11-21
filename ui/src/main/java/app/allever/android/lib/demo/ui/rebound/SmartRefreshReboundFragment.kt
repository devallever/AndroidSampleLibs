package app.allever.android.lib.demo.ui.rebound

import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentSmartRefreshReboundBinding
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class SmartRefreshReboundFragment :
    BaseMvvmFragment<FragmentSmartRefreshReboundBinding, BaseViewModel>() {
    override fun getMvvmConfig() =
        MvvmConfig(R.layout.fragment_smart_refresh_rebound, BR.smartRefreshReboundViewModel)

    override fun init() {

        mBinding.smartRefreshLayout.setEnableRefresh(false)
        mBinding.smartRefreshLayout.setEnableLoadMore(false)
        mBinding.smartRefreshLayout.setEnableOverScrollDrag(true)

    }
}