package app.allever.android.sample.jni

import app.allever.android.lib.common.ListFragment
import app.allever.android.lib.common.adapter.TextDetailClickAdapter
import app.allever.android.lib.common.adapter.bean.TextDetailClickItem
import app.allever.android.lib.common.databinding.FragmentListBinding
import app.allever.android.lib.mvvm.base.BaseViewModel

class CMakeFragment : ListFragment<FragmentListBinding, BaseViewModel, TextDetailClickItem>() {
    override fun getAdapter() = TextDetailClickAdapter()

    override fun getList(): MutableList<TextDetailClickItem> =
        mutableListOf<TextDetailClickItem>().apply {
            add(
                TextDetailClickItem(
                    "add_library()", "# 参数一：库名称\n" +
                            "# 参数二：SHARE, 编译动态库 .so\n" +
                            "# 参数三：c/c++源文件\n" +
                            "add_library(\n" +
                            "        # Sets the name of the library.\n" +
                            "        DarkEyeSDK\n" +
                            "        \n" +
                            "        # Sets the library as a shared library.\n" +
                            "        SHARED\n" +
                            "\n" +
                            "        # Provides a relative path to your source file(s).\n" +
                            "        DarkEyeSDK.cpp\n" +
                            "        #\${CMAKE_SOURCE_DIR}/include/darkeye/MyDemo.cpp\n" +
                            "        #Sample.cpp\n" +
                            "        )"
                )
            )
            add(
                TextDetailClickItem(
                    "find_library()", "# 查找ndk预构建的库\n" +
                            "find_library( # Sets the name of the path variable.\n" +
                            "        log-lib\n" +
                            "\n" +
                            "        # Specifies the name of the NDK library that\n" +
                            "        # you want CMake to locate.\n" +
                            "        log)"
                )
            )
            add(
                TextDetailClickItem(
                    "target_link_libraries()", "# 设置需要链接其他so库\n" +
                            "target_link_libraries(\n" +
                            "        # Specifies the target library.\n" +
                            "        DarkEyeSDK\n" +
                            "\n" +
                            "        # Links the target library to the log library\n" +
                            "        # included in the NDK.\n" +
                            "        jni-test\n" +
                            "        DarkEye\n" +
                            "        FitnessEvaluator\n" +
                            "        DarkEye.Gesture\n" +
                            "        \${log-lib})"
                )
            )
            add(
                TextDetailClickItem(
                    "引入其他.so库和头文件",
                    "# 当需要用到其他库的头文件(接口编程)，就这样引入，否则只需要添加.so并在打包时候包含进去就可以了\n" +
                            "# libDarkEye.so\n" +
                            "add_library(DarkEye STATIC IMPORTED)\n\n" +
                            "# 设置libDarkEye.so 路径\n" +
                            "set_target_properties(DarkEye PROPERTIES IMPORTED_LOCATION \${CMAKE_SOURCE_DIR}/../../../darkeyelib/\${ANDROID_ABI}/libDarkEye.so)\n\n" +
                            "# 设置目标库引用libDarkEye.so 的头文件\n" +
                            "target_include_directories(DarkEyeSDK PRIVATE \${CMAKE_SOURCE_DIR}/include/darkeye)"
                )
            )
        }
}