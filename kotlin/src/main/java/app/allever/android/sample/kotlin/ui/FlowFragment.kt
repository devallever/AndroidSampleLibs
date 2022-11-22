package app.allever.android.sample.kotlin.ui

import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.lib.widget.ripple.RippleHelper
import app.allever.android.sample.kotlin.BR
import app.allever.android.sample.kotlin.R
import app.allever.android.sample.kotlin.databinding.FragmentFlowBinding
import app.allever.android.sample.kotlin.function.flow.FlowMainActivity

class FlowFragment : BaseMvvmFragment<FragmentFlowBinding, BaseViewModel>() {
    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_flow, BR.flowVM)

    override fun init() {
        RippleHelper.addRippleView(mBinding.tvFlow)

        mBinding.tvFlow.setOnClickListener {
            ActivityHelper.startActivity<FlowMainActivity> { }
        }
    }
}
