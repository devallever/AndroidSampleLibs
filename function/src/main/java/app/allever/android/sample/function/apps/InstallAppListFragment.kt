package app.allever.android.sample.function.apps

import androidx.viewbinding.ViewBinding
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.mvvm.base.BaseViewModel
import com.chad.library.adapter.base.BaseQuickAdapter

/**
 *@Description
 *@author: zq
 *@date: 2024/2/1
 */
class InstallAppListFragment : ListFragment<ViewBinding, BaseViewModel, AppItem>() {
    override fun getAdapter(): BaseQuickAdapter<AppItem, *> = AppItemAdapter()

    override fun getList() = mutableListOf<AppItem>().apply {
        addAll(AppHelper.fetchLocalAppList2())
    }

    override fun onItemClick(position: Int, item: AppItem) {
        toast(item.name + " " + item.launchActivity)
    }
}