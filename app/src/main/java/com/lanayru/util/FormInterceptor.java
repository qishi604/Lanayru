package com.lanayru.util;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/5/20
 */
public class FormInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();

        FormBody.Builder builder = new FormBody.Builder();
        final RequestBody oldBody = originalRequest.body();
        if (oldBody instanceof FormBody) {
            final FormBody formBody = (FormBody) oldBody;
            for (int i = 0, N = formBody.size(); i < N; i++) {
                builder.add(formBody.encodedName(i), formBody.encodedValue(i));
            }
        }

        final Request newRequest = originalRequest.newBuilder()
                .post(builder.build())
                .build()
                ;

        return chain.proceed(newRequest);
    }
}
