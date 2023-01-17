#include <jni.h>
#include <string>
#include "jni.h"

//extern "C"
//JNIEXPORT jstring JNICALL
//Java_app_allever_android_learning_jni_Jni_stringFromJNI(JNIEnv *env, jobject thiz) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}
//
////基本类型
//extern "C"
//JNIEXPORT void JNICALL
//Java_app_allever_android_learning_jni_Jni_jniParams(JNIEnv *env, jobject thiz, jint int_params,
//                                                    jfloat float_params, jdouble double_params,
//                                                    jlong long_params, jboolean boolean_params,
//                                                    jstring string_params) {
//
//    //获取参数
//    LOGD("int_params = %d\n", int_params);
//    LOGD("float_params = %f\n", float_params);
//    LOGD("double_params = %lf\n", double_params);
//    LOGD("long_params = %lld\n", long_params);
//    char *stringParams = const_cast<char *>(env->GetStringUTFChars(string_params, nullptr));
//    LOGD("boolean_params = %d\n", boolean_params);
//    LOGD("string_params = %s\n", stringParams);
//
//    //回调onSuccess
//    JNIEnv *jniEnv = getEnv();
//    if (jniEnv == NULL) {
//        return;
//    }
//    //获取要回调的方法ID
//    char *methodName = "onSuccess";
//    char *signature = "()V";
//    //获取要回调的方法ID
//    jmethodID javaCallbackMethodId = getCallbackMethodId(jniEnv, methodName, signature);
//    if (javaCallbackMethodId == NULL) {
//        LOGE("找不到方法: %s\n", methodName);
//        return;
//    }
//    //执行回调
//    jniEnv->CallVoidMethod(jCallback, javaCallbackMethodId);
//    javaCallbackMethodId = NULL;
//
//    //回调onFailed
//    //获取要回调的方法ID
//    methodName = "onFailed";
//    signature = "(ILjava/lang/String;)V";
//    //获取要回调的方法ID
//    javaCallbackMethodId = getCallbackMethodId(jniEnv, methodName, signature);
//    if (javaCallbackMethodId == NULL) {
//        LOGE("找不到方法: %s\n", methodName);
//        return;
//    }
//    //执行回调
//    jstring message = env->NewStringUTF("FAILED");
//    jniEnv->CallVoidMethod(jCallback, javaCallbackMethodId, 0, message);
//
//    //回调paramsCallback
//    //获取要回调的方法ID
//    /*
//     * intParams: Int,
//       floatParams: Float,
//       doubleParams: Double,
//       longParams: Long,
//       booleanParams: Boolean,
//        stringParams: String
//     */
//    methodName = "paramsCallback";
//    signature = "(IFDJZLjava/lang/String;)V";
//    //获取要回调的方法ID
//    javaCallbackMethodId = getCallbackMethodId(jniEnv, methodName, signature);
//    if (javaCallbackMethodId == NULL) {
//        LOGE("找不到方法: %s\n", methodName);
//        return;
//    }
//    //执行回调
//    jstring stringParamsCallback = env->NewStringUTF("stringParamsCallback");
//    jniEnv->CallVoidMethod(jCallback, javaCallbackMethodId,
//                           int_params+1,
//                           float_params+1,
//                           double_params+1,
//                           long_params+1,
//                           !boolean_params,
//                           stringParamsCallback);
//
//    //释放当前线程
//    if (mNeedDetach) {
//        jvm->DetachCurrentThread();
//    }
//    jniEnv = NULL;
//
//}
//
//extern "C"
//JNIEXPORT void JNICALL
//Java_app_allever_android_learning_jni_Jni_jniObjectParams(JNIEnv *env, jobject thiz, jobject user) {
//
//}
//
///***
// * 设置jni回调java层
// */
//extern "C"
//JNIEXPORT void JNICALL
//Java_app_allever_android_learning_jni_Jni_setCallback(JNIEnv *env, jobject thiz,
//                                                      jobject jni_callback) {
//    LOGD("native setCallback");
//    if (jvm == nullptr) {
//        env->GetJavaVM(&jvm);
//    }
//
//    if (jCallback == nullptr) {
//        jCallback = env->NewGlobalRef(jni_callback);
//    }
//}
//
//JNIEnv *getEnv() {
//    mNeedDetach = false;
//    JNIEnv *env = nullptr;
//    //获取当前native线程是否有没有被附加到jvm环境中
//    int getEnvStat = jvm->GetEnv((void **) (&env), JNI_VERSION_1_6);
//    if (getEnvStat == JNI_EDETACHED) {
//        //如果没有， 主动附加到jvm环境中，获取到env
//        if (jvm->AttachCurrentThread(&env, nullptr) != 0) {
//            LOGE("动附加到jvm环境中失败");
//            return env;
//        }
//        mNeedDetach = JNI_TRUE;
//    }
//
//    //通过全局变量g_obj 获取到要回调的类
//    if (env == nullptr) {
//        LOGE("获取不到 env");
//        jvm->DetachCurrentThread();
//        return env;
//    }
//
//    return env;
//}
//
//jmethodID getCallbackMethodId(JNIEnv *env, char *methodName, char *sig) {
//    jmethodID javaCallbackMethodId = nullptr;
//    jclass javaClass = env->GetObjectClass(jCallback);
//    if (javaClass == nullptr) {
//        LOGE("Unable to find class");
//        jvm->DetachCurrentThread();
//        return javaCallbackMethodId;
//    }
//
//    //获取要回调的方法ID
//    javaCallbackMethodId = env->GetMethodID(javaClass, methodName, sig);
//    if (javaCallbackMethodId == nullptr) {
//        LOGE("Unable to find method: %s\n", methodName);
//        return javaCallbackMethodId;
//    }
//    env->DeleteLocalRef(javaClass);
//    return javaCallbackMethodId;
//}
//
//extern "C"
//JNIEXPORT jintArray JNICALL
//Java_app_allever_android_learning_jni_Jni_jniArrayParams(JNIEnv *env, jobject thiz,
//                                                         jintArray int_array,
//                                                         jfloatArray float_array,
//                                                         jdoubleArray double_array,
//                                                         jlongArray long_array,
//                                                         jobjectArray string_array) {
//
//
//    //jni中获取array长度需要使用JNIEnv对象方法GetArrayLength(env, array)来获取
//    int arraySize = env->GetArrayLength(int_array);
//    LOGD("ArraySize = %d\n", arraySize);
//    int cIntArray[] = {};
//    env->GetIntArrayRegion(int_array, 0, arraySize, cIntArray);
//    for (int i = 0; i < arraySize; i++) {
//        LOGD("int array %d = %d\n", i, cIntArray[i]);
//    }
//
////    int stringArraySize = env->GetArrayLength(string_array);
////    for (int i = 0; i < stringArraySizek; ++i) {
////        jstring stringObj = (jstring)(env->GetObjectArrayElement(string_array, stringArraySize));
////        jchar *stringObjChar = nullptr;
////        env->GetStringRegion(stringObj, 0, env->GetStringLength(stringObj), stringObjChar);
////        LOGD("stringObj = %hu\n", *stringObjChar);
////    }
//
//
//    int newIntArray[] = {10, 9, 8, 7, 6, 5, 4, 3};
//    //创建jintArray对象
//    //设置值
//    jintArray newJIntArray = env->NewIntArray(8);
//    env->SetIntArrayRegion(newJIntArray, 0, 8, newIntArray);
//    return newJIntArray;
//}
//extern "C"
//JNIEXPORT void JNICALL
//Java_app_allever_android_learning_jni_Jni_jniList(JNIEnv *env, jobject thiz, jobject int_list) {
//    int arraySize = sizeof(int_list);
//    LOGD("ArraySize = %d\n", arraySize);
//}
extern "C"
JNIEXPORT jstring JNICALL
Java_app_allever_android_sample_jni_Jni_stringFromJni(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}