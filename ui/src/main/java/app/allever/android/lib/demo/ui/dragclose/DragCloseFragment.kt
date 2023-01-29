package app.allever.android.lib.demo.ui.dragclose

import android.content.Intent
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Parcelable
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.SharedElementCallback
import androidx.recyclerview.widget.GridLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.FragmentPhotoListBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class DragCloseFragment : BaseFragment<FragmentPhotoListBinding, BaseViewModel>() {
    private val mAdapter = PhotoItemAdapter()
    private var updateIndex = 0
    override fun inflate() = FragmentPhotoListBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        mBinding.recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener{ adapter, view, position ->
//            val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                requireActivity(),
//                view.findViewById(R.id.ivImage),
//                "share_photo"
//            )
//
//            val intent = Intent(requireContext(),PreviewPhotoActivity::class.java )
//            startActivity(intent, compat.toBundle())
            ActivityHelper.startActivity<PreviewPhotoActivity> {  }
        }
        initData()

        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: List<String?>?,
                sharedElements: MutableMap<String?, View?>
            ) {
                super.onMapSharedElements(names, sharedElements)
                //sharedElements 本页面指定共享元素动画的view
                log("test exit a", "onMapSharedElements")
                //更新共享元素键值对
                val view: View? =
                    mAdapter.getViewByPosition(updateIndex, R.id.ivImage)
                if (view != null) {
                    sharedElements["share_photo"] = view
                }
            }

            override fun onCaptureSharedElementSnapshot(
                sharedElement: View,
                viewToGlobalMatrix: Matrix?,
                screenBounds: RectF?
            ): Parcelable? {
                //sharedElement 本页面指定共享元素动画的view
                log("test exit a", "onCaptureSharedElementSnapshot")
                //以下代码已经没必要设置，因为demo中的动画效果已经全部设置在了rv_item_fake_iv上
                //解决执行共享元素动画的时候，一开始显示空白的问题
                sharedElement.alpha = 1f
                return super.onCaptureSharedElementSnapshot(
                    sharedElement,
                    viewToGlobalMatrix,
                    screenBounds
                )
            }

            override fun onSharedElementsArrived(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                listener: OnSharedElementsReadyListener?
            ) {
                log("test exit a", "onSharedElementsArrived")
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener)
            }
        })
    }

    private fun initData() {
        val list = mutableListOf(
            "http://pic1.win4000.com/m00/66/19/0f4bff95ddeae09123d6b6acd2b29014.jpg",
            "http://pic1.win4000.com/m00/d3/81/b5f1b7905fb0d24c0bf3682aa955ace7.jpg",
            "http://pic1.win4000.com/m00/97/17/ed95cf41b0b69084e46221c4a51afd18.jpg",
            "http://pic1.win4000.com/m00/c9/67/b2ea791310adbd64a4619ab7aa07b01d.jpg",
            "http://pic1.win4000.com/m00/10/dc/2fe3b82ca9c7d899e0df17044f008b2e.jpg",
            "http://pic1.win4000.com/m00/1f/59/1e861cacec15dc68bc91b72adbfff237.jpg",
            "http://pic1.win4000.com/m00/cb/27/25b63f72854890c12aada5264405ace5.jpg",
            "http://pic1.win4000.com/m00/ba/0f/9aae3f6abbc452cc0230dcb6cec16a79.jpg",
            "http://pic1.win4000.com/m00/44/a9/cda1ba1307ae19449baad85b72ec17e5.jpg"
        )
        mAdapter.setList(list)
    }
}