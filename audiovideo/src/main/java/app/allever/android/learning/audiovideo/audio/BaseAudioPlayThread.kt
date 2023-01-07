package app.allever.android.learning.audiovideo.audio

import android.media.AudioFormat
import android.media.MediaRecorder
import android.text.TextUtils
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.helper.ExecutorHelper
import java.io.File
import java.io.FileInputStream

abstract class BaseAudioPlayThread(val path: String): Thread() {

    /**
     * 文件输出
     */
    protected var fileInputStream: FileInputStream? = null

    /**
     * 声源，一般写麦克风MediaRecorder.AudioSource.MIC
     */
    protected var audioSource: Int = MediaRecorder.AudioSource.MIC

    /**
     * 采样率，通常会使用44100
     */
    protected var sampleRateInHz = 44100

    /**
     * 声道设置，比如单声道CHANNEL_IN_MONO，双声道CHANNEL_IN_STEREO
     */
    protected var channelConfig = AudioFormat.CHANNEL_IN_STEREO

    /**
     * 编码格式，通常可以选择ENCODING_PCM_8BIT，也可以选择ENCODING_PCM_16BIT
     */
    protected var audioFormat = AudioFormat.ENCODING_PCM_16BIT

    /**
     * 缓存:该参数表示的是音频缓存的Buffer字节数，
     * 用于读取AudioRecord数据，它是通过AudioRecord.getMinBufferSize方法获取
     */
    protected var bufferSizeInBytes = 1024

    /**
     * 是否停止录制
     */
    protected var isStopRecord = false

    protected var isRecording = false


    init {
        ExecutorHelper.cacheExecutor.execute {
            val dir =
                File("${App.context.externalCacheDir}${File.separator}audiorecord${File.separator}")
            if (!dir.exists()) {
                dir.mkdirs()
            }
        }
    }

    override fun run() {
        super.run()
        if (isRecording) {
            return
        }
        isRecording = true
        initIo()
        initRecord()
        startPlay()
    }

    override fun interrupt() {
        super.interrupt()
        isRecording = false
    }

    private fun initIo() {
        log("audioRecordPath = $path")
        if (TextUtils.isEmpty(path)) {
            return
        }
        val file = File(path)
        if (file.exists()) {
            file.delete()
        }
        fileInputStream = try {
            FileInputStream(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    protected fun closeIo() {
        if (fileInputStream != null) {
            try {
                fileInputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
    }

    abstract fun initRecord()

    abstract fun startPlay()

    fun stopPlay() {
        isStopRecord = true
    }
}