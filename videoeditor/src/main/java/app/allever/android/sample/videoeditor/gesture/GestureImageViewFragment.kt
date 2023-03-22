package app.allever.android.sample.videoeditor.gesture

import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.videoeditor.databinding.FragmentGestureImageViewBinding
import com.alexvasilkov.gestures.Settings

class GestureImageViewFragment : BaseFragment<FragmentGestureImageViewBinding, BaseViewModel>() {
    override fun inflate() = FragmentGestureImageViewBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.imageView.controller.settings
            .setRotationEnabled(true)
//            .setBoundsType(Settings.Bounds.OUTSIDE)
            .setMinZoom(0.1f)
    }
}