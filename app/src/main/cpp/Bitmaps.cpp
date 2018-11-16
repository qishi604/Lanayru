//
// Created by seven on 2018/9/26.
//

#include <jni.h>
#include <android/bitmap.h>
#include <android/log.h>
#include <iostream>
#include <stdarg.h>
#include <time.h>

#define TAG "cpp"

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG ,__VA_ARGS__) // 定义LOGI类型

long millis() {

//    time_t last;
//    time(&last); // 得到的是秒

//    return std::chrono::system_clock::now().time_since_epoch().count();
    timeval now;
    gettimeofday(&now, NULL);

    return now.tv_sec * 1000 + now.tv_usec / 1000;
}

void toRed(const int *src, int *dst, int w, int h) {
    for (int c = 0; c < h; ++c) {
        for (int r = 0; r < w; ++r) {
            int index = w * c + r;

            int color = src[index];

//            int alpha = color & 0xFF000000;
//
//            int red = (color & 0x00FF0000);
//            int green = (color & 0x0000FF00);
//            int blue = color & 0x000000FF;

            // 红色通道是最后8位
            dst[index] = color & 0xFF0000FF;
        }
    }
}

void toGray(const int *src, int *dst, int w, int h) {

    for (int c = 0; c < h; ++c) {
        for (int r = 0; r < w; ++r) {
            int index = w * c + r;

            int color = src[index];

            int alpha = color & 0xFF000000;
            int blue = (color & 0x00FF0000) >> 16;
            int green = (color & 0x0000FF00) >> 8;
            int red = color & 0x000000FF;

            color = red * 299 / 1000 + green * 587 / 1000 + blue * 114 / 1000;

            color = alpha | (color << 16) | color << 8 | color;

            dst[index] = color;
        }
    }
}


extern "C"
JNIEXPORT void JNICALL
Java_com_lanayru_app_ui_jni_Bitmaps_toRed(JNIEnv *env, jobject, jobject src, jobject dst) {
    long last = millis();

    AndroidBitmapInfo info;

    if (AndroidBitmap_getInfo(env, src, &info) != ANDROID_BITMAP_RESULT_SUCCESS) {
        LOGI("get info error");
        return ;
    }

    void *srcaddr, *dstaddr;
    if (AndroidBitmap_lockPixels(env, src, &srcaddr) != ANDROID_BITMAP_RESULT_SUCCESS
        || AndroidBitmap_lockPixels(env, dst, &dstaddr) != ANDROID_BITMAP_RESULT_SUCCESS
            ) {
        LOGI("lock error");
        return ;
    }

    // todo
    int w = info.width;
    int h = info.height;
    int *srcpixs = static_cast<int *>(srcaddr);
    int *dstpixs = static_cast<int *>(dstaddr);

    toRed(srcpixs, dstpixs, w, h);

    AndroidBitmap_unlockPixels(env, src);
    AndroidBitmap_unlockPixels(env, dst);

    long now = millis();
    long duration = now - last;

    LOGI("last: %ld, now: %ld, duration: %ld", last, now, duration);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_lanayru_app_ui_jni_Bitmaps_toGray(JNIEnv *env, jobject, jobject src, jobject dst) {
    using namespace std;

    long last = millis();

    AndroidBitmapInfo info;

    if (AndroidBitmap_getInfo(env, src, &info) != ANDROID_BITMAP_RESULT_SUCCESS) {
        cout << "get info error";
        return ;
    }

    void *srcaddr, *dstaddr;
    if (AndroidBitmap_lockPixels(env, src, &srcaddr) != ANDROID_BITMAP_RESULT_SUCCESS
    || AndroidBitmap_lockPixels(env, dst, &dstaddr) != ANDROID_BITMAP_RESULT_SUCCESS
    ) {
        cout << "lock error";
        return ;
    }

    // todo
    int w = info.width;
    int h = info.height;
    int *srcpixs = static_cast<int *>(srcaddr);
    int *dstpixs = static_cast<int *>(dstaddr);

//    int alpha = 0xFF00000000;
//
//    for (int c = 0; c < h; ++c) {
//        for (int r = 0; r < w; ++r) {
//            int index = w * c + r;
//
//            int color = srcpixs[index];
//
//            int red = (color & 0x00FF0000) >> 16;
//            int green = (color & 0x0000FF00) >> 8;
//            int blue = color & 0x000000FF;
//
////            color = (red + green + blue) / 3;
////            color = red;
////            color = green;
//            color = red;
//
//
//            color = alpha | (color << 16) | color << 8 | color;
//
//            dstpixs[index] = color;
//        }
//    }
    toGray(srcpixs, dstpixs, w, h);
//    toRed(srcpixs, dstpixs, w, h);


    AndroidBitmap_unlockPixels(env, src);
    AndroidBitmap_unlockPixels(env, dst);

    long now = millis();
    long duration = now - last;

    LOGI("last: %ld, now: %ld, duration: %ld", last, now, duration);
}