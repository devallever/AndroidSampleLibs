package app.allever.android.lib.demo.ui.dragclose

import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.RvImageSquareBinding
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class PhotoItemAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_image_square) {

    override fun convert(holder: BaseViewHolder, item: String) {
        val binding = RvImageSquareBinding.bind(holder.itemView)
//        Glide.with(context).load(item).into(binding.ivImage)
    }
}