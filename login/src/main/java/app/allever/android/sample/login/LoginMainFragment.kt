package app.allever.android.sample.login

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding

class LoginMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf(
        "Google", "Facebook"
    )

    override fun getFragments(): MutableList<Fragment> = mutableListOf(
        GoogleLoginFragment(),
        FacebookLoginFragment()
    )
}