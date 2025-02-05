package app.allever.android.sample.microsoft.speech

import android.Manifest
import androidx.lifecycle.lifecycleScope
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.util.UIKit.runOnUiThread
import app.allever.android.sample.microsoft.speech.databinding.FragmentMicrosoftSpeechBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MicrosoftSpeechBaseSampleFragment :
    BaseFragment<FragmentMicrosoftSpeechBinding, MicrosoftSpeechBaseSampleViewModel>() {

    private val mResultBuilder = StringBuilder()

    override fun inflate() = FragmentMicrosoftSpeechBinding.inflate(layoutInflater)

    override fun init() {

        PermissionHelper.request(requireActivity(), object : PermissionListener {
            override fun onAllGranted() {
                log("onAllGranted: ")
            }
        }, Manifest.permission.RECORD_AUDIO)

        mBinding.btnStart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                SpeechSdkHelper.getIns()
                    .pronunciationAssessmentFromMicrophoneStream(object : ResultCallback {
                        override fun onError(errorText: String) {
                            updateResultText(errorText)
                        }

                        override fun onResultText(text: String) {
                            updateResultText(text)
                        }
                    })
            }
        }

        mBinding.btnStop.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                SpeechSdkHelper.getIns().stopPronunciationAssessmentFromMicrophoneStream()
            }
        }
    }

    private fun updateResultText(text: String) {
        mResultBuilder.append(text).append("\n")
        runOnUiThread {
            mBinding.tvResult.text = mResultBuilder.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        SpeechSdkHelper.getIns().stopPronunciationAssessmentFromMicrophoneStream()
    }
}

