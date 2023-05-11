package app.allever.android.sample.project

import android.webkit.JavascriptInterface
import app.allever.android.lib.common.BaseFragment
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toJson
import app.allever.android.lib.core.helper.GsonHelper
import app.allever.android.lib.core.util.WebViewUtils
import app.allever.android.lib.mvvm.base.BaseViewModel
import app.allever.android.sample.project.databinding.FragmentH5Binding

class H5Fragment : BaseFragment<FragmentH5Binding, BaseViewModel>() {
    override fun inflate() = FragmentH5Binding.inflate(layoutInflater)

    override fun init() {
        initWebView()
    }

    private fun initWebView() {
        //需要科学上网
        //h5的对象名jsBridge 要一致
        WebViewUtils.loadData(
            mBinding.webView,
            "https://api.gilet.ceshi.in/testku.html",
            JSInterface(),
            "jsBridge"
        )
    }

    class JSInterface {
        //方法名要一致
        //H5调Android使用的方法，需要在方法上用 @JavascriptInterface注解来定义该方法
        @JavascriptInterface
        fun postMessages(message: String) {
            log("message: $message")
        }

        @JavascriptInterface
        fun postMessage(message: String, content: String) {
            log("message: $message")
            log("content: $content")

            val response = BaseResponse.toObj(message, content)
            log("response = ${response?.toJson()}")

            val map = BaseResponse.toMap(response)
            log("map = ${map.toJson()}")
        }
    }


    open class BaseResponse {
        var success = 0

        companion object {
            const val login = "login"
            const val logout = "logout"
            const val register = "register"
            const val registerClick = "registerClick"
            const val rechargeClick = "rechargeClick"
            const val firstrecharge = "firstrecharge"
            const val recharge = "recharge"
            const val withdrawClick = "withdrawClick"
            const val withdrawOrderSuccess = "withdrawOrderSuccess"

            fun toObj(message: String, content: String): BaseResponse? {
                return when (message) {
                    BaseResponse.login,
                    BaseResponse.logout,
                    BaseResponse.register,
                    BaseResponse.registerClick,
                    BaseResponse.rechargeClick,
                    BaseResponse.withdrawClick -> {
                        GsonHelper.fromJson(content, BaseResponse::class.java)
                    }
                    else -> {
                        GsonHelper.fromJson(content, ChargeResponse::class.java)
                    }
                }
            }

            fun toMap(response: BaseResponse?): Map<String, Any> {
                val map = mutableMapOf<String, Any>()
                if (response is BaseResponse) {
                    map["success"] = response.success
                }
                if (response is ChargeResponse) {
                    map["amount"] = response.amount
                    map["currency"] = response.currency
                    map["isFirst"] = response.isFirst
                }
                return map
            }
        }


    }

    open class ChargeResponse : BaseResponse() {
        var amount = ""
        var currency = ""
        var isFirst = 0
    }

    class LoginResponse : BaseResponse()

    class LogoutResponse : BaseResponse()

    class RegisterResponse : BaseResponse()

    class RegisterClickResponse : BaseResponse()

    class RechargeClickResponse : BaseResponse()

    class FirstRechargeResponse : ChargeResponse()

    class RechargeResponse : ChargeResponse()

    class WithdrawClick : BaseResponse()

    class WithdrawOrderResponse : ChargeResponse()

}