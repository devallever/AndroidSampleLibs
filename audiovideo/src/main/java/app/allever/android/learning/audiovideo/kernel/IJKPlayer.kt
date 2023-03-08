package app.allever.android.learning.audiovideo.kernel

import app.allever.android.lib.core.ext.toast

/***
 * IJKPlayer内核
 */
class IJKPlayer: AbsPlayer() {
    override fun play() {
        toast("IJKPlayer内核：play")
    }

    override fun pause() {
        toast("IJKPlayer内核：pause")
    }

    override fun stop() {
        toast("IJKPlayer内核：stop")
    }

    override fun release() {
        toast("IJKPlayer内核：release")
    }
}