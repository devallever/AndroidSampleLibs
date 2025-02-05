package app.allever.android.sample.project

import android.media.SoundPool
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import com.chad.library.adapter.base.BaseQuickAdapter

class SoundPoolListFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter(): BaseQuickAdapter<TextClickItem, *> = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("播放mp3(raw)") {

        }, TextClickItem("播放aac(raw)") {

        })

    private val mp3SoundPool = SoundPool.Builder().build()
    override fun init() {
        super.init()
        initSoundPool()
    }

    private fun initSoundPool() {
        val mp3Id = mp3SoundPool.load(requireContext(), R.raw.click, 1)

    }

}