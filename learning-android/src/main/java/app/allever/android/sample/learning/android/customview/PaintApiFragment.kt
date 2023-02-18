package app.allever.android.sample.learning.android.customview

import android.graphics.Paint
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailClickAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding

class PaintApiFragment : ListFragment<FragmentListBinding, ListViewModel, TextDetailClickItem>() {
    override fun getAdapter() = TextDetailClickAdapter()

    override fun getList(): MutableList<TextDetailClickItem> {
        val list = mutableListOf<TextDetailClickItem>()
        val clz = Paint::class.java
        val methodSet = mutableSetOf<String>()
        clz.methods.map {
            methodSet.add(it.name)
        }
        methodSet.map {
            val item = TextDetailClickItem(it)
            list.add(item)
        }
        return list
    }
}