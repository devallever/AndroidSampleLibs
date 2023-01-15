package app.allever.android.lib.demo.ui.notification

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.demo.databinding.ActivityNotificationMainBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class NotificationActivity :
    BaseActivity<ActivityNotificationMainBinding, NotificationMainViewModel>() {
    override fun init() {
        initTopBar("通知")
    }

    override fun inflateChildBinding() = ActivityNotificationMainBinding.inflate(layoutInflater)
}

class NotificationMainViewModel() : BaseViewModel() {

}