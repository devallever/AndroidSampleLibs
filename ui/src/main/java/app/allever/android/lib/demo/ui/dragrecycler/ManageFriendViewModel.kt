package app.allever.android.lib.demo.ui.dragrecycler

import app.allever.android.lib.mvvm.base.BaseViewModel
import app.hxz.anime.business.message.bean.FriendGroupItem


class ManageFriendViewModel : BaseViewModel() {

    val adapter = FriendGroupItemDragAdapter()

    fun getTestData() {
        val list = mutableListOf<FriendGroupItem>()
        for (i in 0..4) {
            list.add(FriendGroupItem(i.toString(), "我的分组${i}", false))
        }
        adapter.dataSet = list
    }

}