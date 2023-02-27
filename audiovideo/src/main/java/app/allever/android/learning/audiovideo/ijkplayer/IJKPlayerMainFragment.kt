package app.allever.android.learning.audiovideo.ijkplayer

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding

class IJKPlayerMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("ijkPlayer基础播放器") {
            FragmentActivity.start<IJKBasePlayerFragment>(it.title)
        }
    )
}