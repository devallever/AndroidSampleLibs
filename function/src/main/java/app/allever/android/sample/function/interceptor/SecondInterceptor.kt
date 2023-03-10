package app.allever.android.sample.function.interceptor

import app.allever.android.lib.core.ext.log

class SecondInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain) {
        log("SecondInterceptor")
        if (false) {
            log("条件成立，不在执行")
        } else {
            log("条件不成立，继续执行下一个拦截器")
        }
    }
}