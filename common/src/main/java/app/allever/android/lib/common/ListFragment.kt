package app.allever.android.lib.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import com.chad.library.adapter.base.BaseQuickAdapter

abstract class ListFragment<DB : ViewDataBinding, VM : BaseViewModel, T> :
    BaseFragment<FragmentListBinding, ListViewModel>() {
    private var mAdapter: BaseQuickAdapter<T, *>? = null
    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_list, BR.listVM)

    override fun init() {

        mBinding.smartRefreshLayout.setEnableOverScrollDrag(true)

        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)

        mAdapter = getAdapter()
        mBinding.recyclerView.adapter = mAdapter

        mAdapter?.setList(getList())

        mAdapter?.setOnItemClickListener { adapter, view, position ->
            onItemClick(position, mAdapter?.getItem(position) ?: return@setOnItemClickListener)
        }
    }

    abstract fun getAdapter(): BaseQuickAdapter<T, *>
    abstract fun getList(): MutableList<T>
    open protected fun onItemClick(position: Int, item: T) {

    }
}

class ListViewModel : BaseViewModel() {

}