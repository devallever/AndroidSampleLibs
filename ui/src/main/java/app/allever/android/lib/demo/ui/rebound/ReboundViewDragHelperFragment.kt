package app.allever.android.lib.demo.ui.rebound

import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentReboundViewDragHelperDemoBinding
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class ReboundViewDragHelperFragment :
    BaseMvvmFragment<FragmentReboundViewDragHelperDemoBinding, ReboundViewDragHelperViewViewModel>() {
    override fun getMvvmConfig() =
        MvvmConfig(R.layout.fragment_rebound_view_drag_helper_demo, BR.reboundViewDragHelper)

    override fun init() {
    }
}

class ReboundViewDragHelperViewViewModel : BaseViewModel() {
    override fun init() {

    }
}