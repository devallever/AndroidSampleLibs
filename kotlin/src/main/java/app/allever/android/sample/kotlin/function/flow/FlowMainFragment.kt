package app.allever.android.sample.kotlin.function.flow

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding

class FlowMainFragment : TabFragment<ActivityTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf("基础", "操作符")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(FlowBasicFragment(), FlowOperatorFragment())
}