package app.allever.android.sample.function.im.ui

import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.function.BR
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.FragmentConversationListBinding
import app.allever.android.sample.function.im.viewmodel.ConversationListViewModel

class ConversationListFragment :
    BaseMvvmFragment<FragmentConversationListBinding, ConversationListViewModel>() {
    override fun getMvvmConfig() =
        MvvmConfig(R.layout.fragment_conversation_list, BR.conversationListVM)

    override fun init() {
        mBinding.root.setOnClickListener {
            ActivityHelper.startActivity<ConversationActivity> { }
        }
    }
}