package app.allever.android.sample.jni.mk

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.adapter.TextDetailClickAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class AndroidMkFragment : ListFragment<FragmentListBinding, BaseViewModel, TextDetailClickItem>() {
    override fun getAdapter() = TextDetailClickAdapter()

    override fun getList(): MutableList<TextDetailClickItem> =
        mutableListOf<TextDetailClickItem>().apply {
            add(TextDetailClickItem("getIntFromC()", "int form c ${NdkBuild.intFromCpp()}"))
            add(TextDetailClickItem("getStringFromC()", NdkBuild.stringFromCpp()))
            add(
                TextDetailClickItem(
                    "LOCAL_PATH := \$(call my-dir)",
                    "# 必须位于Android.mk文件的开始，用来定义源文件的位置，\$(call my-dir)返回当前路径，\n" +
                            "# 即Android.mk所在的目录。my-dir是构建系统提供的宏函数，它将会返回当前目录的路径；"
                )
            )
            add(
                TextDetailClickItem(
                    "include \$(CLEAR_VARS)",
                    "# 清除除LOCAL_PATH之外的所有LOCAL_XXX变量。这个清理动作是必须的，因为所有的编译控制文件都是由同一个GNU Make解析和执行，\n" +
                            "# 其变量是全局的，只有清理后才能避免相互影响"
                )
            )
            add(
                TextDetailClickItem(
                    "LOCAL_MODULE    := jniMk",
                    "# 表示Android.mk所在模块的模块名，名字必须唯一且不包含空格。\n" +
                            "# 构建系统在生成最终的so库文件时，会参考该模块名生成最终的so库，如：libjniMk.so；"
                )
            )
            add(
                TextDetailClickItem(
                    "LOCAL_SRC_FILES := hello-jni.c",
                    "# 译该模块时所需的C/C++源文件，如果有多个文件需要用空格分离，如果想换行则需要在每个源文件末尾加上反斜杠\"\\\" ,类似于C语言中的多行宏定义；"
                )
            )
            add(
                TextDetailClickItem(
                    "include \$(BUILD_SHARED_LIBRARY)", "# 确定要构建的内容及其操作方法。\n" +
                            "# BUILD_SHARED_LIBRARY  表示要编译为动态库，构建系统会生成后缀名为.so的库文件；\n" +
                            "# BUILD_STATIC_LIBRARY 表示要编译为静态库，构建系统会生成后缀名为.a 的为文件。"
                )
            )

        }
}