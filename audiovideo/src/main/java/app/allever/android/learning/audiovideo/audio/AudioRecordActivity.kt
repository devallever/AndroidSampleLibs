package app.allever.android.learning.audiovideo.audio

import android.text.TextUtils
import app.allever.android.learning.audiovideo.BR
import app.allever.android.learning.audiovideo.R
import app.allever.android.learning.audiovideo.databinding.ActivityAudioRecordBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.function.media.SongMediaPlayer
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class AudioRecordActivity : BaseActivity<ActivityAudioRecordBinding, AudioRecordViewModel>() {
    private var audioRecord: BaseAudioRecordThread? = null
    private var audioTrack: BaseAudioPlayThread? = null
    private var audioEncoderThread: BaseCodecPcmThread? = null
    private var mPath: String = ""
    private var mAacPath = ""
    private val songMediaPlayer = SongMediaPlayer()

    override fun getContentMvvmConfig() =
        MvvmConfig(R.layout.activity_audio_record, BR.audioRecordVM)

    override fun init() {
        initTopBar("AudioRecord录制音频")

        val audioRecordCallback = object : AudioRecordCallback {
            override fun onFinish(path: String) {
                runOnUiThread {
                    binding.tvSavePath.text = "保存到：$path"
                    mPath = path
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

        binding.btnStartPlay.setOnClickListener {
            audioTrack?.stopPlay()
            audioTrack = AudioTrackPlayThread(mPath)
            audioTrack?.start()
        }

        binding.btnStopPlay.setOnClickListener {
            audioTrack?.stopPlay()
        }

        val encodeCallback = object : BaseCodecPcmThread.EncodePcmCallback {
            override fun onFinish(path: String) {
                binding.tvAacPath.text = "AAC保存路径：$path"
                mAacPath = path
            }
        }

        binding.btnEncodePcm.setOnClickListener {
            audioEncoderThread?.stopEncoding()
            audioEncoderThread = MediaCodecAacThread(mPath, encodeCallback)
            audioEncoderThread?.start()
        }

        binding.btnPlayAac.setOnClickListener {
            if (TextUtils.isEmpty(mAacPath)) {
                return@setOnClickListener
            }

            if (songMediaPlayer.isPlaying()) {
                songMediaPlayer.pause()
                return@setOnClickListener
            }

            songMediaPlayer.load(mAacPath)
            songMediaPlayer.play()
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