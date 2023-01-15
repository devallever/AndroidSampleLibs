package app.allever.android.lib.demo.ui.dialog.bottomslide

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import app.allever.android.lib.core.helper.DisplayHelper
import app.allever.android.lib.demo.databinding.DialogBottomBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSlideDialog : BottomSheetDialogFragment() {

    protected lateinit var mBinding: DialogBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, app.allever.android.lib.core.R.style.BottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DialogBottomBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog?
        val frameLayout =
            dialog?.delegate?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        frameLayout?.let {
            val layoutParams = frameLayout.layoutParams
            layoutParams.height = DisplayHelper.dip2px(250)
            val behavior = BottomSheetBehavior.from(frameLayout)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    open fun show(manager: FragmentManager) {
        if (context is Activity) {
            if ((context as Activity).isFinishing || (context as Activity).isDestroyed) {
                return
            }
        }
        if (isAdded || manager.findFragmentByTag(TAG) != null) {
            return
        }
        super.show(manager, TAG)
    }

    companion object {
        private val TAG = BottomSlideDialog::class.java.simpleName
    }
}