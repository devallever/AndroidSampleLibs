package app.allever.android.sample.function.im.message

/**
 * 图片/视频/音频
 */
open class MediaMessage : BaseMessage() {
    var url = ""
    var cachePath = ""
    var path = ""
    var width = 0
    var height = 0
    var duration = 0L
    var cover = ""
}