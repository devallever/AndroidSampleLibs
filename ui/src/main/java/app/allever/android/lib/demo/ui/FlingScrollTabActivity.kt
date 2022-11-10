package app.allever.android.lib.demo.ui

import androidx.fragment.app.Fragment
import app.allever.android.lib.demo.base.TabActivity
import app.allever.android.lib.demo.base.TabViewModel
import app.allever.android.lib.demo.databinding.ActivityTabBinding
import app.allever.android.lib.demo.ui.rebound.ReboundScrollViewFragment
import app.allever.android.lib.demo.ui.rebound.ReboundViewDragHelperFragment

class FlingScrollTabActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "回弹效果"

    override fun getTabTitles(): MutableList<String> =
        mutableListOf("ScrollView方式", "ViewDragHelper方式")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(
            ReboundScrollViewFragment(),
            ReboundViewDragHelperFragment(),
        )
}