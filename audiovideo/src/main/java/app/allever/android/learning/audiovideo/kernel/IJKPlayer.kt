package app.allever.android.learning.audiovideo.kernel

import android.content.res.AssetFileDescriptor
import android.view.Surface
import android.view.SurfaceHolder

/***
 * IJKPlayer内核
 */
class IJKPlayer : AbsPlayer() {

    override fun initPlayer() {

    }

    override fun setDataSource(path: String, headers: Map<String, String>?) {

    }

    override fun setDataSource(accessFileDescriptor: AssetFileDescriptor) {

    }

    override fun setSurface(surface: Surface?) {

    }

    override fun prepareAsync() {

    }

    override fun start() {

    }

    override fun pause() {

    }

    override fun stop() {

    }

    override fun reset() {

    }

    override fun isPlaying(): Boolean {
        return false
    }

    override fun seekTo(time: Long) {

    }

    override fun release() {

    }

    override fun getCurrentPosition(): Long {
        return 0
    }

    override fun getDuration(): Long {
        return 0
    }

    override fun getBufferedPercentage(): Int {
        return 0
    }

    override fun setDisplay(holder: SurfaceHolder?) {

    }

    override fun setVolume(v1: Float, v2: Float) {

    }

    override fun setLooping(isLooping: Boolean) {

    }

    override fun setOptions() {

    }

    override fun setSpeed(speed: Float) {

    }

    override fun getSpeed(): Float {
        return 0F
    }

    override fun getTcpSpeed(): Long {
        return 0
    }
}