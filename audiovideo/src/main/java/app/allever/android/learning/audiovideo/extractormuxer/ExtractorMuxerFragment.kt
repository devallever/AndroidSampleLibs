package app.allever.android.learning.audiovideo.extractormuxer

import android.annotation.SuppressLint
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import android.os.Build
import android.util.Log
import androidx.lifecycle.lifecycleScope
import app.allever.android.learning.audiovideo.databinding.FragmentExtractorMuxerBinding
import app.allever.android.learning.audiovideo.textureviewplayer.TextureViewPlayerActivity
import app.allever.android.learning.audiovideo.util.isAudio
import app.allever.android.learning.audiovideo.util.isVideo
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.logE
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.function.media.MediaHelper
import app.allever.android.lib.core.function.media.SongMediaPlayer
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.core.util.FileUtils
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.widget.mediapicker.MediaPicker
import app.allever.android.lib.widget.mediapicker.MediaPickerListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import kotlin.math.abs

class ExtractorMuxerFragment : BaseFragment<FragmentExtractorMuxerBinding, BaseViewModel>() {

    private var mSelectMediaPath = ""
    private var mOriginFileName = ""
    private var mExtraAudioPath = ""
    private var mExtraVideoPath = ""
    private var mMuxerAudioVideoPath = ""
    private val mDir = App.context.externalCacheDir?.absolutePath ?: ""

    private val mAudioPlayer by lazy {
        SongMediaPlayer()
    }

    override fun inflate() = FragmentExtractorMuxerBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.btnSelectMedia.setOnClickListener {
            selectVideo()
        }
        mBinding.btnExtraAudio.setOnClickListener {
            if (mSelectMediaPath.isEmpty()) {
                return@setOnClickListener
            }
            lifecycleScope.launch {
                mExtraAudioPath = extraAudio()
                mBinding.tvExtraAudioPath.text = "提取音频路径：${mExtraAudioPath}"
            }
        }
        mBinding.btnPlayExtraAudio.setOnClickListener {
            if (mExtraAudioPath.isEmpty()) {
                return@setOnClickListener
            }
            mAudioPlayer.load(mExtraAudioPath)
            mAudioPlayer.play()
        }

        mBinding.btnStopPlayExtraAudio.setOnClickListener {
            mAudioPlayer.pause()
        }

        mBinding.btnExtraVideo.setOnClickListener {
            if (mSelectMediaPath.isEmpty()) {
                return@setOnClickListener
            }
            lifecycleScope.launch {
                mExtraVideoPath = extractorVideo()
                mBinding.tvExtraVideoPath.text = "提取视频路径：${mExtraVideoPath}"
            }
        }

        mBinding.btnPlayExtraVideo.setOnClickListener {
            if (mSelectMediaPath.isEmpty()) {
                return@setOnClickListener
            }
            ActivityHelper.startActivity<TextureViewPlayerActivity> {
                val mediaBean = MediaBean()
                mediaBean.name = FileUtils.getFileName(mExtraVideoPath)
                mediaBean.path = mExtraVideoPath
                mBinding.videoViewExtra.setData(mediaBean)
                putExtra("MEDIA_BEAN", mediaBean)
            }
        }

        mBinding.btnMuxerAudioVideo.setOnClickListener {
            if (mExtraAudioPath.isEmpty() || mExtraVideoPath.isEmpty()) {
                return@setOnClickListener
            }

            lifecycleScope.launch {
                mMuxerAudioVideoPath = muxerVideoAudio()
                mBinding.tvMuxerVideoPath.text = "合成视频路径：${mMuxerAudioVideoPath}"
            }
        }
    }

    /**
     * 选则视频
     */
    private fun selectVideo() {
        MediaPicker.launchPickerActivity(
            MediaHelper.TYPE_VIDEO,
            mediaPickerListener = object : MediaPickerListener {
                override fun onPicked(
                    all: MutableList<MediaBean>,
                    imageList: MutableList<MediaBean>,
                    videoList: MutableList<MediaBean>,
                    audioList: MutableList<MediaBean>
                ) {
                    mSelectMediaPath = videoList[0].path
                    mOriginFileName = FileUtils.getFileName(mSelectMediaPath)
                    log("path = $mSelectMediaPath")
                    if (videoList.isNotEmpty()) {
                        lifecycleScope.launch {
                            mBinding.tvSelectMediaPath.text = getSelectMediaInfo()
                        }
                    }
                }
            })
    }

    private suspend fun getSelectMediaInfo() = withContext(Dispatchers.IO) {
        val stringBuilder = StringBuilder()
        try {
            stringBuilder.append("选中视频路径：$mSelectMediaPath\n")
            val mediaExtractor = MediaExtractor()
            mediaExtractor.setDataSource(mSelectMediaPath)
            //轨道数
            val trackCount = mediaExtractor.trackCount
            var videoFormat: MediaFormat? = null
            var audioFormat: MediaFormat? = null
            for (i in 0 until trackCount) {
                val trackFormat = mediaExtractor.getTrackFormat(i)
                if (trackFormat.isVideo()) {
                    videoFormat = trackFormat
                } else if (trackFormat.isAudio()) {
                    audioFormat = trackFormat
                }
            }

            //时长:
            val duration = videoFormat?.getLong(MediaFormat.KEY_DURATION) ?: 0L
            stringBuilder.append("时长：$duration\n")
            //分辨率：
            val width: Int = videoFormat?.getInteger(MediaFormat.KEY_WIDTH) ?: 0
            val height: Int = videoFormat?.getInteger(MediaFormat.KEY_HEIGHT) ?: 0
            stringBuilder.append("分辨率：${width}x${height}\n")
            //帧率
            val frameRate = videoFormat?.getInteger(MediaFormat.KEY_FRAME_RATE) ?: 0
            stringBuilder.append("帧率：$frameRate\n")
            //比特率(码率) - 拿不到
//            val bitRate = videoFormat?.getInteger(MediaFormat.KEY_BIT_RATE) ?: 0
//            stringBuilder.append("比特率(码率)：$bitRate\n")
            //格式 - 拿不到
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                val formatType = videoFormat?.getString(MediaFormat.KEY_CODECS_STRING) ?: ""
//                stringBuilder.append("格式：$formatType\n")
//            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        return@withContext stringBuilder.toString()
    }

    @SuppressLint("WrongConstant")
    private suspend fun extraAudio() = withContext(Dispatchers.IO) {
        var path = ""
        //分离器
        val mediaExtractor = MediaExtractor()
        //合成器
        var mediaMuxer: MediaMuxer? = null
        //音轨索引
        var audioIndex = -1
        try {
            //设置数据源
            mediaExtractor.setDataSource(mSelectMediaPath)
            //轨道数
            val trackCount = mediaExtractor.trackCount
            var trackFormat: MediaFormat? = null
            for (i in 0 until trackCount) {
                //获取轨道
                trackFormat = mediaExtractor.getTrackFormat(i)
                //判断音频轨道
                if (trackFormat.getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true) {
                    audioIndex = i
                }
            }
            //选中需要操作的轨道
            mediaExtractor.selectTrack(audioIndex)

            //创建合成器
            path = "${mDir}${File.separator}${mOriginFileName}_audio.mp3"
            mediaMuxer = MediaMuxer(
                path,
                MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4
            )

            val writeAudioIndex = mediaMuxer.addTrack(trackFormat!!)
            //开始合成
            mediaMuxer.start()

            val byteBuffer = ByteBuffer.allocate(500 * 1024)
            val bufferInfo = MediaCodec.BufferInfo()

            // 获取帧之间的间隔时间
            var stampTime = 0L
            mediaExtractor.readSampleData(byteBuffer, 0);
            if (mediaExtractor.sampleFlags == MediaExtractor.SAMPLE_FLAG_SYNC) {
                mediaExtractor.advance();
            }

            mediaExtractor.readSampleData(byteBuffer, 0)
            val secondTime = mediaExtractor.sampleTime
            mediaExtractor.advance();

            mediaExtractor.readSampleData(byteBuffer, 0)
            val thirdTime = mediaExtractor.sampleTime
            stampTime = abs(thirdTime - secondTime)
            log("时间间隔：${stampTime}")

            //为什么要重新选中轨道
            mediaExtractor.unselectTrack(audioIndex)
            mediaExtractor.selectTrack(audioIndex)

            while (true) {
                //分离器读取数据
                val readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0)
                if (readSampleSize < 0) {
                    //没有数据了，退出循环
                    break
                }
                //下一帧
                mediaExtractor.advance()

                bufferInfo.size = readSampleSize
                bufferInfo.flags = mediaExtractor.sampleFlags
                bufferInfo.offset = 0
                bufferInfo.presentationTimeUs += stampTime


                //合成器写入数据
                mediaMuxer.writeSampleData(writeAudioIndex, byteBuffer, bufferInfo);
            }

            toast("分离音频完成")
            log("分离音频完成++++++++++++++++++++++++++++++++++++++");

        } catch (e: Exception) {
            e.printStackTrace()
        }

        mediaMuxer?.stop()
        mediaExtractor.release()
        mediaMuxer?.release()
        return@withContext path
    }

    @Deprecated("有问题")
    @SuppressLint("WrongConstant")
    private suspend fun extraVideo() = withContext(Dispatchers.IO) {
        var path = ""
        //分离器
        val mediaExtractor = MediaExtractor()
        //合成器
        var mediaMuxer: MediaMuxer? = null
        //音轨索引
        var targetTrackIndex = -1
        try {
            //设置数据源
            mediaExtractor.setDataSource(mSelectMediaPath)
            //轨道数
            val trackCount = mediaExtractor.trackCount
            for (i in 0 until trackCount) {
                //获取轨道
                val trackFormat = mediaExtractor.getTrackFormat(i)
                //判断视频轨道
                if (trackFormat.getString(MediaFormat.KEY_MIME)?.startsWith("video/") == true) {
                    targetTrackIndex = i
                    log("视频轨道: $targetTrackIndex")
                }
            }
            //选中需要操作的轨道
            mediaExtractor.selectTrack(targetTrackIndex)

            //创建合成器
            path = "${mDir}${File.separator}${mOriginFileName}_video.mp4"
            mediaMuxer = MediaMuxer(
                path,
                MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4
            )

            val trackFormat = mediaExtractor.getTrackFormat(targetTrackIndex)
            val trackIndex = mediaMuxer.addTrack(trackFormat)
            //开始合成
            mediaMuxer.start()

            val byteBuffer = ByteBuffer.allocate(500 * 1024)
            val bufferInfo = MediaCodec.BufferInfo()

            // 获取帧之间的间隔时间
            var stampTime = 0L

            // 将样本数据存储到字节缓存区
            mediaExtractor.readSampleData(byteBuffer, 0);
            // skip first I frame
            if (mediaExtractor.sampleFlags == MediaExtractor.SAMPLE_FLAG_SYNC) {
                // 读取下一帧数据
                mediaExtractor.advance()
            }

            mediaExtractor.readSampleData(byteBuffer, 0)
            val secondTime = mediaExtractor.sampleTime;

            mediaExtractor.advance()
            mediaExtractor.readSampleData(byteBuffer, 0)
            val thirdTime = mediaExtractor.sampleTime;

            stampTime = abs(thirdTime - secondTime)
            log("时间间隔：${stampTime}")

            //为什么要重新选中轨道, 因为上面更改了
            mediaExtractor.unselectTrack(targetTrackIndex)
            mediaExtractor.selectTrack(targetTrackIndex)

            while (true) {
                //分离器读取数据
                val readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0)
                if (readSampleSize < 0) {
                    //没有数据了，退出循环
                    break
                }
                //下一帧
                mediaExtractor.advance()

                bufferInfo.size = readSampleSize
                bufferInfo.flags = mediaExtractor.sampleFlags
                bufferInfo.offset = 0
                bufferInfo.presentationTimeUs += stampTime


                //合成器写入数据
                mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo);
            }

            toast("分离视频完成")
            log("分离视频完成++++++++++++++++++++++++++++++++++++++");

        } catch (e: Exception) {
            e.printStackTrace()
        }

        mediaMuxer?.stop()
        mediaExtractor.release()
        mediaMuxer?.release()
        return@withContext path
    }

    /**
     * 分离视频
     */
    @SuppressLint("WrongConstant")
    private suspend fun extractorVideo() = withContext(Dispatchers.IO) {
        var path = ""
        // 创建MediaExtractor实例
        val mediaExtractor = MediaExtractor()

        // 初始化MediaMuxer
        var mediaMuxer: MediaMuxer? = null

        // 轨道索引
        var videoIndex = -1
        try {
            // 设置数据源
            mediaExtractor.setDataSource(mSelectMediaPath)
            // 数据源的轨道数
            val trackCount = mediaExtractor.trackCount
            for (i in 0 until trackCount) {
                // 视频轨道格式信息
                val trackFormat = mediaExtractor.getTrackFormat(i)
                val mimeType = trackFormat.getString(MediaFormat.KEY_MIME)
                if (mimeType!!.startsWith("video/")) {
                    // 该轨道是视频轨道
                    videoIndex = i
                }
            }

            // 切换到想要的轨道
            mediaExtractor.selectTrack(videoIndex)

            // 视频轨道格式信息
            val trackFormat = mediaExtractor.getTrackFormat(videoIndex)

            // 创建MediaMuxer实例，通过new MediaMuxer(String path, int format)指定视频文件输出路径和文件格式
            path = "${mDir}${File.separator}${mOriginFileName}_video.mp4"
            mediaMuxer = MediaMuxer(
                path,
                MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4
            )

            // 添加媒体通道
            val trackIndex = mediaMuxer.addTrack(trackFormat)
            val byteBuffer = ByteBuffer.allocate(1024 * 500)
            val bufferInfo = MediaCodec.BufferInfo()

            // 添加完所有track后调用start方法，开始音视频合成
            mediaMuxer.start()

            // 获取帧之间的间隔时间
            var videoSampleTime: Long


            // 将样本数据存储到字节缓存区
            mediaExtractor.readSampleData(byteBuffer, 0)
            // skip first I frame
            if (mediaExtractor.sampleFlags == MediaExtractor.SAMPLE_FLAG_SYNC) // 读取下一帧数据
                mediaExtractor.advance()
            mediaExtractor.readSampleData(byteBuffer, 0)
            val firstVideoPTS = mediaExtractor.sampleTime
            mediaExtractor.advance()
            mediaExtractor.readSampleData(byteBuffer, 0)
            val SecondVideoPTS = mediaExtractor.sampleTime
            videoSampleTime = Math.abs(SecondVideoPTS - firstVideoPTS)
            log("videoSampleTime is $videoSampleTime")

            mediaExtractor.unselectTrack(videoIndex)
            mediaExtractor.selectTrack(videoIndex)
            while (true) {
                // 将样本数据存储到字节缓存区
                val readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0)

                // 将样本数据存储到字节缓存区
                if (readSampleSize < 0) {
                    break
                }

                // 读取下一帧数据
                mediaExtractor.advance()
                bufferInfo.size = readSampleSize
                bufferInfo.offset = 0
                bufferInfo.flags = mediaExtractor.sampleFlags
                bufferInfo.presentationTimeUs += videoSampleTime

                // 调用MediaMuxer.writeSampleData()向mp4文件中写入数据了
                mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo)
            }
            mediaMuxer.stop()
            mediaExtractor.release()
            mediaMuxer.release()
            toast("分离视频完成")
            Log.i("info", "分离视频finish++++++++++++++++++++++++++++++++++++++")
        } catch (e: IOException) {
            e.printStackTrace()
            toast("分离视频失败")
            Log.i("info", "分离视频失败++++++++++++++++++++++++++++++++++++++$e")
        }

        return@withContext path
    }


    /**
     * 合成音视频
     */
    @SuppressLint("WrongConstant")
    private suspend fun muxerVideoAudio() = withContext(Dispatchers.IO) {
        var path = ""
        try {
            // 以下过程是找到output_video.mp4中视频轨道
            val videoExtractor = MediaExtractor()
            videoExtractor.setDataSource(mExtraVideoPath)
            var videoTrackIndex = -1
            val videoTrackCount = videoExtractor.trackCount
            for (i in 0 until videoTrackCount) {
                var videoFormat = videoExtractor.getTrackFormat(i)
                val mimeType = videoFormat.getString(MediaFormat.KEY_MIME)
                if (mimeType!!.startsWith("video/")) {
                    videoTrackIndex = i
                    break
                }
            }

            // 以下过程是找到output_audio.mp3中音频轨道
            val audioExtractor = MediaExtractor()
            audioExtractor.setDataSource(mExtraAudioPath)
            var audioTrackIndex = -1
            val audioTrackCount = audioExtractor.trackCount
            for (i in 0 until audioTrackCount) {
                val audioFormat = audioExtractor.getTrackFormat(i)
                val mimeType = audioFormat.getString(MediaFormat.KEY_MIME)
                if (mimeType!!.startsWith("audio/")) {
                    audioTrackIndex = i
                    break
                }
            }
            videoExtractor.selectTrack(videoTrackIndex)
            audioExtractor.selectTrack(audioTrackIndex)
            val videoBufferInfo = MediaCodec.BufferInfo()
            val audioBufferInfo = MediaCodec.BufferInfo()


            // 通过new MediaMuxer(String path, int format)指定视频文件输出路径和文件格式
            path = "${mDir}${File.separator}${mOriginFileName}_merge.mp4"
            val mediaMuxer = MediaMuxer(
                path,
                MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4
            )
            // MediaMuxer添加媒体通道(视频)
            val videoFormat = videoExtractor.getTrackFormat(videoTrackIndex)
            val writeVideoTrackIndex = mediaMuxer.addTrack(videoFormat)
            // MediaMuxer添加媒体通道(音频)
            val audioFormat = audioExtractor.getTrackFormat(audioTrackIndex)
            val writeAudioTrackIndex = mediaMuxer.addTrack(audioFormat)
            // 开始音视频合成
            mediaMuxer.start()
            var byteBuffer = ByteBuffer.allocate(500 * 1024)
            var sampleTime: Long = 0

            videoExtractor.readSampleData(byteBuffer, 0)
            if (videoExtractor.sampleFlags == MediaExtractor.SAMPLE_FLAG_SYNC) {
                videoExtractor.advance()
            }
            videoExtractor.readSampleData(byteBuffer, 0)
            var secondTime = videoExtractor.sampleTime
            videoExtractor.advance()
            var thirdTime = videoExtractor.sampleTime
            sampleTime = Math.abs(thirdTime - secondTime)

            videoExtractor.unselectTrack(videoTrackIndex)
            videoExtractor.selectTrack(videoTrackIndex)
            while (true) {
                val readVideoSampleSize = videoExtractor.readSampleData(byteBuffer, 0)
                if (readVideoSampleSize < 0) {
                    break
                }
                videoBufferInfo.size = readVideoSampleSize
                videoBufferInfo.presentationTimeUs += sampleTime
                videoBufferInfo.offset = 0
                videoBufferInfo.flags = videoExtractor.sampleFlags
                mediaMuxer.writeSampleData(writeVideoTrackIndex, byteBuffer, videoBufferInfo)
                videoExtractor.advance()
            }

            byteBuffer = ByteBuffer.allocate(500 * 1024)


            audioExtractor.readSampleData(byteBuffer, 0);
            if (audioExtractor.sampleFlags == MediaExtractor.SAMPLE_FLAG_SYNC) {
                audioExtractor.advance();
            }

            audioExtractor.readSampleData(byteBuffer, 0)
            secondTime = audioExtractor.sampleTime
            audioExtractor.advance();

            audioExtractor.readSampleData(byteBuffer, 0)
            thirdTime = audioExtractor.sampleTime
            sampleTime = abs(thirdTime - secondTime)
            log("时间间隔：${sampleTime}")

            while (true) {
                val readAudioSampleSize = audioExtractor.readSampleData(byteBuffer, 0)
                if (readAudioSampleSize < 0) {
                    break
                }
                audioBufferInfo.size = readAudioSampleSize
                audioBufferInfo.presentationTimeUs += sampleTime
                audioBufferInfo.offset = 0
                audioBufferInfo.flags = audioExtractor.sampleFlags
                mediaMuxer.writeSampleData(writeAudioTrackIndex, byteBuffer, audioBufferInfo)
                audioExtractor.advance()
            }
            mediaMuxer.stop()
            mediaMuxer.release()
            videoExtractor.release()
            audioExtractor.release()
            toast("合成音视频完成")
            Log.i("info", "合成音视频完成++++++++++++++++++++++++++++++++++++++")
        } catch (e: Exception) {
            e.printStackTrace()
            toast("合成音视频失败")
            Log.i("info", "合成音视频失败++++++++++++++++++++++++++++++++++++++$e")
        }

        return@withContext path
    }

}