package app.allever.android.sample.project.app

import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.core.util.ResUtils
import app.allever.android.sample.project.R
import app.allever.android.sample.project.databinding.RvGuideItemBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class GuideItemAdapter : BaseQuickAdapter<GuideItem, BaseViewHolder>(R.layout.rv_guide_item) {
    override fun convert(holder: BaseViewHolder, item: GuideItem) {
        val binding = RvGuideItemBinding.bind(holder.itemView)
        binding.bgContainer.setBackgroundColor(ResUtils.getColor(item.bgColorId))
        binding.tvTitle.text = item.title
        binding.tvDesc.text = item.description
        binding.ivGuide.load(item.guideImageId)
    }
}