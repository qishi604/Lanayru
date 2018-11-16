//
// Created by seven on 2018/9/25.
//

#include <jni.h>
#include <string>
#include "Util.h"
#include <android/bitmap.h>

extern "C" JNIEXPORT jint JNICALL
Java_com_lanayru_app_ui_jni_Jnis_max(
        JNIEnv *env,
        jclass,
        jint a,
        jint b) {
    return MAX(a, b);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_lanayru_app_ui_jni_Jnis_getBitmapInfo(JNIEnv *env, jclass, jobject bm) {
    using namespace std;

    // TODO
    AndroidBitmapInfo *info = new AndroidBitmapInfo;
    AndroidBitmap_getInfo(env, bm, info);
    string str = "format: " + to_string(info->format) + " stride: " + to_string(info->stride) + "width: " + to_string(info->width) + " height: " + to_string(info->height);

    delete info;
    return env->NewStringUTF(str.c_str());
}