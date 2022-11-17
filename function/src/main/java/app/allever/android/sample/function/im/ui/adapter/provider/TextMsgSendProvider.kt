package app.allever.android.sample.function.im.ui.adapter.provider

import android.widget.ImageView
import android.widget.TextView
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.message.BaseMessage
import app.allever.android.sample.function.im.message.TextMessage
import app.allever.android.sample.function.im.ui.adapter.ItemType
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class TextMsgSendProvider : BaseMsgProvider() {
    override val itemViewType = ItemType.TEXT_MSG_SEND
    override val layoutId = R.layout.rv_text_msg_send
    override fun convert(helper: BaseViewHolder, item: BaseMessage) {
        if (item !is TextMessage) return
        setUserInfo(helper, item)
        helper.getView<TextView>(R.id.tvContent).text = item.content
    }

}