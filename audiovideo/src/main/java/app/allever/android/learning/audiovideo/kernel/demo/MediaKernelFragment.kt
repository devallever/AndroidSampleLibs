package app.allever.android.learning.audiovideo.kernel.demo

import app.allever.android.learning.audiovideo.kernel.AbsPlayer
import app.allever.android.learning.audiovideo.kernel.AbsPlayerFactory
import app.allever.android.learning.audiovideo.kernel.AndroidPlayerFactory
import app.allever.android.learning.audiovideo.kernel.IJKPlayerFactory
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.toast

class MediaKernelFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    private var player: AbsPlayer? = null
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("Android内核") {
            player = AbsPlayerFactory.createFactory<AndroidPlayerFactory>().createPlayer()
            toast(it.title)
        },
        TextClickItem("IJKPlayer内核") {
            player = AbsPlayerFactory.createFactory<IJKPlayerFactory>().createPlayer()
            toast(it.title)
        },
        TextClickItem("播放") {
            player?.play()
        }
    )

}