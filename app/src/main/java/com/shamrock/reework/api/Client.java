package com.shamrock.reework.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final int RESPONSE_CODE_OK = 200;
    public static String API_KEY = "4ce92ef30d3a2db5b382084ec380e587";

    //DWEB
//   public static final String BASE_URL = "https://reework.in/";
    public static final String BASE_URL = "https://reeworkmindbody.co.in/";


    //UAT
//    public static final String BASE_URL = "https://reeplan.co.in/";
//    public static f/inal String BASE_URL = "http://shamrockuat.dweb.in/";

    private static Retrofit retrofit = null;

    static OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new OkHttpInterceptor())
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(80, TimeUnit.SECONDS)
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    private static Retrofit retrofitMultiPArt = null;

    public static Retrofit getClientMultiPart() {

        retrofitMultiPArt = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();
        return retrofitMultiPArt;
    }
    //GitHubService service = retrofit.create(GitHubService.class);
}
