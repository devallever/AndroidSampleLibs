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
        val title = "Device Detector"
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Device Fonder Tools",
                app.allever.android.sample.project.R.drawable.p01
            )
        )
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Bluetooth device finder",
                app.allever.android.sample.project.R.drawable.p02
            )
        )
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Device distance",
                app.allever.android.sample.project.R.drawable.p03
            )
        )
        list.add(
            GuideItem(
                R.color.googleBleu,
                title,
                "Metal detector",
                app.allever.android.sample.project.R.drawable.p04
            )
        )
        mAdapter.setList(list)
    }
}