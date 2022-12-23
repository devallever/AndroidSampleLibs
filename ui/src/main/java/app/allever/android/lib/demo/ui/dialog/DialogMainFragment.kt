package app.allever.android.lib.demo.ui.dialog

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding

class DialogMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() =
        mutableListOf("Dialog", "DialogFragment", "BottomSheetDialog", "ActivityDialog", "Window")

    override fun getFragments(): MutableList<Fragment> = mutableListOf(
        CommonDialogListFragment(),
        DialogFragmentListFragment(),
        BottomSlideDialogListFragment(),
        ActivityDialogListFragment(),
        WindowFragment()
    )
}