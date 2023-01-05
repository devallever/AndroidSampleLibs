package app.allever.android.learning.audiovideo.audio

interface AudioRecordCallback {
    fun onFinish(path: String)
    fun onError(msg: String)
}