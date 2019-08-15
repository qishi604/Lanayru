//package com.lanayru.decode;
//
//import android.support.annotation.NonNull;
//
//import com.bumptech.glide.load.resource.drawable.DrawableResource;
//
//import pl.droidsonroids.gif.GifDrawable;
//
///**
// * @author 郑齐
// * @version V1.0
// * @since 2019-06-29
// */
//public class FixDrawableResource extends DrawableResource<GifDrawable> {
//
//    public FixDrawableResource(GifDrawable drawable) {
//        super(drawable);
//    }
//
//    @NonNull
//    @Override
//    public Class<GifDrawable> getResourceClass() {
//        return GifDrawable.class;
//    }
//
//    @Override
//    public int getSize() {
//        return drawable.getNumberOfFrames();
//    }
//
//    @Override
//    public void recycle() {
//        drawable.recycle();
//    }
//}
