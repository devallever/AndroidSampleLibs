package app.allever.android.sample.jetpack.navigation

import android.os.Bundle
import android.view.View
import app.allever.android.lib.core.util.StatusBarCompat
import app.allever.android.lib.mvvm.base.BaseMvvmActivity
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.jetpack.databinding.ActivityNavigationMainBinding

class NavigationMainActivity : BaseMvvmActivity<ActivityNavigationMainBinding, BaseViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        StatusBarCompat.translucentStatusBar(this, true)
        StatusBarCompat.changeToLightStatusBar(this)
        super.onCreate(savedInstanceState)
    }
    override fun inflate() = ActivityNavigationMainBinding.inflate(layoutInflater)

    override fun init() {
    }

//    override fun inflateChildBinding() = ActivityNavigationMainBinding.inflate(layoutInflater)

}