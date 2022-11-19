package app.allever.android.sample.function.im.ui.adapter.provider

import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.ui.adapter.ItemType

class ImageMsgReceiveProvider : ImageMsgProvider() {
    override val itemViewType = ItemType.IMAGE_MSG_RECEIVE
    override val layoutId = R.layout.rv_image_msg_receive
}