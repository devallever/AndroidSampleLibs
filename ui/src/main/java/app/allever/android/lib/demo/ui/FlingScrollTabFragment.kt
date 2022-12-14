package app.allever.android.lib.demo.ui

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.demo.ui.rebound.ReboundScrollViewFragment
import app.allever.android.lib.demo.ui.rebound.ReboundViewDragHelperFragment
import app.allever.android.lib.demo.ui.rebound.SmartRefreshReboundFragment

class FlingScrollTabFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles(): MutableList<String> =
        mutableListOf("ScrollView方式", "ViewDragHelper方式", "SmartRefreshLayout方式")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(
            ReboundScrollViewFragment(),
            ReboundViewDragHelperFragment(),
            SmartRefreshReboundFragment()
        )
}