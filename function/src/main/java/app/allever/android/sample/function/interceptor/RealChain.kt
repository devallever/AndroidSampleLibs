package app.allever.android.sample.function.interceptor

class RealChain(builder: Builder) : Interceptor.Chain {
    private val mInterceptors = mutableListOf<Interceptor>()

    init {
        mInterceptors.addAll(builder.interceptors)
    }

    private var mNext = 0
    override fun process() {
        when (mNext) {
            in mInterceptors.indices -> {
                val interceptor = mInterceptors[mNext]
                mNext++
                interceptor.intercept(this)
            }
            mInterceptors.size -> {
                mInterceptors.clear()
            }
        }
    }

    class Builder private constructor() {

        companion object {
            fun create(): Builder = Builder()
        }

        var interceptors = mutableListOf<Interceptor>()

        fun addInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }

        fun addInterceptors(interceptors: List<Interceptor>): Builder {
            this.interceptors.addAll(interceptors)
            return this
        }

        fun build(): RealChain = RealChain(this)
    }
}