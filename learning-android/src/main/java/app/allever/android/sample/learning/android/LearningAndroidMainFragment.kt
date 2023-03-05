package app.allever.android.sample.learning.android

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.sample.learning.android.customview.CustomViewMainFragment
import app.allever.android.sample.learning.android.file.FileMainFragment

class LearningAndroidMainFragment :
    ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("自定义View") {
            FragmentActivity.start<CustomViewMainFragment>(it.title)
        },
        TextClickItem("数据和文件") {
            FragmentActivity.start<FileMainFragment>(it.title)
        }
    )
}