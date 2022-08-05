package com.shamrock.reework.activity.SubscriptionModule.service;

import com.shamrock.reework.api.request.CityRequest;
import com.shamrock.reework.api.response.CityResponse;
import com.shamrock.reework.api.response.SubscriptionFeaturesResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SubscriptionService
{
    @GET("api/SubscriptionMaster/GetSubsFeatures")
    Call<SubscriptionFeaturesResponse> getSubscriptiobFeaturesList();

    @POST("api/")
    Call<CityResponse> getCityList(@Body CityRequest cityRequest);

}
