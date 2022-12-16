package app.allever.android.lib.demo.ui

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment


class TanTanFragment : TabFragment<FragmentTabBinding, TabViewModel>() {
    override fun getTabTitles() = mutableListOf("广场泡泡", "消息-又来看我卡", "卡片滑动")

    override fun getFragments(): MutableList<Fragment> =
        mutableListOf(EmptyFragment("广场泡泡"), EmptyFragment("消息-又来看我卡"), EmptyFragment("卡片滑动"))
}