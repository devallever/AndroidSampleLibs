package app.allever.android.lib.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import com.chad.library.adapter.base.BaseQuickAdapter

abstract class ListFragment<DB : ViewBinding, VM : BaseViewModel, T> :
    BaseFragment<FragmentListBinding, ListViewModel>() {

    private var mAdapter: BaseQuickAdapter<T, *>? = null

    override fun inflate() = FragmentListBinding.inflate(layoutInflater)

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