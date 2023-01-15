package app.allever.android.sample.kotlin.function.flow

import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.kotlin.databinding.FragmentFlowOperatorBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FlowOperatorFragment :
    BaseMvvmFragment<FragmentFlowOperatorBinding, FlowOperatorViewModel>() {

    override fun inflate() = FragmentFlowOperatorBinding.inflate(layoutInflater)

    override fun init() {
        lifecycleScope.launch {
            launch {
//                mapOperator()
            }

            launch {
//                filterOperator()
            }

            launch {
//                onEachOperator()
            }

            launch {
//                debounceOperator()
            }

            launch(Dispatchers.Default) {
//                sampleOperator()
            }

            launch {
//                reduceOperator()
            }

            launch {
//                foldOperator()
            }

            launch {
                flatMapConcatOperator()
            }

            launch {
                flatMapConcatScene()
            }
        }
    }

    private suspend fun mapOperator() {
        mViewModel.basicFlow.map {
            val text = "map转换后： $it -> String"
            log(text)
            text
        }
            .collect {
                mBinding.tvFlow1.text = it
            }
    }

    @OptIn(FlowPreview::class)
    private suspend fun debounceOperator() {
        mViewModel.debounceFlow
            .debounce(500L)
            .collect {
                val text = "debounceOperator: $it"
                log(text)
            }
    }

    private suspend fun onEachOperator() {
        mViewModel.basicFlow3
            .map {
                it * it
            }
            .onEach {
                log("onEachOperator map -> $it")
            }
            .filter {
                it % 2 == 0
            }.onEach {
                log("onEachOperator filter -> $it")
            }
            .collect {
                mBinding.tvFlow3.text = "onEach: $it"
            }
    }

    private suspend fun filterOperator() {
        mViewModel.basicFlow2.filter {
            it % 2 == 0
        }
            .collect {
                val text = "过滤后: $it"
                log(text)
                mBinding.tvFlow2.text = text
            }
    }

    @OptIn(FlowPreview::class)
    private suspend fun sampleOperator() {
        mViewModel.sampleFlow.sample(1000)
            .collect {
                log("sampleOperator: $it")
            }
    }

    private suspend fun reduceOperator() {
        val result = mViewModel.reduceFlow.reduce { accumulator, value ->
            accumulator + value
        }
        lifecycleScope.launch(Dispatchers.Main) {
            mBinding.tvReduceResult.text = "reduce: $result"
        }
    }

    private suspend fun foldOperator() {
        val result = mViewModel.foldFlow.fold(10000) { accumulator, value ->
            accumulator + value
        }
        lifecycleScope.launch(Dispatchers.Main) {
            mBinding.tvFoldResult.text = "fold 初始值10000: $result"
        }
    }

    private suspend fun flatMapConcatOperator() {
        val builder = StringBuilder()
        flowOf(1, 2, 3)
            .flatMapConcat {
                flowOf("${it}a", "${it}b")
            }
            .collect {
                lifecycleScope.launch(Dispatchers.Main) {
                    builder.append(it).append(", ")
                    mBinding.tvFlatMapConcat.text = builder.toString()
                }
            }
    }

    private suspend fun flatMapConcatScene() {
        getToken().flatMapConcat { token ->
            getUserInfo(token)
        }
            .flowOn(Dispatchers.IO)
            .collect { userInfo ->
                lifecycleScope.launch(Dispatchers.Main) {
                    mBinding.tvFlatMapConcatScene.text = "userInfo: $userInfo"
                }
            }
    }

    private suspend fun getToken() = flow { emit("token_xxxx") }

    private suspend fun getUserInfo(token: String) = flow { emit("allever") }
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

    val basicFlow3 = flow<Int> {
        while (true) {
            emit(i++)
            delay(1000)
        }
    }

    val debounceFlow = flow {
        emit(1)
        emit(2)
        delay(600)
        emit(3)
        delay(100)
        emit(4)
        delay(100)
        emit(5)
    }

    val sampleFlow = flow {
        while (true) {
            emit("发送弹幕")
        }
    }

    val reduceFlow = flow {
        for (i in (1..100)) {
            emit(i)
        }
    }

    val foldFlow = flow {
        for (i in (1..100)) {
            emit(i)
        }
    }
}