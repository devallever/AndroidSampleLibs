package app.allever.android.sample.learning.android.customview

import android.graphics.Canvas
import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextDetailClickAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding

class CanvasApiFragment : ListFragment<FragmentListBinding, ListViewModel, TextDetailClickItem>() {
    override fun getAdapter() = TextDetailClickAdapter()

    override fun getList(): MutableList<TextDetailClickItem> {
        val list = mutableListOf<TextDetailClickItem>()
        val canvasClz = Canvas::class.java
        val methodSet = mutableSetOf<String>()
        canvasClz.methods.map {
            methodSet.add(it.name)
        }
        methodSet.map {
            list.add(TextDetailClickItem(it))
        }
        return list
    }
}