package app.allever.android.sample.function.im.function.db

import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.helper.GsonHelper
import app.allever.android.sample.function.im.function.db.entity.MessageEntity
import app.allever.android.sample.function.im.user.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object IMDBController {
    private val userDao by lazy {
        IMDB.getIns().userDao()
    }

    private val messageDao by lazy {
        IMDB.getIns().messageDao()
    }

    suspend fun getUserById(id: Long) = withContext(Dispatchers.IO) {
        val result = userDao.getUserById(id)
        if (result.isEmpty()) {
            null
        } else {
            result[0]
        }
    }

    suspend fun getAllUser(): MutableList<UserInfo> = withContext(Dispatchers.IO) {
        userDao.getAllUser() as MutableList<UserInfo>
    }

    suspend fun addUser(userInfo: UserInfo) = withContext(Dispatchers.IO) {
        val result = userDao.addUser(userInfo)
        result > 0
    }

    suspend fun updateUser(userInfo: UserInfo) = withContext(Dispatchers.IO) {
        userDao.updateUser(userInfo)
    }

    suspend fun deleteUser(userInfo: UserInfo) = withContext(Dispatchers.IO) {
        userDao.deleteUser(userInfo)
    }

    suspend fun insertMessage(message: MessageEntity) = withContext(Dispatchers.IO) {
        messageDao.insertMessage(message)
    }

    suspend fun printAllMessage() = withContext(Dispatchers.IO) {
        val result = messageDao.getAllMessageList()
        result.map {
            log("消息：${GsonHelper.toJson(it)}")
        }
    }

    suspend fun getMessageList(fromUserId: Long, toUserId: Long) = withContext(Dispatchers.IO) {
        val result = messageDao.getMessage(fromUserId, toUserId)
        result.map {
            log("消息：${GsonHelper.toJson(it)}")
        }
    }
}