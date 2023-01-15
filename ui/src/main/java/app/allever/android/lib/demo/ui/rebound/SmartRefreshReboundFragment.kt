package app.allever.android.lib.demo.ui.rebound

import app.allever.android.lib.demo.databinding.FragmentSmartRefreshReboundBinding
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel

class SmartRefreshReboundFragment :
    BaseMvvmFragment<FragmentSmartRefreshReboundBinding, BaseViewModel>() {

    override fun inflate() = FragmentSmartRefreshReboundBinding.inflate(layoutInflater)

    override fun init() {

        mBinding.smartRefreshLayout.setEnableRefresh(false)
        mBinding.smartRefreshLayout.setEnableLoadMore(false)
        mBinding.smartRefreshLayout.setEnableOverScrollDrag(true)

    }
}