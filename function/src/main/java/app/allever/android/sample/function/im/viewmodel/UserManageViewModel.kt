package app.allever.android.sample.function.im.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.allever.android.lib.core.function.datastore.DataStore
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.function.im.function.db.IMDBController
import app.allever.android.sample.function.im.ui.adapter.UserAdapter
import app.allever.android.sample.function.im.user.UserInfo
import kotlinx.coroutines.launch

class UserManageViewModel : BaseViewModel() {

    val userAdapter = UserAdapter()
    val currentUserLiveData = MutableLiveData<UserInfo?>()

    var isRefreshData = true

    fun addUser(nickname: String, avatar: String) {
        viewModelScope.launch {
            val userInfo = UserInfo()
            userInfo.nickname = nickname
            userInfo.avatar = avatar
            IMDBController.addUser(userInfo)
            getAllUserInfo()
        }
    }

    fun updateUser(nickname: String, avatar: String) {
        viewModelScope.launch {
            currentUserLiveData.value?.apply {
                this.nickname = nickname
                this.avatar = avatar
                IMDBController.updateUser(this)
                getAllUserInfo()
            }
        }
    }

    fun getAllUserInfo() {
        viewModelScope.launch {
            userAdapter.setList(IMDBController.getAllUser())
        }
    }

    suspend fun getAllUser(): MutableList<UserInfo> {
        return IMDBController.getAllUser()
    }

    fun deleteSelectUser() {
        viewModelScope.launch {
            currentUserLiveData.value?.let { IMDBController.deleteUser(it) }
            currentUserLiveData.value = null
            getAllUserInfo()
        }
    }

    fun loginCurrentUser() {
        currentUserLiveData.value?.let {
            viewModelScope.launch {
                DataStore.putLong("login_user", it.id)
                IMViewModel.loginUserId = it.id
            }
        }
    }
}