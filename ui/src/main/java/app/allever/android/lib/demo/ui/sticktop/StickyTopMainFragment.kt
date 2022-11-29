package app.allever.android.lib.demo.ui.sticktop

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import com.chad.library.adapter.base.BaseQuickAdapter

class StickyTopMainFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter(): BaseQuickAdapter<String, *> = TextAdapter()

    override fun getList() = mutableListOf(
        "两个View隐藏/显示吸顶",
        "移除/添加View显示吸顶灯",
        "RecyclerView Header",
        "CoordinatorLayout + AppBarLayout"
    )

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                ActivityHelper.startActivity<BaseTwoViewStickyTopActivity> { }
            }
            1 -> {
                ActivityHelper.startActivity<RemoveAddViewStickyTopActivity> { }
            }
            2 -> {
                ActivityHelper.startActivity<RecyclerViewHeaderStickyTopActivity> { }
            }
            3 -> {
                ActivityHelper.startActivity<CoordinatorStickyTopActivity> { }
            }
        }
    }
}