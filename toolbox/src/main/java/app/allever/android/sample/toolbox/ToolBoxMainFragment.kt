package app.allever.android.sample.toolbox

import androidx.fragment.app.Fragment
import app.allever.android.lib.common.TabFragment
import app.allever.android.lib.common.TabViewModel
import app.allever.android.lib.common.databinding.FragmentTabBinding
import app.allever.android.lib.widget.fragment.EmptyFragment

class ToolBoxMainFragment : TabFragment<FragmentTabBinding, TabViewModel>() {

    private val fragmentList = mutableListOf<Fragment>()

    override fun getTabTitles() =
        mutableListOf("日常工具", "系统工具", "图片工具", "查询工具", "提取工具", "计算工具", "文字工具", "其他工具")

    override fun getFragments(): MutableList<Fragment> = mutableListOf(
        DailyToolsFragment(),
        EmptyFragment("#系统工具"),
        EmptyFragment("#图片工具"),
        EmptyFragment("#查询工具"),
        EmptyFragment("#提取工具"),
        EmptyFragment("#计算工具"),
        EmptyFragment("#文字工具"),
        EmptyFragment("#其他工具"),
    )
}