package app.allever.android.sample.kotlin

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class KotlinMainActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "Kotlin"

    override fun getTabTitles() = mutableListOf("Flow", "Coroutine", "Higher Function")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(
            EmptyFragment("Flow"),
            EmptyFragment("Coroutine"),
            EmptyFragment("Higher Function")
        )
}