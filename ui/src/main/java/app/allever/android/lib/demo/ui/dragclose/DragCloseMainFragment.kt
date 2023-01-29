package app.allever.android.lib.demo.ui.dragclose

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class DragCloseMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf(
        "DragCloseHelper", "DragPhotoView"
    )

    override fun getFragments(): MutableList<Fragment> = mutableListOf(
        DragCloseFragment(),
        EmptyFragment("DragPhotoView方式"),
    )
}