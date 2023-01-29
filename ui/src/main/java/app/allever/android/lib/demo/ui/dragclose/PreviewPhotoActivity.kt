package app.allever.android.lib.demo.ui.dragclose

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.core.app.SharedElementCallback
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.core.helper.DragCloseHelper
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentPreviewPhotoBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class PreviewPhotoActivity: BaseActivity<FragmentPreviewPhotoBinding, BaseViewModel>() {


    private lateinit var dragCloseHelper: DragCloseHelper

    override fun inflateChildBinding() = FragmentPreviewPhotoBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        //如果在拖拽返回关闭的时候，导航栏上又出现拖拽的view的情况，就用以下代码。就和微信的表现形式一样
        //隐藏状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //透明导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        super.onCreate(savedInstanceState)
    }

    override fun showTopBar(): Boolean {
        return false
    }

    override fun init() {
        val url = intent?.getStringExtra("url") ?: ""
        binding.photoView.load(R.drawable.default_avatar)


        //初始化拖拽返回
        dragCloseHelper = DragCloseHelper(this)
        dragCloseHelper.setShareElementMode(true)
        dragCloseHelper.setDragCloseViews(binding.rootView, binding.photoView)
        dragCloseHelper.setDragCloseListener(object : DragCloseHelper.DragCloseListener {
            override fun intercept(): Boolean {
                //默认false 不拦截 如果图片是放大状态，或者处于滑动返回状态，需要拦截
                val scale = binding.photoView.scale
                return  scale < 0.99 || scale > 1.01
            }

            override fun dragStart() {
            }

            override fun dragging(percent: Float) {
            }

            override fun dragCancel() {
            }

            override fun dragClose(isShareElementMode: Boolean) {
                //拖拽关闭，如果是共享元素的页面，需要执行activity的onBackPressed方法，注意如果使用finish方法，则返回的时候没有共享元素的返回动画
                if (isShareElementMode) {
                    onBackPressed()
                }
            }
        })

        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onCreateSnapshotView(
                context: Context?,
                snapshot: Parcelable?
            ): View? {
                //新的iv执行动画的真正iv
                log("test enter b", "onCreateSnapshotView")
                return super.onCreateSnapshotView(context, snapshot)
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return if (dragCloseHelper.handleEvent(event)) {
            true
        } else {
            super.dispatchTouchEvent(event)
        }
    }

    override fun enableEnterAnim(): Boolean {
        return false
    }

    override fun enableExitAnim(): Boolean {
        return false
    }

}