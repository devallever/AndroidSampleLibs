#include <jni.h>

//
// Created by allever on 2024/3/26.
//


int getIntFromC() {
    return 110;
}

JNIEXPORT jint JNICALL
Java_app_allever_android_sample_jni_mk_NdkBuild_intFromCpp(JNIEnv *env, jclass clazz) {
    return getIntFromC();
}