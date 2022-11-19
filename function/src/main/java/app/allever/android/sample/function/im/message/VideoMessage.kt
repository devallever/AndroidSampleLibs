package app.allever.android.sample.function.im.message

class VideoMessage : MediaMessage() {
    fun isWidthVideo() = width >= height
}