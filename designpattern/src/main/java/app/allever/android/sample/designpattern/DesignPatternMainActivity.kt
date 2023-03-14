package app.allever.android.sample.designpattern

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.designpattern.databinding.ActivityDesignPatternBinding

class DesignPatternMainActivity : BaseActivity<ActivityDesignPatternBinding, BaseViewModel>() {
    override fun inflateChildBinding() = ActivityDesignPatternBinding.inflate(layoutInflater)

    override fun init() {
        initTopBar("设计模式")
        FragmentHelper.addToContainer(
            supportFragmentManager,
            DesignPatternMainFragment(),
            binding.fragmentContainer.id
        )
    }

}