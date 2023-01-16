package app.allever.android.sample.jni

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class JniMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf(
        "Java调用C", "C调用Java"
    )

    override fun getFragments(): MutableList<Fragment> = mutableListOf(
        EmptyFragment("Java调用C"),
        EmptyFragment("C调用Java")
    )
}