package app.allever.android.lib.common

import app.allever.android.lib.common.databinding.EmptyPageBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class FullScreenActivity : BaseActivity<EmptyPageBinding, BaseViewModel>() {
    override fun init() {

    }

    override fun inflateChildBinding() = EmptyPageBinding.inflate(layoutInflater)

    override fun showTopBar(): Boolean {
        return false
    }
}