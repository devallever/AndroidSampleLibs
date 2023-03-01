package app.allever.android.sample.cleaner

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.toast
import app.allever.android.sample.cleaner.ui.DeviceStatusFragment

class CleanerMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("手机状态信息") {
            FragmentActivity.start<DeviceStatusFragment>(it.title)
        },
        TextClickItem("清理缓存：../Android/data/pkg/cache") {
            toast(it.title)
        },
        TextClickItem("清理下载：../Download") {
            toast(it.title)
        },
        TextClickItem("清理内存") {
            toast(it.title)
        },
        TextClickItem("清理大文件") {
            toast(it.title)
        },
        TextClickItem("清理视频") {
            toast(it.title)
        },
        TextClickItem("清理安装包") {
            toast(it.title)
        },
        TextClickItem("卸载程序") {
            toast(it.title)
        },
    )
}