package app.allever.android.lib.demo.ui

import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.RvItemTextBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class AutoScrollAdapter(private val mLoop: Boolean = true) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item_text) {
    override fun convert(holder: BaseViewHolder, item: String) {
        val binding = RvItemTextBinding.bind(holder.itemView)
        binding.tvText.text = item
        log("AutoScrollRecyclerView: position = ${getItemPosition(item)}")
        log("itemH = ${binding.root.height}")
        //binding.root.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.example_anims)

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

fun RecyclerView.Adapter<*>.getRecyclerViewItem(recyclerView: RecyclerView?, position: Int): View? {
    if (recyclerView == null || recyclerView.layoutManager == null || recyclerView.adapter == null) {
        return null
    }
    if (position > recyclerView.adapter!!.itemCount) {
        return null
    }
    val viewHolder: RecyclerView.ViewHolder = recyclerView.adapter!!.createViewHolder(
        recyclerView,
        recyclerView.adapter!!.getItemViewType(position)
    )
    recyclerView.adapter!!.onBindViewHolder(viewHolder, position)
//    viewHolder.itemView.measure(
//        View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY),
//        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//    )
    val p = viewHolder.itemView.layoutParams
    p.height = WRAP_CONTENT
    viewHolder.itemView.layoutParams = p
    return viewHolder.itemView
}