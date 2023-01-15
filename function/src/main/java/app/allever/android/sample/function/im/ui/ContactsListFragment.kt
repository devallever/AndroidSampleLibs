package app.allever.android.sample.function.im.ui

import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.widget.recycler.RefreshRecyclerView
import app.allever.android.sample.function.databinding.FragmentContactsListBinding
import app.allever.android.sample.function.im.message.BaseMessage
import app.allever.android.sample.function.im.viewmodel.ContactsListViewModel

class ContactsListFragment :
    BaseMvvmFragment<FragmentContactsListBinding, ContactsListViewModel>() {

    override fun inflate() = FragmentContactsListBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.refreshRV.setAdapter(
            mViewModel.contactsAdapter,
            dataFetchListener = object : RefreshRecyclerView.DataFetchListener<BaseMessage> {
                override fun loadData(currentPage: Int, isLoadMore: Boolean) {
                    mViewModel.getContactsList(isLoadMore)
                }
            })
            .enableLoadMore(false)
            .enableRefresh(true)
            .execute()
        mViewModel.contactsAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mViewModel.contactsAdapter.data[position]
            ActivityHelper.startActivity<ConversationActivity> {
                putExtra(ConversationActivity.EXTRA_OTHER_USER_ID, item.id)
            }
        }
    }
}