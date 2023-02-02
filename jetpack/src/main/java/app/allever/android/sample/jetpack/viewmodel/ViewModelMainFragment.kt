package app.allever.android.sample.jetpack.viewmodel

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.demo.MainViewModel
import kotlinx.coroutines.launch

class ViewModelMainFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    private val viewModel1 by viewModels<ViewModelMainViewModel>()



    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "by viewModels<ViewModel>()",
        "ViewModelProvider(this).get(ViewModel::class.java)",
        "指定ViewModelStoreOwner\nownerProducer = { requireParentFragment() }",
        "fragment获取activity的viewModel\nby activityViewModels()",
        "viewModelScope"
    )

    override fun init() {
        super.init()
        log(viewModel1.content)

        val viewModel2 = ViewModelProvider(this).get(ViewModelMainViewModel::class.java)
        log(viewModel2.content2)

        log("viewModel1 hashCode = ${viewModel1.hashCode()}")
        log("viewModel2 hashCode = ${viewModel2.hashCode()}")

        //没有父Fragment则不能用
//        val parentFragmentViewModel: BaseViewModel by viewModels(
//            ownerProducer = { requireParentFragment() }
//        )
//
//        log("parentViewModel = ${parentFragmentViewModel.javaClass.simpleName}")

        val activityViewModel: BaseViewModel by activityViewModels()
        log("activityViewModel = ${activityViewModel.javaClass.simpleName}")
    }
}

class ViewModelMainViewModel() : ViewModel() {
    var content = "by viewModels<ViewModel>()"
    var content2 = "ViewModelProvider(this).get(ViewModel::class.java)"
    init {
        test()
    }
    private fun test() {
        viewModelScope.launch {
            log("viewModelScope")
        }
    }
}