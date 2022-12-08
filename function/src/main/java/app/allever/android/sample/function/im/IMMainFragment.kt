package app.allever.android.sample.function.im

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.sample.function.im.ui.ContactsListFragment
import app.allever.android.sample.function.im.ui.ConversationListFragment
import app.allever.android.sample.function.im.ui.UserManageFragment

class IMMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {

    override fun getTabTitles() = mutableListOf("会话列表", "联系人", "用户管理")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(ConversationListFragment(), ContactsListFragment(), UserManageFragment())
}