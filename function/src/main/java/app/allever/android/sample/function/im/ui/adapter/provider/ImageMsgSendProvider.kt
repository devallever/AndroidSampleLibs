package app.allever.android.sample.function.im.ui.adapter.provider

import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.ui.adapter.ItemType

class ImageMsgSendProvider : ImageMsgProvider() {
    override val itemViewType = ItemType.IMAGE_MSG_SEND
    override val layoutId = R.layout.rv_image_msg_send
}