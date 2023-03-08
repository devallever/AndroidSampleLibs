package app.allever.android.learning.audiovideo.kernel

import app.allever.android.lib.core.ext.toast

/**
 * MediaPlayer内核
 */
class AndroidPlayer: AbsPlayer() {
    override fun play() {
        toast("MediaPlayer内核：play")
    }

    override fun pause() {
        toast("MediaPlayer内核：pause")
    }

    override fun stop() {
        toast("MediaPlayer内核：stop")
    }

    override fun release() {
        toast("MediaPlayer内核：release")
    }
}