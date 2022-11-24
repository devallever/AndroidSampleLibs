package app.allever.android.lib.demo.ui

import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.demo.BR
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.ActivityAutoScrollBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class AutoScrollActivity : BaseActivity<ActivityAutoScrollBinding, BaseViewModel>() {

    private val adapter = AutoScrollAdapter(false)

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_auto_scroll, BR.autoScrollVM)

    override fun init() {
        initTopBar("自动滚动")
        binding.autoScrollRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.autoScrollRecyclerView.adapter = adapter
        initTestData()
        binding.autoScrollRecyclerView.start()

        binding.btnSetInterval.setOnClickListener {
            val interval = binding.etInterval.text?.toString()?.toLong() ?: 1000L
            binding.autoScrollRecyclerView.setInterval(interval, true)
        }

        binding.btnInsertData.setOnClickListener {
            val content = binding.etContent.text.toString()
            if (TextUtils.isEmpty(content)) {
                return@setOnClickListener
            }

            val lastPosition = binding.autoScrollRecyclerView.getChildLayoutPosition(
                binding.autoScrollRecyclerView.getChildAt(binding.autoScrollRecyclerView.childCount - 1)
            )
            val insertIndex  = lastPosition % adapter.data.size
            log("插入位置 = $insertIndex")
            adapter.addData(insertIndex, content)
        }
    }

    private fun initTestData() {
        val list = mutableListOf<String>()
        for (i in 1..100) {
            list.add("item: $i")
        }
        adapter.setList(list)
    }

}