package app.allever.android.sample.function.apps

import androidx.viewbinding.ViewBinding
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.widget.R
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 *@Description
 *@author: zq
 *@date: 2024/2/1
 */
class ChangeIconListFragment : ListFragment<ViewBinding, BaseViewModel, AppItem>() {
    override fun getAdapter(): BaseQuickAdapter<AppItem, *> = AppItemAdapter()

    override fun getList() = mutableListOf<AppItem>().apply {
        addAll(AppHelper.fetchLocalAppList2())
    }

    override fun onItemClick(position: Int, item: AppItem) {
//        IconCompat.createWithBitmap(AssetsHelper.toBitmap(""))
        val result = AppHelper.createShortcut(
            item.pkg,
            item.launchActivity,
            "My ${item.name}",
            R.drawable.icon_album_select
        )
        if (result) {
            toast("Change icon success!")
        } else {
            toast("Fail to change icon!")
        }
    }
}