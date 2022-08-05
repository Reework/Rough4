package com.shamrock.reework.activity.UnfreezeModule.service;

import com.shamrock.reework.activity.ApplicationSnippetModule.ClsSnippetPojo;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodUnitMasterPojo;
import com.shamrock.reework.api.request.UnfreezeRequest;
import com.shamrock.reework.api.response.UnfreezeResponse;
import com.shamrock.reework.api.response.UserStatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UnfreezeService
{
    @POST("api/Login/RequestToUnfreeze")
    Call<UnfreezeResponse> unfreezRequest(@Body UnfreezeRequest request);

    @POST("api/Login/RequestToCheckUserStatus")
    Call<UserStatusResponse> getUserStatus(@Header("token") String header, @Body UnfreezeRequest request);

//    http://shamrockuat.dweb.in/api/Master/SnippetMessage

    @GET("api/Master/SnippetMessage")
    Call<ClsSnippetPojo> getSnippetMessage();
}
