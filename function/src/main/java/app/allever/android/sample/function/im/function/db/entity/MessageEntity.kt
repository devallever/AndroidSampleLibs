package app.allever.android.sample.function.im.function.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.allever.android.sample.function.im.constant.ActionType
import app.allever.android.sample.function.im.constant.MessageType
import app.allever.android.sample.function.im.user.UserInfo

@Entity(tableName = "message")
class MessageEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0L
//    @Embedded
    var userInfoJson: String = ""
    var actionType = ActionType.SEND
    var time: Long = 0L
    var fromUserId: Long = 0L
    var toUserId: Long = 0L
    var type = MessageType.TEXT
    var content: String = ""
}