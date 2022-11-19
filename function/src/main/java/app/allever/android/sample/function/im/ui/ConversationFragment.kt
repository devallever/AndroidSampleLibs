package app.allever.android.sample.function.im.ui

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.allever.android.lib.core.ext.toast
import app.allever.android.lib.core.function.mediapicker.MediaPickerHelper
import app.allever.android.lib.core.function.permission.PermissionHelper
import app.allever.android.lib.core.function.permission.PermissionListener
import app.allever.android.lib.core.helper.ViewHelper
import app.allever.android.lib.core.util.KeyboardUtils
import app.allever.android.lib.core.util.SoftKeyboardUtils
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.function.BR
import app.allever.android.sample.function.R
import app.allever.android.sample.function.databinding.FragmentConversationBinding
import app.allever.android.sample.function.im.ui.adapter.ExpandItem
import app.allever.android.sample.function.im.ui.adapter.ScrollBoundaryDeciderAdapter
import app.allever.android.sample.function.im.ui.widget.InputBar
import app.allever.android.sample.function.im.ui.widget.InputBarDialog
import app.allever.android.sample.function.im.viewmodel.ConversationViewModel
import com.vanniktech.emoji.EmojiPopup
import kotlinx.coroutines.launch

class ConversationFragment :
    BaseMvvmFragment<FragmentConversationBinding, ConversationViewModel>() {

    private var inputBarDialog: InputBarDialog? = null

    /**
     * emoji选择框
     */
    private var mEmojiPopup: EmojiPopup? = null

    override fun getMvvmConfig() = MvvmConfig(R.layout.fragment_conversation, BR.conversationVM)

    override fun init() {
        initMessageList()
        initInputPanel()
        initExpand()
        mViewModel.initExpandFunData()
        initListener()
    }

    private fun initMessageList() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter = mViewModel.messageAdapter
        mViewModel.messageAdapter.setOnItemClickListener { adapter, view, position ->
            if (SoftKeyboardUtils.isShown(context)) {
                KeyboardUtils.hideInput(activity)
            }
            mBinding.expandContainer.isVisible = false
        }

        mBinding.refreshLayout.setEnableRefresh(true)
        mBinding.refreshLayout.setEnableLoadMore(false)

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

        mBinding.etInput.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (mBinding.expandContainer.isVisible) {
                    mBinding.expandContainer.isVisible = false
                }
            }
        }
    }

    private fun initExpand() {
        val layoutManager = GridLayoutManager(activity, 3)
        mBinding.rvExpand.layoutManager = layoutManager
        mBinding.rvExpand.adapter = mViewModel.expandAdapter
        mViewModel.expandAdapter.setOnItemClickListener { adapter, view, position ->
            handleExpFunClick(mViewModel.expandAdapter.data[position])
        }

        mEmojiPopup = EmojiPopup.Builder
            .fromRootView(mBinding.root)
            .setKeyboardAnimationStyle(R.style.emoji_fade_animation_style)
            .setOnEmojiPopupShownListener { mBinding.ivEmoji.setImageResource(R.drawable.ic_input_panel_keyboard) }
            .setOnEmojiPopupDismissListener { mBinding.ivEmoji.setImageResource(R.drawable.ic_input_panel_emoji) }
            .setOnSoftKeyboardCloseListener { }
            .build(mBinding.etInput)

        mBinding.etInput.setOnClickListener {
            mBinding.expandContainer.isVisible = false
        }
    }

    private fun handleExpFunClick(expandItem: ExpandItem) {
        when (expandItem.type) {
            ExpandItem.TYPE_IMAGE -> {
                chooseMedia(MediaPickerHelper.TYPE_IMAGE)
            }
            ExpandItem.TYPE_VIDEO -> {
                chooseMedia(MediaPickerHelper.TYPE_VIDEO)
            }
            ExpandItem.TYPE_AUDIO_CALL -> {
                toast("语音通话")
            }
            ExpandItem.TYPE_VIDEO_CALL -> {
                toast("视频通话")
            }
            ExpandItem.TYPE_LOCATION -> {
                toast("位置")
            }
            else -> {

            }
        }
    }

    private fun initInputPanel() {
        mBinding.tvSend.setOnClickListener {
            mViewModel.sendTextMessage(
                mBinding.etInput.text?.toString() ?: return@setOnClickListener
            )
            mBinding.etInput.setText("")
            mBinding.recyclerView.scrollToPosition(0)
        }

        mBinding.etInput.addTextChangedListener {
            setVisibility(mBinding.tvSend, !TextUtils.isEmpty(it?.toString()))
            setVisibility(mBinding.ivAdd, TextUtils.isEmpty(it?.toString()))
        }

        mBinding.ivAdd.setOnClickListener {
            val showing = mBinding.expandContainer.isVisible
            if (showing) {
                if (mBinding.rvExpand.isVisible) {
                    ViewHelper.setVisible(mBinding.expandContainer, !showing)
                } else {
                    showFunction()
                }
            } else {
                ViewHelper.setVisible(mBinding.expandContainer, !showing)
            }
            KeyboardUtils.hideInput(activity)
        }

        mBinding.ivEmoji.setOnClickListener {
            showEmoji()
        }
    }

    var hideSoftInput = true

    @SuppressLint("ClickableViewAccessibility")
    private fun initListener() {

    }

    private fun chooseMedia(mediaType: String) {
        lifecycleScope.launch {
            val result = MediaPickerHelper.launchPicker(
                context ?: return@launch,
                mediaType
            )
            result.list.map {
                mViewModel.sendMediaMessage(it, mediaType)
            }
            mBinding.recyclerView.scrollToPosition(0)
        }
    }

    private fun showFunction() {
        mBinding.rvExpand.isVisible = true
        mBinding.expandContainer.isVisible = true
        mBinding.emojiContainer.isVisible = false
        KeyboardUtils.hideInput(activity)
    }

    private fun showEmoji() {
//        mBinding.emojiContainer.isVisible = true
        mBinding.expandContainer.isVisible = true
        mBinding.rvExpand.isVisible = false
        KeyboardUtils.hideInput(activity)
        mEmojiPopup?.toggle()
    }

    private fun showInputDialog(showEmo: Boolean) {
        inputBarDialog = InputBarDialog(
            context,
            showEmo,
            mBinding.etInput.text.toString().trim(),
            object : InputBar.InputBarListener {
                override fun onClickSend(message: String) {
                    mViewModel.sendTextMessage(message)
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
                    mBinding.etInput.setText(content)
                    mBinding.tvSend.visibility =
                        if (content.isNotEmpty()) View.VISIBLE else View.GONE
                }
            })
        inputBarDialog?.show()
    }

}