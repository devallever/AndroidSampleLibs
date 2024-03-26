# 指定支持的ABI平台。上面所示为armeabi-v7a,
# 可选的值有all (代表全平台)、arm64-v8a、x86、x86_64，多个平台用空格隔开。
APP_ABI = armeabi-v7a arm64-v8a x86 x86_64
# 如果指定，那么NDK只会编译列出的模块列表，模块名用空格隔开，
# 如果没有指定那么NDK会编译所有的Android.mk声明的所有的LOCAL_MODULE模块
APP_MODULES = jniMk