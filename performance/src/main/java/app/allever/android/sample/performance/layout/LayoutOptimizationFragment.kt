package app.allever.android.sample.performance.layout

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailItem
import app.allever.android.lib.common.databinding.FragmentListBinding

class LayoutOptimizationFragment :
    ListFragment<FragmentListBinding, ListViewModel, TextDetailItem>() {
    override fun getAdapter() = TextDetailAdapter()

    override fun getList() = mutableListOf(
        TextDetailItem("ViewStub延迟加载", "ViewStub延迟加载")
    )

    override fun onItemClick(position: Int, item: TextDetailItem) {
        when (position) {
            0 -> {
                FragmentActivity.start<ViewStubFragment>(item.title)
            }
        }
    }
}