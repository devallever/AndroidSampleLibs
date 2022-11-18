package app.allever.android.sample.function.im.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.core.function.permission.PermissionHelper
import app.allever.android.lib.core.function.permission.PermissionListener
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.function.BR
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.FragmentConversationBinding
import app.allever.android.sample.function.im.ui.adapter.ScrollBoundaryDeciderAdapter
import app.allever.android.sample.function.im.ui.widget.InputBar
import app.allever.android.sample.function.im.ui.widget.InputBarDialog
import app.allever.android.sample.function.im.viewmodel.ConversationViewModel
import com.scwang.smart.refresh.footer.ClassicsFooter

class ConversationFragment :
    BaseMvvmFragment<FragmentConversationBinding, ConversationViewModel>() {

    private var inputBarDialog: InputBarDialog? = null

    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_conversation, BR.conversationVM)

    override fun init() {
        initRecyclerView()
        initListener()
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

    private fun initListener() {

        mBinding.tvInput.setOnClickListener {
            showInputDialog(false)
        }
        mBinding.tvEmo.setOnClickListener {
            showInputDialog(true)
        }
    }

    private fun showInputDialog(showEmo:Boolean){
        inputBarDialog = InputBarDialog(context,showEmo, mBinding.tvInput.text.toString().trim(), object : InputBar.InputBarListener {
            override fun onClickSend(message: String) {
                mViewModel.sendMessage(message)
            }

            override fun onClickAdd() {
                PermissionHelper.requestPermission(
                    object : PermissionListener {
                        override fun onAllGranted() {
//                            chooseImageFormPicture()
                        }
                    },
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                )
            }

            override fun onClickEmoji(): Boolean {
                return false
            }

            override fun inputTextChanged(content: String) {
                mBinding.tvInput.setText(content)
                mBinding.tvSend.visibility = if (content.isNotEmpty()) View.VISIBLE else View.GONE
            }
        })
        inputBarDialog?.show()
    }

}