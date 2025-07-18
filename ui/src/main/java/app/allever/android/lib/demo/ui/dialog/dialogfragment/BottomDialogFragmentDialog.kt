package app.allever.android.lib.demo.ui.dialog.dialogfragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.DialogBottomBinding

class BottomDialogFragmentDialog : DialogFragment() {
    private lateinit var mBinding: DialogBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.google.android.material.R.style.Theme_Design_BottomSheetDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(DisplayHelper.getScreenWidth(), DisplayHelper.dip2px(250))
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogBottomBinding.inflate(layoutInflater)
        return mBinding.root
    }
}