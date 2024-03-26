package app.allever.android.sample.jni.mk

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.adapter.TextDetailClickAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.mvvm.base.BaseViewModel
import com.chad.library.adapter.base.BaseQuickAdapter

class ApplicationMkFragment :
    ListFragment<FragmentListBinding, BaseViewModel, TextDetailClickItem>() {
    override fun getAdapter() = TextDetailClickAdapter()

    override fun getList(): MutableList<TextDetailClickItem> =
        mutableListOf<TextDetailClickItem>().apply {
            add(
                TextDetailClickItem(
                    "APP_ABI = armeabi-v7a", "# 指定支持的ABI平台。上面所示为armeabi-v7a,\n" +
                            "# 可选的值有all (代表全平台)、arm64-v8a、x86、x86_64，多个平台用空格隔开。"
                )
            )
            add(
                TextDetailClickItem(
                    "APP_MODULES = jniMk",
                    "# 如果指定，那么NDK只会编译列出的模块列表，模块名用空格隔开，\n" +
                            "# 如果没有指定那么NDK会编译所有的Android.mk声明的所有的LOCAL_MODULE模块"
                )
            )
        }
}