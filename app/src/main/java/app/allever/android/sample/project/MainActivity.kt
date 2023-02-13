package app.allever.android.sample.project

import android.os.Handler
import android.os.Looper
import android.os.Message
import app.allever.android.lib.common.BaseActivity
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.helper.FragmentHelper
import app.allever.android.lib.core.util.TimeUtils
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.lib.mvvm.base.MvvmConfig
import app.allever.android.sample.project.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun init() {
        initTopBar(getString(R.string.app_name), showBackIcon = false)
        FragmentHelper.addToContainer(
            supportFragmentManager,
            MainListFragment(),
            R.id.fragmentContainer
        )

        val time1 = "2022-11-25 18:00:00:001"
        val time2 = "2022-11-25 18:00:00:201"
        val time1Ms = TimeUtils.getTimeStamp(time1, TimeUtils.FORMAT_yyyy_MM_dd_hh_mm_ss_SSS)
        val time2Ms = TimeUtils.getTimeStamp(time2, TimeUtils.FORMAT_yyyy_MM_dd_hh_mm_ss_SSS)
        val interval = time2Ms - time1Ms
        log("时间间隔 = $interval")
    }

    override fun inflateChildBinding() = ActivityMainBinding.inflate(layoutInflater)

    private fun test() {
        val t = thread {
            Looper.myLooper()
            Looper.prepare()
            Looper.loop()
            val handler = Handler()
            handler.sendEmptyMessage(0)
            handler.post {

            }
            Looper.myLooper()?.quit()
        }

        t.start()


    }
}

class MainViewModel : BaseViewModel() {
    override fun init() {
    }
}