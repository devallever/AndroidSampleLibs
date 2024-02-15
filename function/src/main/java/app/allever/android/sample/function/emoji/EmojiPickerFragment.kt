package app.allever.android.sample.function.emoji

import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.function.databinding.FragmentEmojiPickerBinding

/**
 *@Description
 *@author: zq
 *@date: 2024/2/13
 */
class EmojiPickerFragment: BaseFragment<FragmentEmojiPickerBinding, BaseViewModel>() {
    override fun inflate() = FragmentEmojiPickerBinding.inflate(layoutInflater)

    override fun init() {
        mBinding.apply {
            emojiPickerView.setOnEmojiPickedListener {
                etEmoji.setText(it.emoji)
            }
        }
    }
}