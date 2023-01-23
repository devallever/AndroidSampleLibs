package app.allever.android.sample.jetpack

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment
import app.allever.android.sample.jetpack.ui.NavigationFragment
import app.allever.android.sample.jetpack.ui.PagingFragment

class JetpackMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf(
        "Paging",
        "Navigation",
        "DataStore",
        "Room",
        "LifeCycle",
        "LiveData",
        "ViewModel",
        "DataBinding",
        "ViewBinding"
    )

    override fun getFragments(): MutableList<Fragment> = mutableListOf(
        PagingFragment(),
        NavigationFragment(),
        EmptyFragment("DataStore"),
        EmptyFragment("Room"),
        EmptyFragment("Lifecycle"),
        EmptyFragment("LiveData"),
        EmptyFragment("ViewModel"),
        EmptyFragment("DataBinding"),
        EmptyFragment("ViewBinding"),
    )
}