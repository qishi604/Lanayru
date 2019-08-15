package com.lanayru.library.image;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 *
 * <pre>
 *     如果你的应用拥有多个 GlideModule，你需要把其中一个转换成 AppGlideModule，剩下的转换成 LibraryGlideModule 。
 *     除非存在AppGlideModule ，否则程序不会发现 LibraryGlideModule ，因此您不能仅使用 LibraryGlideModule 。
 *     See <a href="https://muyangmin.github.io/glide-docs-cn/doc/migrating.html#%E9%85%8D%E7%BD%AE">Glide configuration</a>
 * </pre>
 *
 * @author 郑齐
 * @version V1.0
 * @since 2019-07-04
 *
 */
@GlideModule
public class LGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
//        registry.append(Registry.BUCKET_GIF, InputStream.class, GifDrawable.class, new FixGifDecoder());
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));
    }
}
