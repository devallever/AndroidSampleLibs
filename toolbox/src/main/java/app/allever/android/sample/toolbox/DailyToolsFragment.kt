package app.allever.android.sample.toolbox

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.sample.toolbox.dailytools.CompassActivity
import app.allever.android.sample.toolbox.dailytools.RulerActivity

class DailyToolsFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList(): MutableList<TextClickItem> = mutableListOf(
        TextClickItem("刻度尺") {
            ActivityHelper.startActivity<RulerActivity> {  }
        },
        TextClickItem("指南针") {
            ActivityHelper.startActivity<CompassActivity> {  }
        },
        TextClickItem("水平仪") {
            toast(it.title)
        },
        TextClickItem("量角器") {
            toast(it.title)
        },
        TextClickItem("Google翻译") {
            toast(it.title)
        },
        TextClickItem("画板") {
            toast(it.title)
        },
        TextClickItem("LED字幕") {
            toast(it.title)
        },
        TextClickItem("时间屏幕") {
            toast(it.title)
        },
        TextClickItem("历史上的今天") {
            toast(it.title)
        })
}