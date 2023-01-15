package app.allever.android.sample.function.im.ui.adapter

import androidx.core.view.isVisible
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.RvMsgUserInfoReceiveBinding
import app.allever.android.sample.function.im.user.UserInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class UserAdapter :
    BaseQuickAdapter<UserInfo, BaseViewHolder>(R.layout.rv_msg_user_info_receive) {
    override fun convert(holder: BaseViewHolder, item: UserInfo) {
        val binding = RvMsgUserInfoReceiveBinding.bind(holder.itemView)
        binding.ivAvatar.load(item.avatar)
        binding.tvNickName.text = "${item.id}.${item.nickname}"
        binding.userView.isVisible = false
    }
}