package app.allever.android.lib.demo.ui.autoscroll

import AutoScrollLoopFragment
import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding

class AutoScrollMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf("自动滚动（顶部）", "自动滚动（底部）", "自动滚动（循环）")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(
            ScrollTopRecyclerViewFragment(),
            ScrollBottomRecyclerViewFragment(),
            AutoScrollLoopFragment()
        )
}