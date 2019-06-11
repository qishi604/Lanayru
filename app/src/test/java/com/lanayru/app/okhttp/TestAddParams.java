package com.lanayru.app.okhttp;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/5/20
 */
public class TestAddParams {

    /**
     * @param parameter - this is string that contains parameters for Http POST
     * @param request - old Request
     * @return - new {@link Request} with additional parameters
     * */
    public static Request interceptRequest(@NotNull Request request, @NotNull String parameter)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Sink sink = Okio.sink(baos);
        BufferedSink bufferedSink = Okio.buffer(sink);

        /**
         * Write old params
         * */
        request.body().writeTo(bufferedSink);

        /**
         * write to buffer additional params
         * */
        bufferedSink.writeString(parameter, Charset.defaultCharset());

        RequestBody newRequestBody = RequestBody.create(
                request.body().contentType(),
                bufferedSink.buffer().readUtf8()
        );

        return request.newBuilder().post(newRequestBody).build();
    }

    public static Request interceptForm(@NotNull Request request)
            throws IOException {

        RequestBody body = request.body();
        FormBody.Builder builder = new FormBody.Builder()
                .add("q", "google");

        if (body instanceof FormBody) {
            final FormBody old = (FormBody) body;
            for (int i = 0, N = old.size(); i < N; i++) {
                builder.add(old.encodedName(i), old.encodedValue(i));
            }
        }

        return  request.newBuilder()
                .post(builder.build())
                .build();
    }

    public Interceptor getNetInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request request = chain.request();

                final Request request1 = interceptForm(request);
                return chain.proceed(request1);
            }
        };

        return interceptor;
    }

    @Test
    public void testAddPostParams() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        final Interceptor netInterceptor = getNetInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addNetworkInterceptor(netInterceptor)
                .addInterceptor(netInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


        FormBody body = new FormBody.Builder()
                .add("version", "1.0")
                .build();

        Request request = new Request.Builder()
                .url("http://www.saicmotor.com")
                .method("POST", body)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
