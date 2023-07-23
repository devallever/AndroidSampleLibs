package app.allever.android.lib.demo.ui.expandrecycler

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.demo.databinding.ItemFriendGroupBinding
import app.allever.android.lib.demo.databinding.ItemFriendUserBinding
import pokercc.android.expandablerecyclerview.ExpandableAdapter

class ContactGroupAdapter(val data: MutableList<ContactGroup>) :
    ExpandableAdapter<ExpandableAdapter.ViewHolder>() {
    companion object {
        const val ITEM_TYPE_GROUP = 1
        const val ITEM_TYPE_USER = 2
    }

    class GroupVH(val binding: ItemFriendGroupBinding) : ViewHolder(binding.root)

    class UserVH(val binding: ItemFriendUserBinding) : ViewHolder(binding.root)

    override fun getChildCount(groupPosition: Int) = data[groupPosition].list.size

    override fun getGroupCount() = data.size

    override fun onCreateChildViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return UserVH(
            ItemFriendUserBinding.inflate(inflater, viewGroup, false)
        )
    }

    override fun onCreateGroupViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return GroupVH(
            ItemFriendGroupBinding.inflate(inflater, viewGroup, false)
        )
    }

    override fun getGroupItemViewType(groupPosition: Int) = ITEM_TYPE_GROUP

    override fun getChildItemViewType(groupPosition: Int, childPosition: Int) = ITEM_TYPE_USER

    override fun isGroup(viewType: Int) = viewType == ITEM_TYPE_GROUP

    override fun onGroupViewHolderExpandChange(
        holder: ViewHolder, groupPosition: Int, animDuration: Long, expand: Boolean
    ) {
        val arrowImage = (holder as GroupVH).binding.ivArrow
        if (expand) {
            ObjectAnimator.ofFloat(arrowImage, View.ROTATION, 0f)
                .setDuration(100)
                .start()
        } else {
            ObjectAnimator.ofFloat(arrowImage, View.ROTATION, -90f)
                .setDuration(100)
                .start()
        }
    }

    override fun onBindGroupViewHolder(
        holder: ViewHolder, groupPosition: Int, expand: Boolean, payloads: List<Any>
    ) {
        (holder as? GroupVH)?.binding?.apply {
            tvFriendGroup.text = data[groupPosition].name
        }
    }

    override fun onBindChildViewHolder(
        holder: ViewHolder, groupPosition: Int, childPosition: Int, payloads: List<Any>
    ) {
        (holder as? UserVH)?.binding?.apply {
            tvSignature.text = data[groupPosition].list[childPosition].signature
            tvNickname.text = data[groupPosition].list[childPosition].nickname
            ivAvatar.load(data[groupPosition].list[childPosition].avatar)
        }
    }

}