package app.allever.android.sample.jetpack.navigation

import androidx.navigation.fragment.findNavController
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.core.helper.ViewHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.jetpack.R
import app.allever.android.sample.jetpack.databinding.FragmentABinding

class AFragment : BaseFragment<FragmentABinding, BaseViewModel>() {
    override fun inflate() = FragmentABinding.inflate(layoutInflater)

    override fun init() {
        mBinding.btnJump.setOnClickListener {
            findNavController().navigate(R.id.action2B)
        }
    }
}