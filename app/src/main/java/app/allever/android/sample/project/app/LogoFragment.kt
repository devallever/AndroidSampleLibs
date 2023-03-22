package app.allever.android.sample.project.app

import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.project.databinding.FragmentLogoBinding

class LogoFragment : BaseFragment<FragmentLogoBinding, BaseViewModel>() {
    override fun inflate() = FragmentLogoBinding.inflate(layoutInflater)

    override fun init() {
    }
}