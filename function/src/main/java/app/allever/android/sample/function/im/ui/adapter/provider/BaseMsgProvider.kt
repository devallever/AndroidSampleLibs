package app.allever.android.sample.function.im.ui.adapter.provider

import android.widget.ImageView
import android.widget.TextView
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.message.BaseMessage
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseViewHolder

abstract class BaseMsgProvider : BaseItemProvider<BaseMessage>() {
    fun setUserInfo(helper: BaseViewHolder, item: BaseMessage) {
        helper.getView<TextView>(R.id.tvNickName).text = item.user?.nickname ?: ""
        helper.getView<ImageView>(R.id.ivAvatar).load(item.user?.avatar ?: "")
    }
}