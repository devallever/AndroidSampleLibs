package app.allever.android.learning.audiovideo

import app.allever.android.learning.audiovideo.audio.AudioRecordActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper

class AudioVideoMainListFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "VideoView播放器", "TextureView播放器", "SurfaceView播放器", "AudioRecord录制音频"
    )

    override fun onItemClick(position: Int, item: String) {
        when(position) {
            0 -> {
                ActivityHelper.startActivity(SelectMediaActivity::class.java) {
                    putExtra("TYPE", 0)
                }
            }
            1 -> {
                ActivityHelper.startActivity(SelectMediaActivity::class.java) {
                    putExtra("TYPE", 1)
                }
            }
            2 -> {
                ActivityHelper.startActivity(SelectMediaActivity::class.java) {
                    putExtra("TYPE", 2)
                }
            }
            3 -> {
                ActivityHelper.startActivity(AudioRecordActivity::class.java) { }
            }
        }
    }
}