package app.allever.android.learning.audiovideo.textureviewplayer

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import app.allever.android.learning.audiovideo.R
import app.allever.android.learning.audiovideo.databinding.TexturePlayerViewBinding
import app.allever.android.learning.audiovideo.videoviewplayer.VideoViewHandler
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.core.helper.ViewHelper
import app.allever.android.lib.core.util.TimeUtils
import kotlin.math.abs

class TexturePlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), TextureViewHandler.StatusListener {
    private var binding: TexturePlayerViewBinding
    private lateinit var mMediaBean: MediaBean
    private val mTextureViewHandler: TextureViewHandler by lazy {
        TextureViewHandler()
    }

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.texture_player_view,
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
            if (mTextureViewHandler.isPlaying()) {
                mTextureViewHandler.pause()
            } else {
                mTextureViewHandler.play()
            }
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2) {
                    mTextureViewHandler.seekTo(p1)
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
                        mTextureViewHandler.play()
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
                                mTextureViewHandler.pause()
                                val currentPosition =
                                    binding.seekBar.max * realOffsetX / screenWidth.toFloat()
                                val progress = binding.seekBar.progress + currentPosition.toInt()
                                binding.seekBar.progress = progress
                                mTextureViewHandler.seekTo(progress)
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
        mTextureViewHandler.stop()
    }

    fun setData(mediaBean: MediaBean) {
        mMediaBean = mediaBean
        mTextureViewHandler.initVideoView(binding.videoView, mediaBean, this)
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
        stretching(w.toFloat(), h.toFloat())
    }


    private var mVideoHeight = 0f
    private var mVideoWidth = 0f

    //设置避免视频播放时拉伸，复制可直接使用
    private fun stretching(mtextureViewWidth: Float, mtextureViewHeight: Float) {
        log("视频拉伸: $mtextureViewWidth x $mtextureViewHeight")
        binding.videoView.post {
            //mtextureViewWidth为textureView宽，mtextureViewHeight为textureView高
            //mtextureViewWidth宽高，为什么需要用传入的，因为全屏显示时宽高不会及时更新
            val matrix = Matrix();
            //videoView为new MediaPlayer()
            val mVideoWidth = mTextureViewHandler.getMediaPlayer().videoWidth.toFloat()
            val mVideoHeight = mTextureViewHandler.getMediaPlayer().videoHeight.toFloat()

            //得到缩放比，从而获得最佳缩放比
            val sx = mtextureViewWidth / mVideoWidth;
            val sy = mtextureViewHeight / mVideoHeight;
            //先将视频变回原来的大小
            val sx1 = mVideoWidth / mtextureViewWidth;
            val sy1 = mVideoHeight / mtextureViewHeight;
            matrix.preScale(sx1, sy1);
            log("mat", matrix.toString());
            //然后判断最佳比例，满足一边能够填满
            if (sx >= sy) {
                matrix.preScale(sy, sy);
                //然后判断出左右偏移，实现居中，进入到这个判断，证明y轴是填满了的
                val leftX = (mtextureViewWidth - mVideoWidth * sy) / 2;
                matrix.postTranslate(leftX, 0f);
            } else {
                matrix.preScale(sx, sx);
                val leftY = (mtextureViewHeight - mVideoHeight * sx) / 2;
                matrix.postTranslate(0f, leftY);
            }

            binding.videoView.setTransform(matrix);//将矩阵添加到textureView
            binding.videoView.postInvalidate();//重绘视图
        }

    }
}