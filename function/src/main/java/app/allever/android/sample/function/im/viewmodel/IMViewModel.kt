package app.allever.android.sample.function.im.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.function.datastore.DataStore
import app.allever.android.lib.core.helper.SPHelper
import app.allever.android.sample.function.im.function.db.IMDBController
import app.allever.android.sample.function.im.user.UserInfo
import kotlinx.coroutines.launch

object IMViewModel : AndroidViewModel(App.app) {
    var loginUserId = SPHelper.getLong("login_user", 0L)
        set(value) {
            field = value
            updateLoginUser()
        }
    var loginUser: UserInfo? = null

    init {
        updateLoginUser()
    }

    private fun updateLoginUser() {
        viewModelScope.launch {
            loginUser = IMDBController.getUserById(loginUserId)
        }
    }

}