package app.allever.android.learning.audiovideo.audio

abstract class BaseDecodeAacThread(val aacPath: String) : Thread() {

    init {
        initIo()
    }


    override fun run() {
        super.run()
        decode()
    }

    private fun initIo() {

    }

    protected fun closeIo() {

    }

    abstract fun initDecoder()

    abstract fun decode()
}