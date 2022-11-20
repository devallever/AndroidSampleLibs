package app.allever.android.sample.function.im.message

import app.allever.android.lib.core.helper.GsonHelper
import app.allever.android.sample.function.im.constant.ActionType
import app.allever.android.sample.function.im.constant.MessageType
import app.allever.android.sample.function.im.function.db.entity.MessageEntity
import app.allever.android.sample.function.im.user.UserInfo

open class BaseMessage {
    var id = 0L
    var user: UserInfo? = null
    var actionType = ActionType.SEND
    var time: Long = 0L
    var fromUserId: Long = 0L
    var toUserId: Long = 0L

    fun createMessageEntity(actionType: Int, messageType: Int): MessageEntity {
        /**
        var user: UserInfo? = null
        var actionType = ActionType.SEND
        var time: Long = 0L
        var fromUserId: Long = 0L
        var toUserId: Long = 0L
        var type = MessageType.TEXT
        var content: String = ""
         */
        val messageEntity = MessageEntity()
//        messageEntity.user = user
        messageEntity.userInfoJson = GsonHelper.toJson(user)
        messageEntity.actionType = actionType
        messageEntity.time = time
        messageEntity.fromUserId = fromUserId
        messageEntity.toUserId = toUserId
        messageEntity.type = messageType
        messageEntity.content = GsonHelper.toJson(this)
        return messageEntity
    }

    fun <T: BaseMessage> generateMessage(messageEntity: MessageEntity): T {
        val message = when(messageEntity.type) {
            MessageType.TEXT -> TextMessage()
            MessageType.IMAGE -> ImageMessage()
            MessageType.AUDIO -> AudioMessage()
            MessageType.VIDEO -> VideoMessage()
            else -> TextMessage()
        }

        return message as T
    }
}