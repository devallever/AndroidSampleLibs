package app.allever.android.sample.function

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.sample.function.apps.AppHelper
import app.allever.android.sample.function.apps.ChangeIconListFragment
import app.allever.android.sample.function.apps.InstallAppListFragment
import app.allever.android.sample.function.download.DownloadMainFragment
import app.allever.android.sample.function.emoji.EmojiPickerFragment
import app.allever.android.sample.function.im.IMMainFragment
import app.allever.android.sample.function.theme.ThemeMainActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.vdurmont.emoji.Emoji

class FunctionMainFragment : ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun init() {
        super.init()
        AppHelper.init(requireContext())
        AppHelper.preLoad()
    }
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
        TextClickItem("安装列表") {
            FragmentActivity.start<InstallAppListFragment>(it.title)
        },
        TextClickItem("更改图标") {
            FragmentActivity.start<ChangeIconListFragment>(it.title)
        },
        TextClickItem("Emoji选择器") {
            FragmentActivity.start<EmojiPickerFragment>(it.title)
        },
    )
}