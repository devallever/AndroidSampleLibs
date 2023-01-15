package app.allever.android.lib.demo.ui.rebound

import app.allever.android.lib.demo.databinding.FragmentReboundScrollViewDemoBinding
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel

class ReboundScrollViewFragment :
    BaseMvvmFragment<FragmentReboundScrollViewDemoBinding, ReboundScrollViewViewModel>() {

    override fun inflate() = FragmentReboundScrollViewDemoBinding.inflate(layoutInflater)

    override fun init() {
    }
}

class ReboundScrollViewViewModel : BaseViewModel() {
    override fun init() {

    }
}