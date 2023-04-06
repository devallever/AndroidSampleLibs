package app.allever.android.sample.project.app

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.common.R
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.project.databinding.FragmentGuideBinding

class GuideFragment : BaseFragment<FragmentGuideBinding, BaseViewModel>() {

    private val mAdapter = GuideItemAdapter()

    override fun inflate() = FragmentGuideBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerView.adapter = mAdapter
        PagerSnapHelper().attachToRecyclerView(mBinding.recyclerView)
        initData()
    }

    private fun initData() {
        val list = mutableListOf<GuideItem>()
        val title = "GIF Memes"
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "The hot gif memes",
                app.allever.android.sample.project.R.drawable.p01
            )
        )
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Search all gif memes",
                app.allever.android.sample.project.R.drawable.p02
            )
        )
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Collect favorite gif memes",
                app.allever.android.sample.project.R.drawable.p03
            )
        )
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Share the gif memes",
                app.allever.android.sample.project.R.drawable.p06
            )
        )

        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Save gif memes local",
                app.allever.android.sample.project.R.drawable.p07
            )
        )

        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Video to Gif Memes",
                app.allever.android.sample.project.R.drawable.p05
            )
        )

        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Manage your Gif Memes",
                app.allever.android.sample.project.R.drawable.p04
            )
        )
        mAdapter.setList(list)
    }
}