package app.allever.android.sample.jni.mk

object NdkBuild {
    init {
        //不需要lib前缀
        System.loadLibrary("jniMk")
    }

    external fun intFromCpp(): Int

    external fun stringFromCpp(): String
}