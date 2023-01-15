package app.allever.android.sample.function.im.ui.adapter

import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.RvExpandItemBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class ExpandAdapter :
    BaseQuickAdapter<ExpandItem, BaseViewHolder>(R.layout.rv_expand_item) {
    override fun convert(holder: BaseViewHolder, item: ExpandItem) {
        val binding = RvExpandItemBinding.bind(holder.itemView)
        binding.ivFun.setImageResource(item.icon)
        binding.tvTitle.text = item.title
    }
}

class ExpandItem(val icon: Int, val title: String, val type: Int) {
    companion object {
        const val TYPE_IMAGE = 0
        const val TYPE_VIDEO = 1
        const val TYPE_AUDIO_CALL = 2
        const val TYPE_VIDEO_CALL = 3
        const val TYPE_LOCATION = 4
    }
}