package app.allever.android.learning.audiovideo

import app.allever.android.learning.audiovideo.databinding.ActivitySelectMediaBinding
import app.allever.android.learning.audiovideo.videoviewplayer.VideoViewPlayerActivity
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.function.media.MediaHelper
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.lib.widget.mediapicker.MediaPicker
import app.allever.android.lib.widget.mediapicker.MediaPickerListener
import app.allever.android.lib.widget.mediapicker.ui.PreviewActivity

class SelectMediaActivity : BaseActivity<ActivitySelectMediaBinding, SelectMediaViewModel>() {
    override fun init() {
        initTopBar("选择视频")
        binding.btnSelectMedia.setOnClickListener {
            MediaPicker.launchPickerActivity(
                MediaHelper.TYPE_VIDEO,
                mediaPickerListener = object : MediaPickerListener {
                    override fun onPicked(
                        all: MutableList<MediaBean>,
                        imageList: MutableList<MediaBean>,
                        videoList: MutableList<MediaBean>,
                        audioList: MutableList<MediaBean>
                    ) {
                        log("path = ${videoList[0]}")
                        if (videoList.isNotEmpty()) {
                            videoList[0].date
                            ActivityHelper.startActivity<VideoViewPlayerActivity> {
                                putExtra("MEDIA_BEAN", videoList[0])
                            }
                        }
                    }

                })
        }
    }

    override fun getContentMvvmConfig() =
        MvvmConfig(R.layout.activity_select_media, BR.selectMediaVM)
}

class SelectMediaViewModel : BaseViewModel() {
    override fun init() {

    }
}