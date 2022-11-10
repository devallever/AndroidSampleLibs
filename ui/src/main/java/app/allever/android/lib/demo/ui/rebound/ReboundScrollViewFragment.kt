package app.allever.android.lib.demo.ui.rebound

import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentReboundScrollViewDemoBinding
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class ReboundScrollViewFragment: BaseMvvmFragment<FragmentReboundScrollViewDemoBinding, ReboundScrollViewViewModel>() {
    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_rebound_scroll_view_demo, BR.roundScrollViewModel)

    override fun init() {
    }
}

class ReboundScrollViewViewModel: BaseViewModel() {
    override fun init() {

    }
}