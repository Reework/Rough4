package com.shamrock.reework.activity.DailyActivityModule.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FitBitClient {

    public static final int RESPONSE_CODE_OK = 200;
    public static String API_KEY = "4ce92ef30d3a2db5b382084ec380e587";

    //DWEB
//    public static final String BASE_URL = "http://shamrockreework.dweb.in/";

    //UAT
    public static final String BASE_URL = "https://api.fitbit.com/";



    static  Gson gson;
    private static Retrofit retrofit = null;

    static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
            .newBuilder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build();



    public static Retrofit getClient() {
        if (retrofit == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }


    private static Retrofit retrofitMultiPArt = null;
  /*  order id = 1054087617
    Ticket id = ZTD3D-19-927AC44725E*/
    public static Retrofit getClientMultiPart() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient  client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofitMultiPArt =  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();
        return retrofitMultiPArt;
    }
    //GitHubService service = retrofit.create(GitHubService.class);
}
