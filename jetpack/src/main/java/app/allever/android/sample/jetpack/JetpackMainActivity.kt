package app.allever.android.sample.jetpack

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment
import app.allever.android.sample.jetpack.ui.PagingFragment

class JetpackMainActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "JetPack"

    override fun getTabTitles() =
        mutableListOf(
            "Paging",
            "DataStore",
            "Room",
            "Lifecycle",
            "LiveData",
            "ViewModel",
            "DataBinding"
        )

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(
            PagingFragment(),
            EmptyFragment("DataStore"),
            EmptyFragment("Room"),
            EmptyFragment("Lifecycle"),
            EmptyFragment("LiveData"),
            EmptyFragment("ViewModel"),
            EmptyFragment("DataBinding"),
        )
}