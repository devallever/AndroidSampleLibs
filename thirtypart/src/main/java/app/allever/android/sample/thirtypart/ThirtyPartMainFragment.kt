package app.allever.android.sample.thirtypart

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.sample.thirtypart.ui.GlideFragment

class ThirtyPartMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf("Glide")

    override fun getFragments(): MutableList<Fragment> = mutableListOf(GlideFragment())
}