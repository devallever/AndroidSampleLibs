package app.allever.android.sample.billing.helper

import android.util.Log
import app.allever.android.lib.core.app.App
import app.allever.android.lib.core.ext.toast

abstract class BaseBilling : IBilling {
    val DEFAULT_ERROR_MSG = "Un support the country"

    protected var mIsConnect = false
    protected var mNeedReconnect = false

    override fun disConnect() {
        log("已断开谷歌连接")
        mNeedReconnect = false
        mIsConnect = false
    }

    protected fun reconnectIfNeed() {
        if (mNeedReconnect) {
            App.mainHandler.postDelayed({
                connect()
            }, 1000)
        }
    }

    protected fun log(msg: String, showToast: Boolean = false) {
        if (showToast) {
            toast(msg)
        }
        Log.d("ILogger", msg)
    }

    protected fun logE(msg: String, showToast: Boolean = false) {
        if (showToast) {
            toast(msg)
        }
        Log.e("ILogger", msg)
    }
}