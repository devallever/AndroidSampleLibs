package app.allever.android.sample.function.im.viewmodel

import androidx.lifecycle.viewModelScope
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toJson
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.function.im.function.db.IMDBController
import app.allever.android.sample.function.im.ui.adapter.ContactsAdapter
import kotlinx.coroutines.launch

class ContactsListViewModel : BaseViewModel() {
    val contactsAdapter = ContactsAdapter()

    fun getContactsList(loadMore: Boolean) {
        viewModelScope.launch {
            val messageList = IMDBController.getContactsList()
            messageList.map {
                log("message = ${it.toJson()}")
            }
            if (loadMore) {
                contactsAdapter.refreshRV?.loadMoreData(messageList)
            } else {
                contactsAdapter.refreshRV?.refreshData(messageList)
            }
        }
    }
}