package app.allever.android.lib.demo

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.ui.*
import app.allever.android.lib.demo.ui.UIMainActivity
import app.allever.android.lib.demo.ui.autoscroll.AutoScrollMainFragment
import app.allever.android.lib.demo.ui.sticktop.StickyTopMainFragment
import com.chad.library.adapter.base.BaseQuickAdapter

class DemoMainListFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter(): BaseQuickAdapter<String, *> = TextAdapter()

    override fun getList() =
        mutableListOf(
            "UI效果",
            "吸顶效果",
            "个人主页折叠效果",
            "回弹效果",
            "自动滚动效果且随时设置间隔",
            "探探-交互效果",
            "Soul-交互效果"
        )

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                ActivityHelper.startActivity<UIMainActivity>()
            }
            1 -> {
                FragmentActivity.start<StickyTopMainFragment>("吸顶Demo")
            }
            2 -> {
                ActivityHelper.startActivity<UserCenterActivity>()
            }
            3 -> {
                ActivityHelper.startActivity<FlingScrollTabActivity>()
            }
            4 -> {
                FragmentActivity.start<AutoScrollMainFragment>("自动滚动效果")
//                ActivityHelper.startActivity<AutoScrollActivity>()
            }
            5 -> {
                ActivityHelper.startActivity<TanTanActivity>()
            }
            6 -> {
                ActivityHelper.startActivity<SoulActivity>()
            }
        }
    }
}