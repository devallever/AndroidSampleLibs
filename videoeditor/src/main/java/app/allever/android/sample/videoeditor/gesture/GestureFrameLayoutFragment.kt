package app.allever.android.sample.videoeditor.gesture

import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.videoeditor.databinding.FragmentGestureFrameLayoutBinding

class GestureFrameLayoutFragment :
    BaseFragment<FragmentGestureFrameLayoutBinding, BaseViewModel>() {
    override fun inflate() = FragmentGestureFrameLayoutBinding.inflate(layoutInflater)

    override fun init() {

    }
}