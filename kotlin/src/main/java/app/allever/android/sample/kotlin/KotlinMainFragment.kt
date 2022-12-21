package app.allever.android.sample.kotlin

import app.allever.android.lib.common.FragmentActivity
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.sample.kotlin.function.flow.FlowMainFragment

class KotlinMainFragment : ListFragment<FragmentListBinding, ListViewModel, String>() {

    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf("Flow", "Coroutine", "Higher Function")

    override fun onItemClick(position: Int, item: String) {
        when (position) {
            0 -> {
                FragmentActivity.start<FlowMainFragment>(item)
            }
        }
    }
}