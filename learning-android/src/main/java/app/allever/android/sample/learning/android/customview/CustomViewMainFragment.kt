package app.allever.android.sample.learning.android.customview

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding

class CustomViewMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("Canvas API") {
            FragmentActivity.start<CanvasApiFragment>(it.title)
        },
        TextClickItem("Paint API") {
            FragmentActivity.start<PaintApiFragment>(it.title)
        }
    )
}