package app.allever.android.learning.audiovideo.audio

import android.text.TextUtils
import app.allever.android.learning.audiovideo.databinding.ActivityAudioRecordBinding
import app.allever.android.learning.audiovideo.databinding.FragmentAudioRecordBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.function.media.SongMediaPlayer
import app.allever.android.lib.core.util.UIKit.runOnUiThread
import app.allever.android.lib.mvvm.base.BaseViewModel

class AudioRecordFragment : BaseFragment<FragmentAudioRecordBinding, AudioRecordViewModel>() {
    private var audioRecord: BaseAudioRecordThread? = null
    private var audioTrack: BaseAudioPlayThread? = null
    private var audioEncoderThread: BaseCodecPcmThread? = null
    private var mPath: String = ""
    private var mAacPath = ""
    private val songMediaPlayer = SongMediaPlayer()

    override fun inflate() = FragmentAudioRecordBinding.inflate(layoutInflater)

    override fun init() {

        val audioRecordCallback = object : AudioRecordCallback {
            override fun onFinish(path: String) {
                runOnUiThread {
                    mBinding.tvSavePath.text = "保存到：$path"
                    mPath = path
                }
            }

            override fun onError(msg: String) {

            }
        }
        mBinding.btnStartRecord.setOnClickListener {
            audioRecord?.stopRecord()
            audioRecord = AudioRecordThread(audioRecordCallback)
            audioRecord?.start()
        }

        mBinding.btnStopRecord.setOnClickListener {
            audioRecord?.stopRecord()
        }

        mBinding.btnStartPlay.setOnClickListener {
            audioTrack?.stopPlay()
            audioTrack = AudioTrackPlayThread(mPath)
            audioTrack?.start()
        }

        mBinding.btnStopPlay.setOnClickListener {
            audioTrack?.stopPlay()
        }

        val encodeCallback = object : BaseCodecPcmThread.EncodePcmCallback {
            override fun onFinish(path: String) {
                mBinding.tvAacPath.text = "AAC保存路径：$path"
                mAacPath = path
            }
        }

        mBinding.btnEncodePcm.setOnClickListener {
            audioEncoderThread?.stopEncoding()
            audioEncoderThread = MediaCodecAacThread(mPath, encodeCallback)
            audioEncoderThread?.start()
        }

        mBinding.btnPlayAac.setOnClickListener {
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