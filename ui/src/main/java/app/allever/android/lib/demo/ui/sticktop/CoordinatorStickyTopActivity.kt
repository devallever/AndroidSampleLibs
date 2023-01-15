package app.allever.android.lib.demo.ui.sticktop

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.demo.databinding.ActivityCoordinatorStickyTopBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class CoordinatorStickyTopActivity :
    BaseActivity<ActivityCoordinatorStickyTopBinding, CoordinatorStickyTopViewModel>() {

    override fun inflateChildBinding() = ActivityCoordinatorStickyTopBinding.inflate(layoutInflater)

    override fun init() {
        initTopBar("CoordinatorLayout + AppbarLayout")

    }


}

class CoordinatorStickyTopViewModel : BaseViewModel() {
    override fun init() {

    }
}