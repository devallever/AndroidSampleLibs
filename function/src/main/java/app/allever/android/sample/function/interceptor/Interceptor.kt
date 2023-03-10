package app.allever.android.sample.function.interceptor


interface Interceptor {

    /**
     * 每个拦截器都重写的方法
     */
    fun intercept(chain: Chain)

    /**
     * 拦截链条
     */
    interface Chain {
        /**
         * 调用下一个拦截器
         */
        fun process()
    }
}