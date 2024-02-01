package app.allever.android.sample.function.apps

import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.RvAppItemBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *@Description
 *@author: zq
 *@date: 2024/2/1
 */
class AppItemAdapter : BaseQuickAdapter<AppItem, BaseViewHolder>(R.layout.rv_app_item) {
    override fun convert(holder: BaseViewHolder, item: AppItem) {
        val binding = RvAppItemBinding.bind(holder.itemView)
        binding.apply {
            ivIcon.setImageDrawable(item.iconDrawable)
//            item.iconDrawable?.let {
//                ivIcon.loadRound(it)
//            }
            tvTitle.text = item.name
            tvPkg.text = item.pkg
        }
    }
}