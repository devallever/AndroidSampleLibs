import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.demo.databinding.FragmentAutoScrollLoopBinding
import app.allever.android.lib.demo.ui.AutoScrollAdapter
import app.allever.android.lib.mvvm.base.BaseViewModel

class AutoScrollLoopFragment : BaseFragment<FragmentAutoScrollLoopBinding, BaseViewModel>() {

    private val adapter = AutoScrollAdapter(true)

    override fun inflate() = FragmentAutoScrollLoopBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.autoScrollRecyclerView.layoutManager = LinearLayoutManager(context)
        mBinding.autoScrollRecyclerView.adapter = adapter
        initTestData()
        mBinding.autoScrollRecyclerView.start()

        mBinding.btnSetInterval.setOnClickListener {
            val interval = mBinding.etInterval.text?.toString()?.toLong() ?: 1000L
            mBinding.autoScrollRecyclerView.setInterval(interval, true)
        }

        mBinding.btnInsertData.setOnClickListener {
            val content = mBinding.etContent.text.toString()
            if (TextUtils.isEmpty(content)) {
                return@setOnClickListener
            }

            val lastPosition = mBinding.autoScrollRecyclerView.getChildLayoutPosition(
                mBinding.autoScrollRecyclerView.getChildAt(mBinding.autoScrollRecyclerView.childCount - 1)
            )
            val insertIndex = lastPosition % adapter.data.size
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