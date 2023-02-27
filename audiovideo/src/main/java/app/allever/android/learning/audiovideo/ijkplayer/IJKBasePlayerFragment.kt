package app.allever.android.learning.audiovideo.ijkplayer

import android.net.Uri
import app.allever.android.learning.audiovideo.databinding.FragmentIjkBaseBinding
import app.allever.android.learning.audiovideo.ijkplayer.widget.media.AndroidMediaController
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel

class IJKBasePlayerFragment : BaseFragment<FragmentIjkBaseBinding, BaseViewModel>() {
    override fun inflate() = FragmentIjkBaseBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.ijkVideoView.setVideoURI(Uri.parse("http://vfx.mtime.cn/Video/2019/02/04/mp4/190204084208765161.mp4"))
        mBinding.ijkVideoView.start()
        mBinding.ijkVideoView.setMediaController(AndroidMediaController(requireContext()))
    }
}