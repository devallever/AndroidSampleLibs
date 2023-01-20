package app.allever.android.sample.project

import app.allever.android.learning.audiovideo.AudioVideoMainActivity
import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.demo.UIMainFragment
import app.allever.android.sample.function.FunctionMainFragment
import app.allever.android.sample.jetpack.JetpackMainFragment
import app.allever.android.sample.kotlin.KotlinMainFragment
import app.allever.android.sample.thirtypart.ThirtyPartMainFragment
import com.chad.library.adapter.base.BaseQuickAdapter

class MainListFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {

    override fun getAdapter(): BaseQuickAdapter<String, *> = TextAdapter()

    override fun getList() = mutableListOf("UI交互效果", "Jetpack", "Kotlin", "ThirtyPart", "功能实现", "音视频")

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                FragmentActivity.start<UIMainFragment>("UI交互")
            }
            1 -> {
                FragmentActivity.start<JetpackMainFragment>("JetPack")
            }
            2 -> {
                FragmentActivity.start<KotlinMainFragment>("Kotlin")
            }
            3 -> {
                FragmentActivity.start<ThirtyPartMainFragment>("ThirtyPart")
            }
            4 -> {
                FragmentActivity.start<FunctionMainFragment>("功能列表")
            }
            5 -> {
                ActivityHelper.startActivity<AudioVideoMainActivity> {  }
            }
            else -> {

            }
        }
    }
}