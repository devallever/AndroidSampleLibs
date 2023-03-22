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
                R.color.googleRed,
                title,
                "The hot gif memes",
                app.allever.android.sample.project.R.drawable.default_image
            )
        )
        list.add(
            GuideItem(
                R.color.googleGreen,
                title,
                "Search all gif memes",
                app.allever.android.sample.project.R.drawable.default_image
            )
        )
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Collect favorite gif memes",
                app.allever.android.sample.project.R.drawable.default_image
            )
        )
        list.add(
            GuideItem(
                R.color.googleYellow,
                title,
                "Backup and restore",
                app.allever.android.sample.project.R.drawable.default_image
            )
        )
        mAdapter.setList(list)
    }
}