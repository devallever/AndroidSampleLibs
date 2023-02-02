package app.allever.android.sample.jetpack

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment
import app.allever.android.sample.jetpack.lifecycle.LifeCycleMainFragment
import app.allever.android.sample.jetpack.livedata.LiveDataMainFragment
import app.allever.android.sample.jetpack.navigation.NavigationFragment
import app.allever.android.sample.jetpack.paging3.PagingFragment
import app.allever.android.sample.jetpack.viewmodel.ViewModelMainFragment

class JetpackMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf(
        "Paging",
        "Navigation",
        "ViewModel",
        "LifeCycle",
        "LiveData",
        "DataStore",
        "Room",
        "DataBinding",
        "ViewBinding"
    )

    override fun getFragments(): MutableList<Fragment> = mutableListOf(
        PagingFragment(),
        NavigationFragment(),
        ViewModelMainFragment(),
        LifeCycleMainFragment(),
        LiveDataMainFragment(),
        EmptyFragment("DataStore"),
        EmptyFragment("Room"),
        EmptyFragment("DataBinding"),
        EmptyFragment("ViewBinding"),
    )
}