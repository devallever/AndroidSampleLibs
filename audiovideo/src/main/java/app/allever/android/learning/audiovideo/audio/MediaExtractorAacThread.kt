package app.allever.android.learning.audiovideo.audio

import android.media.*

class MediaExtractorAacThread(aacPath: String) : BaseDecodeAacThread(aacPath) {

    private var mediaExtractor: MediaExtractor? = null
    private var medeaCodec: MediaCodec? = null
    private var audioTrack: AudioTrack? = null

    /**
     * 采样率，通常会使用44100
     */
    private var sampleRateInHz = 44100

    /**
     * 声道设置，比如单声道CHANNEL_IN_MONO，双声道CHANNEL_IN_STEREO
     * 播放用OUT, 保存用IN
     */
    private var channelConfig = AudioFormat.CHANNEL_OUT_STEREO

    /**
     * 编码格式，通常可以选择ENCODING_PCM_8BIT，也可以选择ENCODING_PCM_16BIT
     */
    private var audioFormat = AudioFormat.ENCODING_PCM_16BIT

    /**
     * 缓存:该参数表示的是音频缓存的Buffer字节数，
     * 用于读取AudioRecord数据，它是通过AudioRecord.getMinBufferSize方法获取
     */
    private var bufferSizeInBytes = 1024

    /**
     * 该参数表示AudioTrack的使用类型，通常使用AudioTrack.MODE_STREAM类型
     */
    private var mode = AudioTrack.MODE_STREAM

    /**
     * 音频流的类型，在Android音频管理中，有很多音频流类型，一般使用AudioManager.STREAM_MUSIC即可
     */
    private var streamType = AudioManager.STREAM_MUSIC


    private var mime: String = ""
    private lateinit var format: MediaFormat

    private var isStopEncode = false

    private val TIMEOUT_MS = 2000L

    override fun initDecoder() {
        initExtractor()
        initAudioTrack()
        initMediaCodec()
    }

    private fun initExtractor() {
        mediaExtractor = MediaExtractor()
        mediaExtractor?.setDataSource(aacPath)
        val trackCount = mediaExtractor?.trackCount ?: 0
        for (i in 0..trackCount) {
            val format = mediaExtractor?.getTrackFormat(i)
            val mime = format?.getString(MediaFormat.KEY_MIME)
            if (mime?.startsWith("audio") == true) {
                mediaExtractor?.selectTrack(i)
                this.mime = mime
                this.format = format
                break
            }
        }
    }

    private fun initMediaCodec() {
        try {
            medeaCodec = MediaCodec.createEncoderByType(mime)
            medeaCodec?.configure(format, null, null, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initAudioTrack() {
        bufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat)
        audioTrack = AudioTrack(
            streamType,
            sampleRateInHz,
            channelConfig,
            audioFormat,
            bufferSizeInBytes,
            mode
        )
    }

    override fun decode() {
        try {
            val bufferInfo = MediaCodec.BufferInfo()
            medeaCodec?.start()
            audioTrack?.play()
            while (true) {
                if (isStopEncode) {
                    closeIo()
                    release()
                    return
                }

                val inputBufferId = medeaCodec?.dequeueInputBuffer(TIMEOUT_MS) ?: 0
                if (inputBufferId >= 0) {
                    val inputBuffer = medeaCodec?.getInputBuffer(inputBufferId)
                    var readSize = -1
                    inputBuffer?.let {
                        readSize = mediaExtractor?.readSampleData(inputBuffer, 0) ?: -1
                    }
                    if (readSize <= 0) {
                        //结束
                        medeaCodec?.queueInputBuffer(
                            inputBufferId,
                            0,
                            0,
                            0,
                            MediaCodec.BUFFER_FLAG_END_OF_STREAM
                        )
                    } else {
                        //正常
                        medeaCodec?.queueInputBuffer(
                            inputBufferId,
                            0,
                            readSize,
                            mediaExtractor?.sampleTime ?: 0,
                            0
                        )
                        mediaExtractor?.advance()
                    }
                }

                val outBufferId = medeaCodec?.dequeueOutputBuffer(bufferInfo, TIMEOUT_MS) ?: 0
                if (outBufferId >= 0) {

                }


            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stopEncode() {
        isStopEncode = true
    }

    private fun release() {

    }
}