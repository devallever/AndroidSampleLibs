package app.allever.android.sample.billing

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.billing.databinding.ActivityBillingMainBinding

class BillingMainActivity : BaseActivity<ActivityBillingMainBinding, BaseViewModel>() {
    override fun init() {
        initTopBar("谷歌内购/订阅/支付")
        FragmentHelper.addToContainer(
            supportFragmentManager,
            BillingMainFragment(),
            binding.fragmentContainer.id
        )
    }

    override fun inflateChildBinding() = ActivityBillingMainBinding.inflate(layoutInflater)
}