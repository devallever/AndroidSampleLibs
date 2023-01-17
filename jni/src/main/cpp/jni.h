//
// Created by allever on 2022/5/2.
//

#include <jni.h>
#include <android/log.h>

#ifndef JNIPROJECT_NATICE_LIB_H
#define JNIPROJECT_NATICE_LIB_H

#define JNI_DARK_EYE_SDK_TAG "Native-Jni"

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, JNI_DARK_EYE_SDK_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, JNI_DARK_EYE_SDK_TAG, __VA_ARGS__)

//全局变量
JavaVM *jvm = NULL;
jobject jCallback = NULL;
bool mNeedDetach;

JNIEnv *getEnv();

jmethodID getCallbackMethodId(JNIEnv *env, char *methodName, char *sig);

#endif //JNIPROJECT_NATICE_LIB_H
