package app.allever.android.learning.audiovideo.audio

import app.allever.android.lib.core.ext.log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

abstract class BaseCodecPcmThread(val pcmPath: String, val callback: EncodePcmCallback) : Thread() {

    protected var fileInputStream: FileInputStream? = null
    protected var fileOutputStream: FileOutputStream? = null

    protected var aacPath: String = ""

    protected var isStopEncoding = false

    init {
        initIo()
        initEncoder()
    }

    override fun run() {
        super.run()
        startEncode()
    }

    private fun initIo() {
        try {
            fileInputStream = FileInputStream(pcmPath)
            val pcmFile = File(pcmPath)
            aacPath = "${pcmFile.parent}${File.separator}${pcmFile.name}.aac"
            log("aacPath = $aacPath")
            fileOutputStream = FileOutputStream(aacPath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun closeIo() {
        fileInputStream?.close()
        fileOutputStream?.close()
    }

    fun stopEncoding() {
        isStopEncoding = false
    }

    abstract fun initEncoder()

    abstract fun startEncode()

    interface EncodePcmCallback {
        fun onFinish(path: String)
        fun onError(msg: String){}
    }

    /**
     * 添加AAC帧文件头
     *
     * @param packet    packet
     * @param packetLen packetLen
     */
    fun addADTSHeader(packet: ByteArray, packetLen: Int, channelCount: Int) {
        val profile = 2 // AAC
        val freqIdx = 4 // 44.1kHz
        packet[0] = 0xFF.toByte()
        packet[1] = 0xF9.toByte()
        packet[2] = ((profile - 1 shl 6) + (freqIdx shl 2) + (channelCount shr 2)).toByte()
        packet[3] = ((channelCount and 3 shl 6) + (packetLen shr 11)).toByte()
        packet[4] = (packetLen and 0x7FF shr 3).toByte()
        packet[5] = ((packetLen and 7 shl 5) + 0x1F).toByte()
        packet[6] = 0xFC.toByte()
    }
}