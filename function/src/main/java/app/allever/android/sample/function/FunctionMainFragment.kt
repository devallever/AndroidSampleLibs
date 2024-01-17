package app.allever.android.sample.function

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.sample.function.download.DownloadMainFragment
import app.allever.android.sample.function.im.IMMainFragment
import app.allever.android.sample.function.theme.ThemeMainActivity
import com.chad.library.adapter.base.BaseQuickAdapter

class FunctionMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter(): BaseQuickAdapter<TextClickItem, *> = TextClickAdapter()
    override fun getList(): MutableList<TextClickItem> = mutableListOf(
        TextClickItem("主题") {
            ActivityHelper.startActivity<ThemeMainActivity> { }
        },
        TextClickItem("IM") {
            FragmentActivity.start<IMMainFragment>(it.title)
        },
        TextClickItem("Download") {
            FragmentActivity.start<DownloadMainFragment>(it.title)
        },
    )
}