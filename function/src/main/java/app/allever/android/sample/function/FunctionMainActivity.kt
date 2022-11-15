package app.allever.android.sample.function

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.function.databinding.ActivityFunctionMainBinding

class FunctionMainActivity : BaseActivity<ActivityFunctionMainBinding, FunctionMainViewModel>() {
    override fun init() {
        initTopBar("功能列表")
    }

    override fun getContentMvvmConfig() =
        MvvmConfig(R.layout.activity_function_main, BR.functionMainVM)
}

class FunctionMainViewModel : BaseViewModel() {
    override fun init() {

    }
}