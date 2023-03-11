package app.allever.android.learning.audiovideo.kernel.demo

import app.allever.android.learning.audiovideo.databinding.FragmentRenderKernelBinding
import app.allever.android.learning.audiovideo.kernel.*
import app.allever.android.learning.audiovideo.render.ConstantKeys
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.media.MediaBean
import app.allever.android.lib.core.function.media.MediaHelper
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.core.util.FileUtils
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.widget.mediapicker.MediaPicker
import app.allever.android.lib.widget.mediapicker.MediaPickerListener

class RenderKernelFragment : BaseFragment<FragmentRenderKernelBinding, BaseViewModel>() {

    private var player: AbsPlayer? = AbsPlayerFactory.create<AndroidPlayerFactory>().createPlayer()

    override fun inflate() = FragmentRenderKernelBinding.inflate(layoutInflater)

    override fun init() {
        val list = mutableListOf(
            TextClickItem("Android内核") {
                player = AbsPlayerFactory.create<AndroidPlayerFactory>().createPlayer()
                setPlayerListener()
                mBinding.surfaceRenderView.attachToPlayer(player!!)
                toast(it.title)
            },
            TextClickItem("IJKPlayer内核") {
                player = AbsPlayerFactory.create<IJKPlayerFactory>().createPlayer()
                setPlayerListener()
                mBinding.surfaceRenderView.attachToPlayer(player!!)
                toast(it.title)
            },
            TextClickItem("1.初始化播放器") {
                player?.initPlayer()
            },
            TextClickItem("2.选择视频") {
                selectVideo()
            },
            TextClickItem("3.设置渲染") {
//                player?.setDisplay(mBinding.surfaceView.holder)
            },
            TextClickItem("4.准备") {
                player?.prepareAsync()
            },
            TextClickItem("播放") {
                player?.start()
            },
            TextClickItem("暂停") {
                player?.pause()
            },
            TextClickItem("停止(需要重新设置数据)") {
                player?.stop()
            }
        )
        FragmentHelper.addToContainer(
            childFragmentManager,
            MediaKernelFragment(list),
            mBinding.fragmentContainer.id
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player?.stop()
        player?.release()
        player?.playerStatusListener = null
    }

    private fun setPlayerListener() {
        player?.playerStatusListener = object : PlayerStatusListener {
            override fun onError(type: Int, error: String?) {

            }

            override fun onCompletion() {
            }

            override fun onInfo(what: Int, extra: Int) {
            }

            override fun onPrepared() {
            }

            override fun onVideoSizeChanged(width: Int, height: Int) {
                log("width = $width x height = $height")
//                handleSurfaceSize(width, height)
                mBinding.surfaceRenderView.setScaleType(ConstantKeys.PlayerScreenScaleType.SCREEN_SCALE_16_9)
                mBinding.surfaceRenderView.setVideoSize(width, height)
            }

        }
    }

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
                    val mSelectMediaPath = videoList[0].path
                    val mOriginFileName = FileUtils.getFileName(mSelectMediaPath)
                    log("path = $mSelectMediaPath")
                    toast("path = $mSelectMediaPath")
                    videoList[0].uri?.let {
                        player?.setDataSource(it)
                    }
                }
            })
    }

    private fun handleSurfaceSize(width: Int, height: Int) {
        val w: Float = width.toFloat()
        val h: Float = height.toFloat()
        val sw: Float = mBinding.renderViewContainer.width.toFloat()
        val sh: Float = mBinding.renderViewContainer.height.toFloat()
        var displayW = 0
        var displayH = 0

        if (w > h) {
            //横向视频
            if (w > sw) {
                //超宽视频
            } else {
                //
                displayH = sh.toInt()
                displayW = (w * sh / h).toInt()
            }
        } else {
            //纵向视频
            displayH = sh.toInt()
            displayW = (w * sh / h).toInt()
        }

        log("surface size = $displayW x $displayH")

        //无法直接设置视频尺寸，将计算出的视频尺寸设置到surfaceView 让视频自动填充。
        val params = mBinding.surfaceView.layoutParams
        params.width = displayW
        params.height = displayH
        mBinding.surfaceView.layoutParams = params
    }
}