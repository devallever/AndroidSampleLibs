package app.allever.android.learning.audiovideo.audio

import android.media.AudioRecord
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.log

class AudioRecordThread(audioRecordCallback: AudioRecordCallback?) : BaseAudioRecordThread(audioRecordCallback) {

    /**
     * 音频录制工具
     */
    private lateinit var audioRecord: AudioRecord

    override fun initRecord() {
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat)
        log("bufferSize = $bufferSizeInBytes")
        audioRecord =
            AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes)
    }

    override fun startRecord() {
        if (fileOutputStream == null) {
            return
        }

        val data = ByteArray(bufferSizeInBytes)
        audioRecord.startRecording()
        while (true) {
            if (isStopRecord) {
                App.mainHandler.post {
                    mRecordCallback?.onFinish(path)
                }
                release()
                break
            }
            val readSize = audioRecord.read(data, 0, bufferSizeInBytes);
            if (readSize <= 0) {
                isStopRecord = true
                continue
            }
            try {
                fileOutputStream?.write(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 释放资源
     */
    private fun release() {
        audioRecord.stop()
        audioRecord.release()
        closeIo()
    }
}