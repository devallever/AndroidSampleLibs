package app.allever.android.sample.performance.layout

import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.performance.databinding.FragmentViewStubBinding
import app.allever.android.sample.performance.databinding.LayoutViewStubContentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewStubFragment : BaseFragment<FragmentViewStubBinding, BaseViewModel>() {

    private var mStubBinding: LayoutViewStubContentBinding? = null

    override fun inflate(): FragmentViewStubBinding =
        FragmentViewStubBinding.inflate(layoutInflater)

    override fun init() {
        lifecycleScope.launch {
            delay(3000)
            mStubBinding = if (mStubBinding == null) {
                LayoutViewStubContentBinding.bind(mBinding.viewStubLayout.inflate())
            } else {
                mStubBinding
            }
            delay(1000)
            mStubBinding?.tvStub?.text = "LayoutViewStubContentBinding"
        }
    }
}