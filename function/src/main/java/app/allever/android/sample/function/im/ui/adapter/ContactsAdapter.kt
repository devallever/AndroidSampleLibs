package app.allever.android.sample.function.im.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.widget.recycler.RefreshRVAdapter
import app.allever.android.sample.function.R
import app.allever.android.sample.function.im.user.UserInfo
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ContactsAdapter :
    RefreshRVAdapter<UserInfo, BaseViewHolder>(R.layout.rv_contacts_item) {

    override fun convert(
        holder: BaseViewHolder,
        item: UserInfo
    ) {
        holder.getView<TextView>(R.id.tvNickName)?.text = "${item.id}.${item.nickname}"
        holder.getView<ImageView>(R.id.ivAvatar).load(item.avatar)
    }
}