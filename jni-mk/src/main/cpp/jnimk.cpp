//
// Created by allever on 2024/3/27.
//

#include "jnimk.h"
#include <jni.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_app_allever_android_sample_jni_mk_NdkBuild_stringFromCpp(JNIEnv *env, jobject thiz) {
    //C语法
    char *hello2 = "String from Cpp";
    //创建jstring
    jstring result = env->NewStringUTF(hello2);
    return result;
}