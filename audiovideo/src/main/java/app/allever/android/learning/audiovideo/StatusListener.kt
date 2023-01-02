package app.allever.android.learning.audiovideo

interface StatusListener {
    fun onPrepare(duration: Int)
    fun onVideoPlay()
    fun onVideoPause()
    fun onVideoError()
    fun onVideoPlaying(currentPosition: Int)
}