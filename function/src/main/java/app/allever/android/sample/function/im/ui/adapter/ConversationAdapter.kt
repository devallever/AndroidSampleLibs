package app.allever.android.sample.function.im.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.widget.recycler.RefreshRVAdapter
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.RvConversationItemBinding
import app.allever.android.sample.function.im.message.BaseMessage
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class ConversationAdapter :
    RefreshRVAdapter<BaseMessage, BaseDataBindingHolder<RvConversationItemBinding>>(R.layout.rv_conversation_item) {
    override fun convert(
        holder: BaseDataBindingHolder<RvConversationItemBinding>,
        item: BaseMessage
    ) {
        holder.getView<TextView>(R.id.tvNickName).text =  "${item.user?.id}.${item.user?.nickname}"
        holder.getView<ImageView>(R.id.ivAvatar).load(item.user?.avatar ?: "")
    }
}