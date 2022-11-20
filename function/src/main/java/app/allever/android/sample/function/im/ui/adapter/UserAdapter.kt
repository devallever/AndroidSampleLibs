package app.allever.android.sample.function.im.ui.adapter

import androidx.core.view.isVisible
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.RvMsgUserInfoReceiveBinding
import app.allever.android.sample.function.im.user.UserInfo
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class UserAdapter :
    BaseQuickAdapter<UserInfo, BaseDataBindingHolder<RvMsgUserInfoReceiveBinding>>(R.layout.rv_msg_user_info_receive) {
    override fun convert(
        holder: BaseDataBindingHolder<RvMsgUserInfoReceiveBinding>,
        item: UserInfo
    ) {
        val binding = holder.dataBinding ?: return
        binding.ivAvatar.load(item.avatar)
        binding.tvNickName.text =  "${item.id}.${item.nickname}"
        binding.userView.isVisible = false
    }
}

//class UserAdapter : BasePagingBindingAdapter<UserInfo, RvMsgUserInfoReceiveBinding>(
//    R.layout.rv_msg_user_info_receive,
//    PagingHelper.createPagingDiffCallback { old, new ->
//        old.id == new.id
//    }) {
//    override fun convert(
//        holder: BaseBindingViewHolder<RvMsgUserInfoReceiveBinding>,
//        position: Int,
//        item: UserInfo
//    ) {
//        holder.binding.ivAvatar.load(item.avatar)
//        holder.binding.tvNickName.text = item.nickname
//        holder.binding.userView.isVisible = false
//    }
//
//}