package app.allever.android.sample.function.im

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabActivity
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.ActivityTabBinding
import app.allever.android.sample.function.im.ui.ConversationFragment
import app.allever.android.sample.function.im.ui.ConversationListFragment

class IMMainActivity : TabActivity<ActivityTabBinding, TabViewModel>() {
    override fun getPageTitle() = "IM"

    override fun getTabTitles() = mutableListOf("会话列表", "会话界面")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(ConversationListFragment(), ConversationFragment())
}