package app.allever.android.lib.demo.ui

import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.demo.databinding.FragmentRippleBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class RippleFragment: BaseFragment<FragmentRippleBinding, BaseViewModel>() {
    override fun inflate() = FragmentRippleBinding.inflate(layoutInflater)

    override fun init() {

    }
}