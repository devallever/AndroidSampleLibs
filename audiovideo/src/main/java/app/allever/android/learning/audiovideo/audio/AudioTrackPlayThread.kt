package app.allever.android.learning.audiovideo.audio

import android.media.AudioManager
import android.media.AudioTrack
import app.allever.android.lib.core.ext.log

class AudioTrackPlayThread(path: String) : BaseAudioPlayThread(path) {

    private lateinit var audioTrack: AudioTrack

    /**
     * 该参数表示AudioTrack的使用类型，通常使用AudioTrack.MODE_STREAM类型
     */
    private var mod = AudioTrack.MODE_STREAM

    /**
     * 音频流的类型，在Android音频管理中，有很多音频流类型，一般使用AudioManager.STREAM_MUSIC即可
     */
    private var streamType = AudioManager.STREAM_MUSIC

    override fun initRecord() {
        bufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        audioTrack = AudioTrack(
            streamType,
            sampleRateInHz,
            channelConfig,
            audioFormat,
            bufferSizeInBytes,
            mod
        )

    }

    override fun startPlay() {
        if (fileInputStream == null) {
            return
        }

        val data = ByteArray(bufferSizeInBytes)
        audioTrack.play()
        while (true) {
            if (isStopRecord) {
                closeIo()
                release()
                break
            }
            var readSize = -1
            try {
                log("正在播放")
                readSize = fileInputStream?.read(data) ?: 0
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (readSize <= 0) {
                isStopRecord = true
                continue
            }

            audioTrack.write(data, 0, readSize)

        }
    }

    private fun release() {
        audioTrack.stop()
        audioTrack.release()
    }
}