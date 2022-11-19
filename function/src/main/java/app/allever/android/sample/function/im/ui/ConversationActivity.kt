package app.allever.android.sample.function.im.ui

import app.allever.android.lib.mvvm.base.BaseMvvmActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.function.BR
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.ActivityConversationBinding

class ConversationActivity : BaseMvvmActivity<ActivityConversationBinding, BaseViewModel>() {

    override fun init() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ConversationFragment())
            .commit()
    }

    override fun getMvvmConfig() = MvvmConfig(R.layout.activity_conversation, BR.conversationVM)
}
