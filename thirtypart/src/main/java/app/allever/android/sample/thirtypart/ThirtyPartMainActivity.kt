package app.allever.android.sample.thirtypart

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding
import app.allever.android.sample.thirtypart.ui.GlideFragment

class ThirtyPartMainActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "ThirtyPart"

    override fun getTabTitles() = mutableListOf("Glide")

    override fun getFragments(): MutableList<Fragment> = mutableListOf(GlideFragment())
}