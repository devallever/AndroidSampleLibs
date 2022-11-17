package app.allever.android.sample.function.im.message

import app.allever.android.sample.function.im.constant.ActionType
import app.allever.android.sample.function.im.user.UserInfo

open class BaseMessage {
    var user: UserInfo? = null
    var actionType = ActionType.SEND
}