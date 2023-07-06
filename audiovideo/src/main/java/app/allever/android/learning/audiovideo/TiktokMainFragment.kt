package app.allever.android.learning.audiovideo

import app.allever.android.learning.audiovideo.tiktok.RvJzIjkTiktokFragment
import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding

class TiktokMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList(): MutableList<TextClickItem> = mutableListOf(
        TextClickItem("RV+系统播放器内核") {

        },
        TextClickItem("RV+ijk播放器内核") {

        },
        TextClickItem("RV+饺子播放器+ijk内核") {
            FragmentActivity.start<RvJzIjkTiktokFragment>("", showTopBar = false, darkMode = true)
        },


        )
}