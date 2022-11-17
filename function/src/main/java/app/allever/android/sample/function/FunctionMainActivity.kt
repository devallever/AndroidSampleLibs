package app.allever.android.sample.function

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.ActivityHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.function.databinding.ActivityFunctionMainBinding
import app.allever.android.sample.function.theme.ThemeMainActivity

class FunctionMainActivity : BaseActivity<ActivityFunctionMainBinding, FunctionMainViewModel>() {
    override fun init() {
        initTopBar("功能列表")
        binding.btnTheme.setOnClickListener {
            ActivityHelper.startActivity<ThemeMainActivity> { }
        }
    }

    override fun getContentMvvmConfig() =
        MvvmConfig(R.layout.activity_function_main, BR.functionMainVM)
}

class FunctionMainViewModel : BaseViewModel() {
    override fun init() {
    }
}