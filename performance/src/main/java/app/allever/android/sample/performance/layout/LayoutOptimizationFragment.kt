package app.allever.android.sample.performance.layout

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper

class LayoutOptimizationFragment :
    ListFragment<FragmentListBinding, ListViewModel, TextDetailItem>() {
    override fun getAdapter() = TextDetailAdapter()

    override fun getList() = mutableListOf(
        TextDetailItem(
            "原因", "一个是IO,一个是反射\n1.侧面缓解(异步加载)\n" +
                    "2.根本解决(不需要IO,反射过程,如X2C,Anko,Compose等)"
        ),
        TextDetailItem("ViewStub延迟加载", "ViewStub延迟加载"),
        TextDetailItem("ConstraintLayout", "实现扁平化布局，减少嵌套层级"),
        TextDetailItem("include/merge", "merge标签减少一个层级"),
        TextDetailItem("RelativeLayout的性能问题", "会测量两次，不建议用"),
        TextDetailItem("LinearLayout的weight", "会测量两侧，不建议用"),
        TextDetailItem("过渡绘制", "同一个像素点绘制多次, 避免层级叠加"),
        TextDetailItem(
            "AsyncLayoutInflater",
            "AsyncLayoutInflater异步加载布局, UI加载过程迁移到了子线程，保证了UI线程的高响应 缺点在于牺牲了易用性，同时如果在初始化过程中调用了UI可能会导致崩溃"
        ), //index = 7
    )

    override fun onItemClick(position: Int, item: TextDetailItem) {
        when (position) {
            1 -> {
                FragmentActivity.start<ViewStubFragment>(item.title)
            }
            7 -> {
                ActivityHelper.startActivity<AsyncInflateActivity> { }
            }
        }
    }
}