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
        val title = "BP Recorder"
        list.add(
            GuideItem(
                R.color.googleRed,
                title,
                "Record your Blood Pressure",
                app.allever.android.sample.project.R.drawable.p04
            )
        )
        list.add(
            GuideItem(
                R.color.googleRed,
                title,
                "Record BP",
                app.allever.android.sample.project.R.drawable.p01
            )
        )
        list.add(
            GuideItem(
                R.color.googleRed,
                title,
                "Report Weekly",
                app.allever.android.sample.project.R.drawable.p02
            )
        )
        list.add(
            GuideItem(
                R.color.googleRed,
                title,
                "Healthy tips",
                app.allever.android.sample.project.R.drawable.p03
            )
        )
//        list.add(
//            GuideItem(
//                R.color.googleBleu,
//                title,
//                "challenge",
//                app.allever.android.sample.project.R.drawable.p05
//            )
//        )
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