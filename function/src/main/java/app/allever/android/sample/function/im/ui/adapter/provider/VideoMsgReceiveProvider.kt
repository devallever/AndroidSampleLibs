package app.allever.android.sample.function.im.ui.adapter.provider

import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.ui.adapter.ItemType

class VideoMsgReceiveProvider : VideoMsgProvider() {
    override val itemViewType = ItemType.VIDEO_MSG_RECEIVE
    override val layoutId = R.layout.rv_video_msg_receive
}