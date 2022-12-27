package app.allever.android.lib.demo.ui.notification

import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.demo.R
import app.allever.android.lib.demo.databinding.ActivityNotificationMainBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig

class NotificationActivity :
    BaseActivity<ActivityNotificationMainBinding, NotificationMainViewModel>() {
    override fun init() {
        initTopBar("通知")
    }

    override fun getContentMvvmConfig() = MvvmConfig(R.layout.activity_notification_main, -1)

}

class NotificationMainViewModel() : BaseViewModel() {

}