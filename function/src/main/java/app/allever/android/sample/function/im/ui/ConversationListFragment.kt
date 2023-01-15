package app.allever.android.sample.function.im.ui

import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.sample.function.databinding.FragmentConversationListBinding
import app.allever.android.sample.function.im.viewmodel.ConversationListViewModel

class ConversationListFragment :
    BaseMvvmFragment<FragmentConversationListBinding, ConversationListViewModel>() {

    override fun inflate() = FragmentConversationListBinding.inflate(layoutInflater)

    override fun init() {
    }
}