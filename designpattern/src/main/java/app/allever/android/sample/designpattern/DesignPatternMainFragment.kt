package app.allever.android.sample.designpattern

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextClickAdapter
import app.allever.android.lib.common.adapter.bean.TextClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.sample.designpattern.behavior.command.CommandClient

class DesignPatternMainFragment :
    ListFragment<FragmentListBinding, ListViewModel, TextClickItem>() {
    override fun getAdapter() = TextClickAdapter()

    override fun getList() = mutableListOf(
        TextClickItem("（行为型）命令模式") {
            CommandClient().execute()
            CommandClient().executeRussiaBlockGame()
        }
    )
}