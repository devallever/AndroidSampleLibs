package app.allever.android.sample.jetpack.navigation

import androidx.navigation.fragment.findNavController
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.core.helper.ViewHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.jetpack.databinding.FragmentBBinding

class BFragment : BaseFragment<FragmentBBinding, BaseViewModel>() {
    override fun inflate() = FragmentBBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}