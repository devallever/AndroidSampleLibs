package app.allever.android.lib.demo.ui.dialog

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.demo.ui.dialog.bottomslide.BottomSlideDialog

class BottomSlideDialogListFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "基础底部滑动弹窗"
    )

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                BottomSlideDialog().show(childFragmentManager)
            }
        }
    }
}