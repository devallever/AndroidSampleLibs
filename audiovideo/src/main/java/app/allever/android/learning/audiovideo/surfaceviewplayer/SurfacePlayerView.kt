package app.allever.android.learning.audiovideo.surfaceviewplayer

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import app.allever.android.learning.audiovideo.BasePlayerView
import app.allever.android.learning.audiovideo.R
import app.allever.android.learning.audiovideo.StatusListener
import app.allever.android.learning.audiovideo.databinding.SurfacePlayerViewBinding
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.core.helper.ViewHelper
import app.allever.android.lib.core.util.TimeUtils
import kotlin.math.abs
import kotlin.math.ceil

class SurfacePlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BasePlayerView(context, attrs), StatusListener {
    private var binding: SurfacePlayerViewBinding
    private val playerHandler: SurfaceViewHandler by lazy {
        SurfaceViewHandler()
    }

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.surface_player_view,
            this,
            true
        )

        initListener()
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener {
            toast("finish")
            (context as? Activity)?.finish()
        }
        binding.ivPlayPause.setOnClickListener {
            if (playerHandler.isPlaying()) {
                playerHandler.pause()
            } else {
                playerHandler.play()
            }
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    playerHandler.seekTo(p1)
                }
                binding.tvProgress.text = TimeUtils.formatTime(p1.toLong(), TimeUtils.FORMAT_mm_ss)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.ivRotate.setOnClickListener {
            val tag = if (binding.ivRotate.tag == null) false else binding.ivRotate.tag as Boolean
            (context as Activity).requestedOrientation =
                if (!tag) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            binding.ivRotate.tag = !tag
        }

        binding.controlView.setOnClickListener {
            val visible = binding.controlContainer.visibility == View.VISIBLE
            ViewHelper.setVisible(binding.controlContainer, !visible)
            ViewHelper.setVisible(binding.topBarContainer, !visible)
        }

        val screenWidth = DisplayHelper.getScreenWidth()
        val screenHeight = DisplayHelper.getScreenHeight()
        val leftSide = screenWidth / 8
        val rightSide = screenWidth / 8 * 7
        val bottomSide = screenHeight / 3 * 2
        var moved = false
        var downX = 0f
        var downY = 0f
        var lastRealDownX = 0f
        binding.controlView.setOnTouchListener { view, motionEvent ->
            val screenWidth = DisplayHelper.getScreenWidth()
            val screenHeight = DisplayHelper.getScreenHeight()
            val leftSide = screenWidth / 8
            val rightSide = screenWidth / 8 * 7
            val bottomSide = screenHeight / 3 * 2
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = motionEvent.rawX
                    downY = motionEvent.rawY
                    lastRealDownX = downX
                    moved = false
                }
                MotionEvent.ACTION_UP -> {
                    if (!moved) {
//                        toast("没移动")
                        binding.controlView.performClick()
                    } else {
//                        toast("移动了")
                        playerHandler.play()
                    }
                    moved = false
                }
                MotionEvent.ACTION_MOVE -> {
                    val rawX = motionEvent.rawX
                    val rawY = motionEvent.rawY
                    val offsetY = abs(rawY - downY)
                    val offsetX = abs(rawX - downX)
                    val realOffsetX = rawX - lastRealDownX
                    val maxOffset = 50
                    if (offsetX > maxOffset || offsetY > maxOffset) {
                        moved = true
                    }
                    log("rawXY: (${motionEvent.rawX}, ${motionEvent.rawY})")
                    log("XY: (${motionEvent.x}, ${motionEvent.y})")
                    when {
                        rawX < leftSide -> {
                            log("左边滑动")
                        }
                        rawX > rightSide -> {
                            log("右边滑动")
                        }
                        rawY > bottomSide -> {
                            log("下边滑动")

                            if (moved) {
                                playerHandler.pause()
                                val currentPosition =
                                    binding.seekBar.max * realOffsetX / screenWidth.toFloat()
                                val progress = binding.seekBar.progress + currentPosition.toInt()
                                binding.seekBar.progress = progress
                                playerHandler.seekTo(progress)
                                log(" progress = $progress")
                            }
                        }
                        else -> {
                        }
                    }
                    lastRealDownX = rawX

                }
                MotionEvent.ACTION_CANCEL -> {
                    moved = false
                }
            }

            return@setOnTouchListener true
        }
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        playerHandler.stop()
    }

    fun setData(mediaBean: MediaBean) {
        mMediaBean = mediaBean
        playerHandler.initVideoView(binding.videoView, mediaBean, this)
        binding.tvTitle.text = mMediaBean.name
    }

    override fun onPrepare(duration: Int) {
        binding.seekBar.max = duration
        val text = TimeUtils.formatTime(duration.toLong(), TimeUtils.FORMAT_mm_ss)
        binding.tvDuration.text = " / $text"
    }

    override fun onVideoPlay() {
        binding.ivPlayPause.setImageResource(app.allever.android.lib.widget.R.drawable.icon_album_video_preview_pause)
    }

    override fun onVideoPause() {
        binding.ivPlayPause.setImageResource(app.allever.android.lib.widget.R.drawable.icon_album_video_preview_play)
    }

    override fun onVideoError() {
        binding.ivPlayPause.setImageResource(app.allever.android.lib.widget.R.drawable.icon_album_video_preview_play)
    }

    override fun onVideoPlaying(currentPosition: Int) {
        binding.seekBar.progress = currentPosition
        binding.tvProgress.text =
            TimeUtils.formatTime(currentPosition.toLong(), TimeUtils.FORMAT_mm_ss)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        changeVideoSize()
    }

    //改变视频的尺寸自适应。算法有点点问题，1.被拉伸，2.没全屏占满
    private fun changeVideoSize() {
        binding.videoView.post {
            var videoWidth = playerHandler.getMediaPlayer()?.videoWidth ?: 0
            var videoHeight = playerHandler.getMediaPlayer()?.videoHeight ?: 0

            log("video size = $videoWidth x $videoHeight")

            val surfaceWidth = binding.videoView.width
            val surfaceHeight = binding.videoView.height

            log("surface size = $surfaceWidth x $surfaceHeight")

            //根据视频尺寸去计算->视频可以在sufaceView中放大的最大倍数。
            var max = 0f
            max = if (resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                //竖屏模式下按视频宽度计算放大倍数值
                Math.max(
                    videoWidth / surfaceWidth,
                    videoHeight / surfaceHeight
                ).toFloat();
            } else {
                //横屏模式下按视频高度计算放大倍数值
                Math.max(
                    (videoWidth / surfaceHeight),
                    videoHeight / surfaceWidth
                ).toFloat();
            }

            //视频宽高分别/最大倍数值 计算出放大后的视频尺寸
            videoWidth = ceil(videoWidth / max).toInt();
            videoHeight = ceil(videoHeight / max).toInt();

            //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
            val params = binding.videoView.layoutParams
            params.width = videoWidth
            params.height = videoHeight
            binding.videoView.layoutParams = params
        }

    }
}