package app.allever.android.sample.function.im.ui

import app.allever.android.lib.mvvm.base.BaseMvvmActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.function.BR
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.ActivityConversationBinding

class ConversationActivity : BaseMvvmActivity<ActivityConversationBinding, BaseViewModel>() {

    companion object {
        const val EXTRA_OTHER_USER_ID = "other_user_id"
    }

    override fun init() {
        val otherUserId = intent?.getLongExtra(EXTRA_OTHER_USER_ID, 0L) ?: 0L
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ConversationFragment.newInstance(otherUserId))
            .commit()
    }

    override fun getMvvmConfig() = MvvmConfig(R.layout.activity_conversation, BR.conversationVM)
}
