package com.shamrock.reework.activity.RescoreModule.service;

import com.shamrock.reework.activity.FoodModule.model.foodtripuser.ClsUSerFoodTripmain;
import com.shamrock.reework.activity.RescoreModule.model.ClsReescoreMianClass;
import com.shamrock.reework.activity.aNewInterpretation.model.ClsReescoreMain;
import com.shamrock.reework.api.request.RescoreRequest;
import com.shamrock.reework.api.response.RescoreResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RescoreService
{
    @POST("api/HealthParams/GetInterpretation")
    Call<RescoreResponse> GetRescoreData(@Body RescoreRequest request);


    @GET("api/Master/ReeScoreMessage")
    Call<ClsReescoreMianClass> GetReeScoreMessage();


//    http://shamrockuat.dweb.in/api/Master/GetInterpretationByReeWorkerID?ReeworkerId=3040
//@GET("api/Master/GetInterpretationByReeWorkerID")
@GET("api/Master/GetCurrentReescore")
Call<ClsReescoreMain> GetInterpretationByReeWorkerIDAPI(@Query("ReeworkerId") int id,@Query("CreatedOn") String date);
}
