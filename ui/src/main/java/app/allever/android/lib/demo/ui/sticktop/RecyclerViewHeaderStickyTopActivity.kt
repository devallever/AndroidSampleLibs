package app.allever.android.lib.demo.ui.sticktop

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.ActivityRecyclerViewHeaderStickyTopBinding
import app.allever.android.lib.demo.databinding.RvHeaderBinding
import app.allever.android.lib.demo.databinding.RvHeaderStickyBinding
import app.allever.android.lib.demo.databinding.RvItemTextBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class RecyclerViewHeaderStickyTopActivity :
    BaseActivity<ActivityRecyclerViewHeaderStickyTopBinding, RecyclerViewHeaderStickyTopViewModel>() {
    private val adapter = RvAdapter()

    override fun inflateChildBinding() =
        ActivityRecyclerViewHeaderStickyTopBinding.inflate(layoutInflater)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun init() {
        initTopBar("RecyclerViewHeader吸顶")

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        val rvHeaderSticky = RvHeaderStickyBinding.inflate(layoutInflater)
        val rvHeader = RvHeaderBinding.inflate(layoutInflater)

//        initData()
        adapter.addHeaderView(rvHeader.rvHeader)
        adapter.addHeaderView(rvHeaderSticky.tvHeaderSticky)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val position =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (position >= 1) {
                    binding.tvHeaderSticky.visibility = View.VISIBLE
                } else {
                    binding.tvHeaderSticky.visibility = View.GONE
                }
            }
        })
    }

    private fun initData() {
        val list = mutableListOf<String>()
        for (i in 0..100) {
            list.add("text-$i")
        }
        adapter.setList(list)
    }
}

class RecyclerViewHeaderStickyTopViewModel : BaseViewModel() {
    override fun init() {

    }
}

class RvAdapter() :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.rv_item_text) {
    override fun convert(holder: BaseViewHolder, item: String) {
        val binding = RvItemTextBinding.bind(holder.itemView)
        binding.tvText.text = item
    }
}