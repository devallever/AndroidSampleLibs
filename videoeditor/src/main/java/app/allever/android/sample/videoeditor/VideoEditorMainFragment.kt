package app.allever.android.sample.videoeditor

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.sample.videoeditor.gesture.GestureFragment

class VideoEditorMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("Gesture") {
            FragmentActivity.start<GestureFragment>(it.title)
        },
        TextClickItem("基础编辑") {
            FragmentActivity.start<BaseEditFragment>(it.title)
        }
    )
}