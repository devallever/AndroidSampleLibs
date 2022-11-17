package app.allever.android.sample.function.im.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.function.BR
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.FragmentConversationBinding
import app.allever.android.sample.function.im.ui.adapter.ScrollBoundaryDeciderAdapter
import app.allever.android.sample.function.im.viewmodel.ConversationViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter

class ConversationFragment :
    BaseMvvmFragment<FragmentConversationBinding, ConversationViewModel>() {
    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_conversation, BR.conversationVM)

    override fun init() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mBinding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mBinding.recyclerView.adapter = mViewModel.messageAdapter

        val arrow: View = mBinding.footerView.findViewById(ClassicsFooter.ID_IMAGE_ARROW)
        arrow.scaleY = -1f //必须设置
        mBinding.recyclerView.scaleY = -1f//必须设置
        mBinding.refreshLayout.setEnableRefresh(false) //必须关闭
        mBinding.refreshLayout.setEnableAutoLoadMore(true) //必须关闭
        mBinding.refreshLayout.setEnableNestedScroll(false) //必须关闭
        mBinding.refreshLayout.setEnableScrollContentWhenLoaded(true) //必须关闭
        mBinding.refreshLayout.layout.scaleY = -1f //必须设置

        mBinding.refreshLayout.setScrollBoundaryDecider(object : ScrollBoundaryDeciderAdapter() {
            override fun canLoadMore(content: View?): Boolean {
                return super.canRefresh(content) //必须替换
            }
        })

        //监听加载，而不是监听 刷新
        mBinding.refreshLayout.setOnLoadMoreListener { refreshLayout ->
            refreshLayout.layout.postDelayed({
                mBinding.refreshLayout.finishLoadMore(true)
            }, 800)
        }
    }
}