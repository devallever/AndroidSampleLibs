package app.allever.android.sample.jni

/**
 * Android JNI基础篇（一）
 * https://blog.csdn.net/kgdwbb/article/details/72810251
 *
 * 基础JNI语法和常见使用
 * https://blog.csdn.net/xuexiangjys/article/details/83115117
 */
object Jni {

    init {
        System.loadLibrary("jni")
    }

    external fun intFromJni(intValue: Int): Int

    external fun intArrayFromJni(intArray: IntArray): IntArray

    external fun floatFromJni(float: Float): Float

    external fun floatArrayFromJni(floatArray: FloatArray): FloatArray

    external fun doubleFromJni(double: Double): Double

    external fun doubleArrayFromJni(doubleArray: DoubleArray): DoubleArray

    external fun byteFromJni(byte: Byte): Byte

    external fun byteArrayFromJni(byteArray: ByteArray): ByteArray

    external fun stringFromJni(string: String): String

    external fun stringArrayFromJni(stringArray: Array<String>): Array<String>

    external fun stringListFromJni(stringList: List<String>): List<String>


}