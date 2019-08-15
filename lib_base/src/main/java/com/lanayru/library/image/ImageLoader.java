package com.lanayru.library.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 *
 * Image Loader
 *
 * <pre>
 *  <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javadoc.html">javadoc</a>
 *  <a href="https://www.oracle.com/technetwork/articles/javase/index-137868.html">How to Write Doc Comments for the Javadoc Tool</a>
 *</pre>
 * @author 郑齐
 * @version V1.0
 * @since 2019-07-04
 *
 */
public class ImageLoader {

    /**
     * Load bitmap or drawable in to ImageView
     *
     * @param iv ImageView
     * @param url Bitmap url
     *
     * @see GlideApp
     */
    public static void load(ImageView iv, String url) {
//        GlideApp.with(iv).load(url).into(iv);
        Glide.with(iv).load(url).into(iv);

    }

    public static void load(ImageView iv, String url, int placeholder) {
        GlideApp.with(iv).load(url).placeholder(placeholder).into(iv);
    }

    public static void load(ImageView iv, String url, RequestOptions options) {
        GlideApp.with(iv).load(url).apply(options).into(iv);
    }

    public static void test(ImageView iv, String url) {
        GlideApp.with(iv)
                .asBitmap()
                .load(url)
                .transition(BitmapTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        Glide.with(iv).clear(iv);
    }
}
