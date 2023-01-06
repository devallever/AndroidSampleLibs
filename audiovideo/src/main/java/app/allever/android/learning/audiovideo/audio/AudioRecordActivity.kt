package app.allever.android.learning.audiovideo.audio

import app.allever.android.learning.audiovideo.BR
import app.allever.android.learning.audiovideo.R
import app.allever.android.learning.audiovideo.databinding.ActivityAudioRecordBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class AudioRecordActivity : BaseActivity<ActivityAudioRecordBinding, AudioRecordViewModel>() {
    private var audioRecord: BaseAudioRecordThread? = null

    override fun getContentMvvmConfig() =
        MvvmConfig(R.layout.activity_audio_record, BR.audioRecordVM)

    override fun init() {
        initTopBar("AudioRecord录制音频")

        val audioRecordCallback = object : AudioRecordCallback {
            override fun onFinish(path: String) {
                runOnUiThread {
                    binding.tvSavePath.text = "保存到：$path"
                }
            }

            override fun onError(msg: String) {

            }
        }
        binding.btnStartRecord.setOnClickListener {
            audioRecord?.stopRecord()
            audioRecord = AudioRecordThread(audioRecordCallback)
            audioRecord?.start()
        }

        binding.btnStopRecord.setOnClickListener {
            audioRecord?.stopRecord()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioRecord?.stopRecord()
    }
}


class AudioRecordViewModel : BaseViewModel() {
    override fun init() {

    }
}