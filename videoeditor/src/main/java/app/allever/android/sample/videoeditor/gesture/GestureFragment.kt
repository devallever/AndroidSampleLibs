package app.allever.android.sample.videoeditor.gesture

import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.widget.fragment.EmptyFragment

class GestureFragment : TabFragment<FragmentTabBinding, BaseViewModel>() {
    override fun getTabTitles() = mutableListOf(
        "GestureImageView",
        "GestureFrameLayout",
        "CustomGestureFrameLayout"
    )

    override fun getFragments() = mutableListOf(
        GestureImageViewFragment(),
        GestureFrameLayoutFragment(),
        EmptyFragment("CustomGestureFrameLayout")
    )

}