package app.allever.android.sample.jni.mk

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.adapter.TextDetailClickAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class BuildMkFragment : ListFragment<FragmentListBinding, BaseViewModel, TextDetailClickItem>() {
    override fun getAdapter() = TextDetailClickAdapter()

    override fun getList(): MutableList<TextDetailClickItem> =
        mutableListOf<TextDetailClickItem>().apply {
            add(TextDetailClickItem("创建c/cpp源文件", ""))
            add(TextDetailClickItem("创建Android.mk", ""))
            add(TextDetailClickItem("创建Application.mk", ""))
            add(TextDetailClickItem("ndk-build", "去到.mk目录，执行命令，编译.so库"))
            add(TextDetailClickItem("将.so库移动到libs目录", ""))
            //    sourceSets.getByName("main") {
            //        jniLibs.setSrcDirs(jniLibs.srcDirs + files("$projectDir/libs"))
            //    }
            add(
                TextDetailClickItem(
                    "打包包含.so库", "    sourceSets.getByName(\"main\") {\n" +
                            "        jniLibs.setSrcDirs(jniLibs.srcDirs + files(\"\$projectDir/libs\"))\n" +
                            "    }"
                )
            )
            add(
                TextDetailClickItem(
                    "打包自动构建", "    externalNativeBuild {\n" +
                            "        //使用Android.mk 方式，打包时自动构建\n" +
                            "        ndkBuild {\n" +
                            "            path file('src/main/jni/Android.mk')\n" +
                            "        }\n"+
 "    }")
            )
        }
}