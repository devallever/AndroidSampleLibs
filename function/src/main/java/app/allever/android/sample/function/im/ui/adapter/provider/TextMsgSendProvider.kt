package app.allever.android.sample.function.im.ui.adapter.provider

import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.ui.adapter.ItemType

class TextMsgSendProvider : TextMsgProvider() {
    override val itemViewType = ItemType.TEXT_MSG_SEND
    override val layoutId = R.layout.rv_text_msg_send
}