package app.allever.android.learning.audiovideo.videoviewplayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import app.allever.android.learning.audiovideo.StatusListener
import app.allever.android.learning.audiovideo.databinding.VideoPlayerViewBinding
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.core.helper.ViewHelper
import app.allever.android.lib.core.util.TimeUtils
import kotlin.math.abs

class VideoPlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), StatusListener {

    private val videoViewHandler: VideoViewHandler by lazy {
        VideoViewHandler()
    }

    private var binding: VideoPlayerViewBinding

    private var mMediaBean: MediaBean? = null

    init {
        binding = VideoPlayerViewBinding.inflate(LayoutInflater.from(App.context), this, true)

        initListener()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {
        binding.ivBack.setOnClickListener {
            toast("finish")
            (context as? Activity)?.finish()
//            finish()
        }
        binding.ivPlayPause.setOnClickListener {
            if (videoViewHandler.isPlaying()) {
                videoViewHandler.pause()
            } else {
                videoViewHandler.play()
            }
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    videoViewHandler.seekTo(p1)
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
                        videoViewHandler.play()
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
                                videoViewHandler.pause()
                                val currentPosition =
                                    binding.seekBar.max * realOffsetX / screenWidth.toFloat()
                                val progress = binding.seekBar.progress + currentPosition.toInt()
                                binding.seekBar.progress = progress
                                videoViewHandler.seekTo(progress)
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
        videoViewHandler.stop()
    }

    fun setData(mediaBean: MediaBean) {
        mMediaBean = mediaBean
        videoViewHandler.initVideoView(
            binding.videoView,
            mMediaBean!!,
            null,
            this
        )
        binding.tvTitle.text = mMediaBean?.name
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
        super.onSizeChanged(w, h, oldw, oldh)
        binding.videoView.post {
            val screenWidth = DisplayHelper.getScreenWidth()
            val screenHeight = DisplayHelper.getScreenHeight()
            val isWidthMode = screenWidth > screenHeight

            if (isWidthMode) {
                val lp = binding.videoView.layoutParams
                lp.width = ViewGroup.LayoutParams.WRAP_CONTENT
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT
                binding.videoView.layoutParams = lp
            } else {
                val lp = binding.videoView.layoutParams
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.videoView.layoutParams = lp
            }
        }
    }
}