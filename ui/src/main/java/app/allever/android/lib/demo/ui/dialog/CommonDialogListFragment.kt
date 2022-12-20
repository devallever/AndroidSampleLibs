package app.allever.android.lib.demo.ui.dialog

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.demo.ui.dialog.commondialog.BottomDialog
import app.allever.android.lib.demo.ui.dialog.commondialog.CenterDialog
import com.chad.library.adapter.base.BaseQuickAdapter

class CommonDialogListFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter(): BaseQuickAdapter<String, *> = TextAdapter()

    override fun getList() = mutableListOf(
        "顶部弹窗",
        "中部弹窗",
        "底部弹窗"
    )

    override fun onItemClick(position: Int, item: String) {
        toast(item)
        when (position) {
            0 -> {
            }
            1 -> {
                CenterDialog(requireContext()).show()
            }
            2 -> {
                BottomDialog(requireContext()).show()
            }
        }
    }
}