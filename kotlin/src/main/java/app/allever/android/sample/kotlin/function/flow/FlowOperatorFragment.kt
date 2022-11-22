package app.allever.android.sample.kotlin.function.flow

import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.kotlin.BR
import app.allever.android.sample.kotlin.R
import app.allever.android.sample.kotlin.databinding.FragmentFlowOperatorBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowOperatorFragment :
    BaseMvvmFragment<FragmentFlowOperatorBinding, FlowOperatorViewModel>() {
    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_flow_operator, BR.flowOperatorVM)

    override fun init() {
        lifecycleScope.launch {
            launch {
                mViewModel.basicFlow.map {
                    "map转换后： $it -> String"
                }
                    .collect {
                        mBinding.tvFlow1.text = it
                    }
            }

            launch {
                delay(2000L)
                mViewModel.basicFlow2.filter {
                    it % 2 == 0
                }
                    .collect {
                        mBinding.tvFlow2.text = "过滤后: $it"
                    }
            }

            launch {
                mViewModel.basicFlow
                    .map {
                        it * it
                    }
                    .onEach {
                        log("onEach map -> $it")
                    }
                    .filter {
                        it % 2 == 0
                    }.onEach {
                        log("onEach filter -> $it")
                    }
                    .collect {
                        mBinding.tvFlow3.text = "onEach: $it"
                    }
            }
        }
    }
}

class FlowOperatorViewModel : BaseViewModel() {

    var i = 0
    val basicFlow = flow<Int> {
        while (true) {
            emit(i++)
            delay(1000)
        }
    }

    val basicFlow2 = flow<Int> {
        while (true) {
            emit(i++)
            delay(1000)
        }
    }

    val basicFlow3 = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
}