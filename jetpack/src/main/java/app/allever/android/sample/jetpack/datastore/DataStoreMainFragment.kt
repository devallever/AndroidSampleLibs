package app.allever.android.sample.jetpack.datastore

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class DataStoreMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf(
        "Preferences DataStore",
        "Proto DataStore"
    )

    override fun getFragments(): MutableList<Fragment> = mutableListOf(
        PreferenceStoreFragment(),
        EmptyFragment("Proto DataStore")
    )
}