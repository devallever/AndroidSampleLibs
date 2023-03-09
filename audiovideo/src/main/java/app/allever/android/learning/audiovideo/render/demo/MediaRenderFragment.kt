package app.allever.android.learning.audiovideo.render.demo

import app.allever.android.learning.audiovideo.kernel.AbsPlayerFactory
import app.allever.android.learning.audiovideo.kernel.AndroidPlayerFactory
import app.allever.android.learning.audiovideo.render.*
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding

class MediaRenderFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    private var renderView: IRenderView? = null
    private val player = AbsPlayerFactory.create<AndroidPlayerFactory>().createPlayer()
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("使用SurfaceView渲染") {
            renderView =
                AbsRenderFactory.create<SurfaceRenderFactory>().createRender(requireContext())
        },
        TextClickItem("使用TextureView渲染") {
            renderView =
                AbsRenderFactory.create<TextureRenderFactory>().createRender(requireContext())
        },
        TextClickItem("使用IJKVideoView渲染") {
            renderView =
                AbsRenderFactory.create<IJKRenderFactory>().createRender(requireContext())
        },
        TextClickItem("绑定播放器") {
            renderView?.attachPlayer(player)
        }
    )
}