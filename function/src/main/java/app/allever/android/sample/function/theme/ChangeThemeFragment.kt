package app.allever.android.sample.function.theme

import app.allever.android.lib.mvvm.base.BaseMvvmFragment
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.function.databinding.FragmentChangeThemeBinding
import skin.support.SkinCompatManager

class ChangeThemeFragment : BaseMvvmFragment<FragmentChangeThemeBinding, BaseViewModel>() {

    override fun inflate() = FragmentChangeThemeBinding.inflate(layoutInflater)

    override fun init() {
        SkinCompatManager.getInstance().restoreDefaultTheme()
        mBinding.btnChangeThemeInApp.setOnClickListener {
            SkinCompatManager.getInstance()
                .loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
        }
    }
}