package app.allever.android.learning.audiovideo.util

import android.media.MediaFormat

fun MediaFormat.isVideo(): Boolean {
    return getString(MediaFormat.KEY_MIME)?.startsWith("video/") == true
}

fun MediaFormat.isAudio(): Boolean {
    return getString(MediaFormat.KEY_MIME)?.startsWith("audio/") == true
}