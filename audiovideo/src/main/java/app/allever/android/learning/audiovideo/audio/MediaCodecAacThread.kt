package app.allever.android.learning.audiovideo.audio

import android.media.AudioFormat
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.log
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.ByteBuffer

class MediaCodecAacThread(pcm: String, callback: EncodePcmCallback) :
    BaseCodecPcmThread(pcm, callback) {

    private var channelConfig = AudioFormat.CHANNEL_IN_STEREO

    private val sampleRateInHz = 44100

    private val maxInputSize = 1024

    private lateinit var mediaCodec: MediaCodec

    private val mime = "audio/mp4a-latm"

    private lateinit var encodeBufferInfo: MediaCodec.BufferInfo
    private var encodeInputBuffers = mutableListOf<ByteBuffer>()
    private var encodeOutputBuffers = mutableListOf<ByteBuffer>()

    private val chunkAudio = ByteArray(0)

    override fun initEncoder() {
        var channelCount = 1
        if (channelConfig == AudioFormat.CHANNEL_IN_MONO) {
            channelCount = 1
        } else if (channelConfig == AudioFormat.CHANNEL_IN_STEREO) {
            channelCount = 2
        }
        val format = MediaFormat.createAudioFormat(
            mime, sampleRateInHz, channelCount
        )
        format.setInteger(MediaFormat.KEY_BIT_RATE, AudioFormat.ENCODING_PCM_16BIT)
        format.setInteger(
            MediaFormat.KEY_AAC_PROFILE,
            MediaCodecInfo.CodecProfileLevel.AACObjectLC
        );
        format.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, maxInputSize)


        try {
            //参数对应-> mime type、采样率、声道数
            val encodeFormat =
                MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, 44100, channelCount)
            encodeFormat.setInteger(MediaFormat.KEY_BIT_RATE, 96000) //比特率
            encodeFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 2)
            encodeFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.CHANNEL_IN_STEREO)
            encodeFormat.setInteger(
                MediaFormat.KEY_AAC_PROFILE,
                MediaCodecInfo.CodecProfileLevel.AACObjectLC
            )
            encodeFormat.setInteger(
                MediaFormat.KEY_MAX_INPUT_SIZE,
                4096
            ) //作用于inputBuffer的大小
            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC)
            mediaCodec.configure(encodeFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        } catch (e: IOException) {
            e.printStackTrace()
        }


        try {
//            mediaCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC)
//            mediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
            mediaCodec.start()
            encodeInputBuffers = mediaCodec.inputBuffers.toMutableList()
            encodeOutputBuffers = mediaCodec.outputBuffers.toMutableList()
            encodeBufferInfo = MediaCodec.BufferInfo()
        } catch (e: Exception) {
            e.printStackTrace()
//            mediaCodec = null
        }
    }

    override fun startEncode() {
        val data = ByteArray(1024)
        while (true) {
            val len = fileInputStream?.read(data) ?: 0
            if (len > 0) {
                log("正在读文件：len = $len")
                dstAudioFormatFromPCM(data)
            } else {
                log("结束读文件")
                closeIo()
                App.mainHandler.post {
                    callback.onFinish(aacPath)
                }
                break
            }
        }

    }

    /**
     * 编码PCM数据 得到AAC格式的音频文件
     */
    private fun dstAudioFormatFromPCM(pcmData: ByteArray) {
        val inputBuffer: ByteBuffer
        var outputIndex: Int
        var outputBuffer: ByteBuffer
        var outBitSize: Int
        var outPacketSize: Int
        val PCMAudio: ByteArray = pcmData
        encodeInputBuffers = mediaCodec.inputBuffers.toMutableList()
        encodeOutputBuffers = mediaCodec.outputBuffers.toMutableList()
        encodeBufferInfo = MediaCodec.BufferInfo()
        val inputIndex: Int = mediaCodec.dequeueInputBuffer(0)
        if (inputIndex != -1) {
            inputBuffer = encodeInputBuffers.get(inputIndex)
            inputBuffer.clear()
            inputBuffer.limit(PCMAudio.size)
            inputBuffer.put(PCMAudio) //PCM数据填充给inputBuffer
            mediaCodec.queueInputBuffer(inputIndex, 0, PCMAudio.size, 0, 0) //通知编码器 编码
            outputIndex = mediaCodec.dequeueOutputBuffer(encodeBufferInfo, 0)
            while (outputIndex > 0) {
                outBitSize = encodeBufferInfo.size
                outPacketSize = outBitSize + 7 //7为ADT头部的大小
                outputBuffer = encodeOutputBuffers.get(outputIndex) //拿到输出Buffer
                outputBuffer.position(encodeBufferInfo.offset)
                outputBuffer.limit(encodeBufferInfo.offset + outBitSize)
                val chunkAudio = ByteArray(outPacketSize)
                addADTSHeader(chunkAudio, outPacketSize, 2) //添加ADTS
                outputBuffer[chunkAudio, 7, outBitSize] //将编码得到的AAC数据 取出到byte[]中
                try {
                    //录制aac音频文件，保存在手机内存中
                    fileOutputStream?.write(chunkAudio, 0, chunkAudio.size)
                    fileOutputStream?.flush()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                outputBuffer.position(encodeBufferInfo.offset)
                mediaCodec.releaseOutputBuffer(outputIndex, false)
                outputIndex = mediaCodec.dequeueOutputBuffer(encodeBufferInfo, 0)
            }
        }
    }

    private fun release() {

    }
}