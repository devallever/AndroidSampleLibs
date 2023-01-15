package app.allever.android.lib.demo.ui.autoscroll

import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.demo.databinding.FragmentScrollBottomRecyclerViewBinding
import app.allever.android.lib.demo.ui.widget.SmartAutoScrollRecyclerView
import app.allever.android.lib.mvvm.base.BaseViewModel

class ScrollBottomRecyclerViewFragment :
    BaseFragment<FragmentScrollBottomRecyclerViewBinding, BaseViewModel>() {

    override fun inflate() = FragmentScrollBottomRecyclerViewBinding.inflate(layoutInflater)

    private val mAdapter = TextAdapter()

    override fun init() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.direction = SmartAutoScrollRecyclerView.DIRECTION_BOTTOM
        mBinding.recyclerView.start()
        initTestData()
    }

    private fun initTestData() {
        val list = mutableListOf<String>()
        for (i in 0..299) {
            list.add("$i")
        }
        mAdapter.setList(list)
    }
}