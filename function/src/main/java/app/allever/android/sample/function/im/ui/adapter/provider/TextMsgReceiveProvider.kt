package app.allever.android.sample.function.im.ui.adapter.provider

import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.ui.adapter.ItemType

class TextMsgReceiveProvider : TextMsgProvider() {
    override val itemViewType = ItemType.TEXT_MSG_RECEIVE
    override val layoutId = R.layout.rv_text_msg_receive
}