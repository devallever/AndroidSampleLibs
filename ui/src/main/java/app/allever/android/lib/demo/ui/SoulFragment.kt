package app.allever.android.lib.demo.ui

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class SoulFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf("星球")

    override fun getFragments(): MutableList<Fragment> = mutableListOf(EmptyFragment("星球"))
}