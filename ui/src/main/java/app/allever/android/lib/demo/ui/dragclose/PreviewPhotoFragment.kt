package app.allever.android.lib.demo.ui.dragclose

import android.content.Context
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.core.app.SharedElementCallback
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.function.imageloader.load
import app.allever.android.lib.core.helper.DragCloseHelper
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentPreviewPhotoBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class PreviewPhotoFragment : BaseFragment<FragmentPreviewPhotoBinding, BaseViewModel>() {
    private lateinit var dragCloseHelper: DragCloseHelper
    override fun inflate() = FragmentPreviewPhotoBinding.inflate(layoutInflater)

    override fun init() {
        val bundle = requireActivity().intent.getBundleExtra("fragmentArgs")
        val url = bundle?.getString("url", "") ?: ""
        mBinding.photoView.load(R.drawable.default_avatar)


        //初始化拖拽返回
        dragCloseHelper = DragCloseHelper(requireContext())
        dragCloseHelper.setShareElementMode(true)
        dragCloseHelper.setDragCloseViews(mBinding.root, mBinding.photoView)
        dragCloseHelper.setDragCloseListener(object : DragCloseHelper.DragCloseListener {
            override fun intercept(): Boolean {
                return false
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
                    requireActivity().onBackPressed()
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



}