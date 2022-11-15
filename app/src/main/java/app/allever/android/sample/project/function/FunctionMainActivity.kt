package app.allever.android.sample.project.function

import app.allever.android.sample.project.BR
import app.allever.android.sample.project.R
import app.allever.android.sample.project.databinding.ActivityFunctionMainBinding
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class FunctionMainActivity: BaseActivity<ActivityFunctionMainBinding, FunctionMainViewModel>() {
    override fun init() {

    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_function_main, BR.functionMainVM)
}

class FunctionMainViewModel: BaseViewModel() {
    override fun init() {

    }
}