package app.allever.android.lib.common.adapter

import app.allever.android.lib.common.R
import app.allever.android.lib.common.databinding.RvTextItemBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class TextAdapter :
    BaseQuickAdapter<String, BaseDataBindingHolder<RvTextItemBinding>>(R.layout.rv_text_item) {
    override fun convert(holder: BaseDataBindingHolder<RvTextItemBinding>, item: String) {
        holder.dataBinding?.tvText?.text = item
    }
}