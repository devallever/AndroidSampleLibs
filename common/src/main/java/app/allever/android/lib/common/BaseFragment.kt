package app.allever.android.lib.common

import androidx.databinding.ViewDataBinding
import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel> : BaseMvvmFragment<DB, VM>() {
}