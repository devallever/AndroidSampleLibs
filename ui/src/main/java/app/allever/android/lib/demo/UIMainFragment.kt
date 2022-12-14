package app.allever.android.lib.demo

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.ui.*
import app.allever.android.lib.demo.ui.UIFragment
import app.allever.android.lib.demo.ui.autoscroll.AutoScrollMainFragment
import app.allever.android.lib.demo.ui.sticktop.StickyTopMainFragment
import com.chad.library.adapter.base.BaseQuickAdapter

class UIMainFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter(): BaseQuickAdapter<String, *> = TextAdapter()

    override fun getList() =
        mutableListOf(
            "UI效果",
            "吸顶效果",
            "个人主页折叠效果",
            "回弹效果",
            "自动滚动效果",
            "探探-交互效果",
            "Soul-交互效果",
            "分组联系人列表"
        )

    override fun onItemClick(position: Int, item: String) {
        val title = getList()[position]
        when (position) {
            0 -> {
                FragmentActivity.start<UIFragment>(title)
            }
            1 -> {
                FragmentActivity.start<StickyTopMainFragment>(title)
            }
            2 -> {
                ActivityHelper.startActivity<UserCenterActivity>()
            }
            3 -> {
                FragmentActivity.start<FlingScrollTabFragment>(title)
            }
            4 -> {
                FragmentActivity.start<AutoScrollMainFragment>(title)
            }
            5 -> {
                ActivityHelper.startActivity<TanTanActivity>()
            }
            6 -> {
                ActivityHelper.startActivity<SoulActivity>()
            }
            7 -> {
                FragmentActivity.start<ContactListFragment>(title)
            }
        }
    }
}