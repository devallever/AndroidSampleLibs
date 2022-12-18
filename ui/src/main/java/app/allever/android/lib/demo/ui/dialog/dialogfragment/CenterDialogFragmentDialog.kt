package app.allever.android.lib.demo.ui.dialog.dialogfragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.DialogCenterBinding

class CenterDialogFragmentDialog : DialogFragment() {
    private lateinit var mBinding: DialogCenterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //底部上移弹出
        setStyle(STYLE_NORMAL, R.style.Theme_Design_BottomSheetDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(DisplayHelper.dip2px(285), DisplayHelper.dip2px(250))
        dialog?.window?.setGravity(Gravity.CENTER)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_center,
            null,
            false
        )
        return mBinding.root
    }
}