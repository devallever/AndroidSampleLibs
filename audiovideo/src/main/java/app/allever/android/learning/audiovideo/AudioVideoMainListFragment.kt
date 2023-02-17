package app.allever.android.learning.audiovideo

import app.allever.android.learning.audiovideo.audio.AudioRecordFragment
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
        TextClickItem("AudioRecord录制音频") {
            FragmentActivity.start<AudioRecordFragment>(it.title)
        }
    )
}