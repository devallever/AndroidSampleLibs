package app.allever.android.sample.jni

object Jni {

    init {
        System.loadLibrary("jni")
    }

    external fun stringFromJni(): String


}