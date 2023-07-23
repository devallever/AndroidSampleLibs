package app.allever.android.lib.demo.ui.dragrecycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import app.allever.android.lib.demo.R
import app.hxz.anime.business.message.bean.FriendGroupItem
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter

class FriendGroupItemDragAdapter :
    DragDropSwipeAdapter<FriendGroupItem, FriendGroupItemDragAdapter.Holder>() {

    class Holder(itemView: View) : ViewHolder(itemView) {
        val ivDrag = itemView.findViewById<ImageView>(R.id.ivDrag)
        val ivEdit = itemView.findViewById<ImageView>(R.id.ivEdit)
        val ivDelete = itemView.findViewById<ImageView>(R.id.ivDelete)
        val tvName = itemView.findViewById<TextView>(R.id.tvGroupName)
    }

    override fun getViewHolder(itemView: View) = Holder(itemView)

    override fun onBindViewHolder(item: FriendGroupItem, viewHolder: Holder, position: Int) {
        viewHolder.tvName.text = item.name
    }

    override fun getViewToTouchToStartDraggingItem(
        item: FriendGroupItem, viewHolder: Holder, position: Int
    ): View? {
        return viewHolder.ivDrag
    }
}