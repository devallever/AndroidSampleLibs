package app.allever.android.sample.kotlin

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment
import app.allever.android.sample.kotlin.ui.FlowFragment

class KotlinMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {

    override fun getTabTitles() = mutableListOf("Flow", "Coroutine", "Higher Function")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(
            FlowFragment(),
            EmptyFragment("Coroutine"),
            EmptyFragment("Higher Function")
        )
}