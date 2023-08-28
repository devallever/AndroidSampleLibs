package app.allever.android.sample.gaodemap

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding

class GaoDeMapMainListFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun init() {
        super.init()
        AMapHelper.init(requireContext())
    }

    override fun getAdapter() = TextClickAdapter()

    override fun getList(): MutableList<TextClickItem> = mutableListOf(
        TextClickItem("基础地图") {
            FragmentActivity.start<GaoDeBaseMapFragment>(it.title)
        }
    )
}