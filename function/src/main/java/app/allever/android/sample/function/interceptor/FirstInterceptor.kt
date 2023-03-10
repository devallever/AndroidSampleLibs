package app.allever.android.sample.function.interceptor

import app.allever.android.lib.core.ext.log

class FirstInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain) {
        log("执行FirstInterceptor")
        if (true) {
            log("条件成立，不在执行")
            return
        } else {
            log("条件不成立，继续执行下一个拦截器")
        }
        chain.process()
    }
}