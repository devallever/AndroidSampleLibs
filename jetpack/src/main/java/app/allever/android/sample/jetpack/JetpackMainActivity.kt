package app.allever.android.sample.jetpack

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class JetpackMainActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "JetPack"

    override fun getTabTitles() = mutableListOf("Paging")

    override fun getFragments(): MutableList<Fragment> = mutableListOf(EmptyFragment())
}