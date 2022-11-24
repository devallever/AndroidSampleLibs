package app.allever.android.lib.demo.ui

import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.RvItemTextBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

class AutoScrollAdapter(private val mLoop: Boolean = true) :
    BaseQuickAdapter<String, BaseDataBindingHolder<RvItemTextBinding>>(R.layout.rv_item_text) {
    override fun convert(holder: BaseDataBindingHolder<RvItemTextBinding>, item: String) {
        val binding = holder.dataBinding ?: return
        binding.tvText.text = item
    }

    override fun getItem(position: Int): String {
        return if (mLoop) {
            val newPosition = position % data.size
            data[newPosition]
        } else {
            data[position]
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (mLoop) {
            val realPosition = position % data.size
            super.getItemViewType(realPosition)
        } else {
            super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return if (mLoop) {
            Integer.MAX_VALUE
        } else {
            data.size
        }
    }
}