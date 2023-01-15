package app.allever.android.sample.kotlin.function.flow

import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.kotlin.databinding.FragmentFlowBasicBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FlowBasicFragment : BaseMvvmFragment<FragmentFlowBasicBinding, FlowBasicViewModel>() {

    override fun inflate() = FragmentFlowBasicBinding.inflate(layoutInflater)

    override fun init() {
        lifecycleScope.launch {
            launch {
//                mViewModel.basicFlow.collect {
//                    val content = "计数器：$it"
//                    log(content)
//                    mBinding.tvCounter.text = content
//                }
            }


            launch {
                delay(2000)
//                mViewModel.basicFlow.collectLatest {
//                    val content = "计数器2：$it"
//                    log(content)
//                    mBinding.tvCounter2.text = content
//                    delay(3000)
//                }
            }
        }
    }
}

class FlowBasicViewModel : BaseViewModel() {
    val basicFlow = flow {
        var i = 0
        while (true) {
            emit(i++)
            delay(1000)
        }
    }

    override fun init() {

    }
}