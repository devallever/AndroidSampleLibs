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
        "FloatArray <-> jfloatArray"
    )

    override fun onItemClick(position: Int, item: String) {
        when(position) {
            0 -> {
                toast("${Jni.intFromJni(3)}")
            }
            1 -> {
                val result = Jni.intArrayFromJni(intArrayOf(10, 20, 30))
                toast(result.toJson())
                result.map {
                    log("获取C的intArray: $it")
                }
            }
            2 -> {
                toast("${Jni.intFromJni(5)}")
            }
            3 -> {
                val result = Jni.floatArrayFromJni(floatArrayOf(5f, 6f, 7f))
                toast(result.toJson())
                result.map {
                    log("获取C的floatArray: $it")
                }
            }
        }
    }
}