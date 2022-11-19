package app.allever.android.sample.function.im.ui.adapter

import app.allever.android.sample.function.im.constant.ActionType
import app.allever.android.sample.function.im.constant.MessageType
import app.allever.android.sample.function.im.message.*
import app.allever.android.sample.function.im.ui.adapter.provider.*
import com.chad.library.adapter.base.BaseProviderMultiAdapter

class MessageAdapter : BaseProviderMultiAdapter<BaseMessage>() {
    init {
        addItemProvider(TextMsgSendProvider())
        addItemProvider(TextMsgReceiveProvider())
        addItemProvider(ImageMsgSendProvider())
        addItemProvider(ImageMsgReceiveProvider())
        addItemProvider(VideoMsgSendProvider())
        addItemProvider(VideoMsgReceiveProvider())
    }

    override fun getItemType(data: List<BaseMessage>, position: Int): Int {
        return when (val item = data[position]) {
            is TextMessage -> {
                if (item.actionType == ActionType.SEND) {
                    ItemType.TEXT_MSG_SEND
                } else {
                    ItemType.TEXT_MSG_RECEIVE
                }
            }
            is ImageMessage -> {
                if (item.actionType == ActionType.SEND) {
                    ItemType.IMAGE_MSG_SEND
                } else {
                    ItemType.IMAGE_MSG_RECEIVE
                }
            }
            is AudioMessage -> {
                if (item.actionType == ActionType.SEND) {
                    ItemType.AUDIO_MSG_SEND
                } else {
                    ItemType.AUDIO_MSG_RECEIVE
                }
            }
            is VideoMessage -> {
                if (item.actionType == ActionType.SEND) {
                    ItemType.VIDEO_MSG_SEND
                } else {
                    ItemType.VIDEO_MSG_RECEIVE
                }
            }
            else -> MessageType.OTHER
        }
    }
}

class ItemType {
    companion object {
        const val TEXT_MSG_SEND = 0
        const val TEXT_MSG_RECEIVE = 1
        const val IMAGE_MSG_SEND = 2
        const val IMAGE_MSG_RECEIVE = 3
        const val AUDIO_MSG_SEND = 4
        const val AUDIO_MSG_RECEIVE = 5
        const val VIDEO_MSG_SEND = 6
        const val VIDEO_MSG_RECEIVE = 7
    }
}