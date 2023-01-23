package app.allever.android.sample.jetpack.navigation

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.jetpack.R
import app.allever.android.sample.jetpack.databinding.FragmentABinding

class AFragment : BaseFragment<FragmentABinding, BaseViewModel>() {
    override fun inflate() = FragmentABinding.inflate(layoutInflater)

    override fun init() {
        requireActivity().intent
        mBinding.btnJump.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("userId", "001")
            findNavController().navigate(R.id.action2B, bundle)
        }
    }
}