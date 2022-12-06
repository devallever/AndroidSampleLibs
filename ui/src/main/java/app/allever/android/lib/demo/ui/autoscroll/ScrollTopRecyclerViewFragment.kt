package app.allever.android.lib.demo.ui.autoscroll

import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentScrollTopRecyclerViewBinding
import app.allever.android.lib.demo.ui.widget.SmartAutoScrollRecyclerView
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class ScrollTopRecyclerViewFragment :
    BaseFragment<FragmentScrollTopRecyclerViewBinding, BaseViewModel>() {
    override fun getMvvmConfig() =
        MvvmConfig(R.layout.fragment_scroll_top_recycler_view, BR.scrollTopRvVm)

    private val mAdapter = TextAdapter()

    override fun init() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.direction = SmartAutoScrollRecyclerView.DIRECTION_TOP
        mBinding.recyclerView.start()
        initTestData()
    }

    private fun initTestData() {
        val list = mutableListOf<String>()
        for (i in 0..29) {
            list.add("$i")
        }
        mAdapter.setList(list)
    }
}