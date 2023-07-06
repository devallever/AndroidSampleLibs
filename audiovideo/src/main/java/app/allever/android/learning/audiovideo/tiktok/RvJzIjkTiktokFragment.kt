package app.allever.android.learning.audiovideo.tiktok

import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.learning.audiovideo.R
import app.allever.android.learning.audiovideo.databinding.FragmentTiktokVideoListBinding
import app.allever.android.learning.audiovideo.jz.JzvdStdTikTok
import app.allever.android.learning.audiovideo.tiktok.adapter.RvJzIjkAdapter
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import cn.jzvd.Jzvd
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RvJzIjkTiktokFragment :
    BaseMvvmFragment<FragmentTiktokVideoListBinding, RvJzIjkTiktokViewModel>() {

    private var mCurrentPosition = -1

    override fun inflate() = FragmentTiktokVideoListBinding.inflate(layoutInflater)

    override fun init() {
        val layoutManager = ViewPagerLayoutManager(requireContext(), OrientationHelper.VERTICAL)
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter = mViewModel.adapter
//        val pagerSnapHelper = PagerSnapHelper()
//        pagerSnapHelper.attachToRecyclerView(mBinding.recyclerView)
        mViewModel.fetchTestData()


        layoutManager.setOnViewPagerListener(object : OnViewPagerListener {
            override fun onInitComplete() {
                //自动播放第一条
                autoPlayVideo(0)
            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                if (mCurrentPosition == position) {
                    Jzvd.releaseAllVideos()
                }
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                if (mCurrentPosition == position) {
                    return
                }
                autoPlayVideo(position)
                mCurrentPosition = position
            }
        })

        mBinding.recyclerView.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {}
            override fun onChildViewDetachedFromWindow(view: View) {
                val jzvd = view.findViewById<Jzvd>(R.id.videoView)
                if (jzvd != null && Jzvd.CURRENT_JZVD != null && jzvd.jzDataSource != null &&
                    jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)
                ) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos()
                    }
                }
            }
        })
    }

    private fun autoPlayVideo(postion: Int) {
        if (mBinding.recyclerView == null || mBinding.recyclerView.getChildAt(0) == null) {
            return
        }
        val player: JzvdStdTikTok = mBinding.recyclerView.getChildAt(0).findViewById(R.id.videoView)
        if (player != null) {
            player.startVideoAfterPreloading()
        }
    }

//    override fun onBackPressed() {
//        if (Jzvd.backPress()) {
//            return
//        }
//        super.onBackPressed()
//    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }


}

class RvJzIjkTiktokViewModel : BaseViewModel() {
    fun fetchTestData() {
        viewModelScope.launch {
            delay(1000)
            val list = mutableListOf<VideoBean>()
            for (i in 0..10) {
                list.add(VideoBean(VideoSource.testUrlList.random(), "视频标题: $i"))
            }
            adapter.setList(list)
        }
    }

    val adapter = RvJzIjkAdapter()
}