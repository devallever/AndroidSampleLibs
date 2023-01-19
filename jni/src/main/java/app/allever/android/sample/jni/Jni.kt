package app.allever.android.sample.jni

object Jni {

    init {
        System.loadLibrary("jni")
    }

    external fun stringFromJni(): String

    external fun intFromJni(intValue: Int): Int

    external fun intArrayFromJni(intArray: IntArray): IntArray

    external fun floatFromJni(float: Float): Float

    external fun floatArrayFromJni(floatArray: FloatArray): FloatArray


}