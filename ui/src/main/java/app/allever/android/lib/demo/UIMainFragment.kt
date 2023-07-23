package app.allever.android.lib.demo

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.ui.*
import app.allever.android.lib.demo.ui.autoscroll.AutoScrollMainFragment
import app.allever.android.lib.demo.ui.dialog.DialogMainFragment
import app.allever.android.lib.demo.ui.dragclose.DragCloseMainFragment
import app.allever.android.lib.demo.ui.expandrecycler.ContactExpandListFragment
import app.allever.android.lib.demo.ui.notification.NotificationMainFragment
import app.allever.android.lib.demo.ui.sticktop.StickyTopMainFragment
import app.allever.android.lib.widget.fragment.EmptyFragment
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
            "分组联系人列表",
            "弹窗",
            "通知",
            "拖拽关闭页面效果",
            "分组联系人列表-折叠"
        )

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                FragmentActivity.start<UIFragment>(item)
            }
            1 -> {
                FragmentActivity.start<StickyTopMainFragment>(item)
            }
            2 -> {
                ActivityHelper.startActivity<UserCenterActivity>()
            }
            3 -> {
                FragmentActivity.start<FlingScrollTabFragment>(item)
            }
            4 -> {
                FragmentActivity.start<AutoScrollMainFragment>(item)
            }
            5 -> {
                FragmentActivity.start<TanTanFragment>(item)
            }
            6 -> {
                FragmentActivity.start<SoulFragment>(item)
            }
            7 -> {
                FragmentActivity.start<ContactListFragment>(item)
            }
            8 -> {
                FragmentActivity.start<DialogMainFragment>(item)
            }
            9 -> FragmentActivity.start<NotificationMainFragment>(item)
            10 -> {
                FragmentActivity.start<DragCloseMainFragment>(item)
            }
            11 -> {
                FragmentActivity.start<ContactExpandListFragment>(item)
            }
        }
    }
}