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
        val title = "Quick PE"
        list.add(
            GuideItem(
                R.color.white,
                title,
                "Simple Photo Editor",
                app.allever.android.sample.project.R.drawable.p01
            )
        )
        list.add(
            GuideItem(
                R.color.white,
                title,
                "Add Text Description",
                app.allever.android.sample.project.R.drawable.p02
            )
        )
        list.add(
            GuideItem(
                R.color.white,
                title,
                "Colorful Filters",
                app.allever.android.sample.project.R.drawable.p03
            )
        )
        list.add(
            GuideItem(
                R.color.white,
                title,
                "Funny Emoji",
                app.allever.android.sample.project.R.drawable.p04
            )
        )
        list.add(
            GuideItem(
                R.color.white,
                title,
                "Cute Sticker",
                app.allever.android.sample.project.R.drawable.p05
            )
        )
//        list.add(
//            GuideItem(
//                R.color.googleBleu,
//                title,
//                "Hard",
//                app.allever.android.sample.project.R.drawable.p06
//            )
//        )
//        list.add(
//            GuideItem(
//                R.color.googleBleu,
//                title,
//                "Game store",
//                app.allever.android.sample.project.R.drawable.p07
//            )
//        )
        mAdapter.setList(list)
    }
}