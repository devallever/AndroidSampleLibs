package app.allever.android.sample.kotlin.function.flow

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class FlowMainActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "Flow"

    override fun getTabTitles() = mutableListOf("基础", "操作符")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(EmptyFragment("基础"), EmptyFragment("操作符"))
}