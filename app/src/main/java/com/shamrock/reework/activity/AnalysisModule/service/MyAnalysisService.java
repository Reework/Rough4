package com.shamrock.reework.activity.AnalysisModule.service;

import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ClsCalorianalysis;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsScheduleData;
import com.shamrock.reework.activity.HealthSupportModule.modal.HealthSupportClassMain;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.BcaResponse;
import com.shamrock.reework.api.response.BloodReportResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyAnalysisService
{
    @POST("api/BCAReScore/Get_BCAReScoreList")
    Call<BcaResponse> getBcaReport(@Body BcaRequest bcaRequest);


    @POST("api/UserBloodTestSchedule/Get_BCAReportDetails")
    Call<BCAResponce> getBCAReport(@Body BcaRequest bcaRequest);





    @GET("api/UserBloodTestSchedule/GetTestReport")
    Call<BCAResponce> getMyBCAHistory(@Query("UserId") String id);


    @POST("api/BRReescore/Get_ReescoreList")
    Call<BloodReportResponse> getBloodReport(@Body BcaRequest bcaRequest);

//http://localhost:62096/api/Master/GetHelpSupportDetails
    @GET("api/Master/GetHelpSupportDetails")
    Call<HealthSupportClassMain> getHealthSupportData();


}
