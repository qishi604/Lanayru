//package com.lanayru.decode;
//
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import com.bumptech.glide.load.Options;
//import com.bumptech.glide.load.ResourceDecoder;
//import com.bumptech.glide.load.engine.Resource;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import pl.droidsonroids.gif.GifDrawable;
//
///**
// * @author 郑齐
// * @version V1.0
// * @since 2019-06-29
// */
//public class FixGifDecoder implements ResourceDecoder<InputStream, GifDrawable> {
//
//
//    @Override
//    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Resource<GifDrawable> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
//        GifDrawable drawable = new GifDrawable(source);
//        return new FixDrawableResource(drawable);
//    }
//}
