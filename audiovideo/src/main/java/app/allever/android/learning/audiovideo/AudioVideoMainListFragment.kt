package app.allever.android.learning.audiovideo

import app.allever.android.learning.audiovideo.audio.AudioRecordFragment
import app.allever.android.learning.audiovideo.extractormuxer.ExtractorMuxerFragment
import app.allever.android.learning.audiovideo.ijkplayer.IJKPlayerMainFragment
import app.allever.android.learning.audiovideo.kernel.demo.RenderKernelFragment
import app.allever.android.learning.audiovideo.render.demo.MediaRenderFragment
import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper

class AudioVideoMainListFragment :
    ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("音视频基础知识"),
        TextClickItem("VideoView播放器") {
            ActivityHelper.startActivity(SelectMediaActivity::class.java) {
                putExtra("TYPE", 0)
            }
        },
        TextClickItem("TextureView播放器") {
            ActivityHelper.startActivity(SelectMediaActivity::class.java) {
                putExtra("TYPE", 1)
            }
        },
        TextClickItem("SurfaceView播放器") {
            ActivityHelper.startActivity(SelectMediaActivity::class.java) {
                putExtra("TYPE", 2)
            }
        },
        TextClickItem("ijkPlayer播放器") {
            FragmentActivity.start<IJKPlayerMainFragment>(it.title)
        },
        TextClickItem("切换播放器内核") {
            FragmentActivity.start<RenderKernelFragment>(it.title)
        },
        TextClickItem("切换播放器渲染") {
            FragmentActivity.start<MediaRenderFragment>(it.title)
        },
        TextClickItem("AudioRecord录制音频") {
            FragmentActivity.start<AudioRecordFragment>(it.title)
        },
        TextClickItem("MediaExtractor/MediaMuxer提取分离音视频并重新合成") {
            FragmentActivity.start<ExtractorMuxerFragment>("提取分离音视频并重新合成")
        },
        TextClickItem("抖音视频列表") {
            FragmentActivity.start<TiktokMainFragment>(it.title)
        },
    )
}