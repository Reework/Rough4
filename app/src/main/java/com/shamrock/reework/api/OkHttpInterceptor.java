package com.shamrock.reework.api;


import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;


/**
 * Created by bhavdip on 25/9/17.
 */

public class OkHttpInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        Request newRequest = chain.request()
                .newBuilder()
                .header("Content-type", "application/json")
                .build();
//        .header("Content-type", "multipart/form-data")
        return chain.proceed(newRequest);

    }
}
