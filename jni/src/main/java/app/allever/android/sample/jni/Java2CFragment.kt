package app.allever.android.sample.jni

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.ListViewModel
import app.allever.android.lib.common.adapter.TextAdapter
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.core.ext.log
import app.allever.android.lib.core.ext.toJson
import app.allever.android.lib.core.ext.toast


/**
 * Java调用C
 */
class Java2CFragment: ListFragment<FragmentListBinding, ListViewModel, String>() {
    override fun getAdapter() = TextAdapter()

    override fun getList() = mutableListOf(
        "Int <-> jint",
        "IntArray <-> jintArray",
        "Float <-> jfloat",
        "FloatArray <-> jfloatArray",
        "Double <-> jdouble",
        "DoubleArray <-> jdoubleArray",
        "Byte <-> jbyte",
        "ByteArray <-> jbyteArray",
        "String <-> jstring",
        "Array<String> <-> jobjectArray",
        "List<String> <-> jobject"
    )

    override fun onItemClick(position: Int, item: String) {
        when(position) {
            0 -> {
                toast("${Jni.intFromJni(position)}")
            }
            1 -> {
                val result = Jni.intArrayFromJni(intArrayOf(10, 20, 30))
                toast(result.toJson())
                result.map {
                    log("获取C的intArray: $it")
                }
            }
            2 -> {
                toast("${Jni.floatFromJni(position.toFloat())}")
            }
            3 -> {
                val result = Jni.floatArrayFromJni(floatArrayOf(5f, 6f, 7f))
                toast(result.toJson())
                result.map {
                    log("获取C的floatArray: $it")
                }
            }
            4 -> {
                toast("${Jni.doubleFromJni(position.toDouble())}")
            }
            5 -> {
                val result = Jni.doubleArrayFromJni(doubleArrayOf(8.toDouble(), 9.toDouble(), 10.toDouble()))
                toast(result.toJson())
                result.map {
                    log("获取C的doubleArray: $it")
                }
            }
            6 -> {
                toast("${Jni.byteFromJni(0.toByte())}")
            }
            7 -> {
                val result = Jni.byteArrayFromJni(byteArrayOf(0, 1, 0))
                toast(result.toJson())
                result.map {
                    log("获取C的byteArray: $it")
                }
            }
            8 -> {
                toast(Jni.stringFromJni("FromJava"))
            }
            9 -> {
                val result = Jni.stringArrayFromJni(arrayOf("A", "B", "C"))
            }
            10 -> {
                val result = Jni.stringListFromJni(listOf("A", "B", "C"))
            }
        }
    }
}