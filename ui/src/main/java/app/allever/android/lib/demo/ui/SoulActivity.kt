package app.allever.android.lib.demo.ui

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class SoulActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "Soul-交互效果"

    override fun getTabTitles() = mutableListOf("星球")

    override fun getFragments(): MutableList<Fragment> = mutableListOf(EmptyFragment("星球"))
}