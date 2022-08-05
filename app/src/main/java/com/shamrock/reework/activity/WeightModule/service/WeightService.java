package com.shamrock.reework.activity.WeightModule.service;

import com.shamrock.reework.api.request.GraphRequest;
import com.shamrock.reework.api.request.WeightMonthsRequest;
import com.shamrock.reework.api.response.GraphResponse;
import com.shamrock.reework.api.response.WeightMonthsResponse;
import com.shamrock.reework.common.ClsHistoryRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WeightService
{

    @POST("api/HealthParams/WeightMonthsList")
    Call<WeightMonthsResponse> getWeightMonthsList(@Body WeightMonthsRequest weightMonthsRequest);

    @POST("api/HealthParams/WeightGraphData")
    Call<GraphResponse> getGraphList(@Body GraphRequest graphRequest);


    @POST("api/Report/WeightAnalysis")
    Call<GraphResponse> getWeightAnalysis(@Body ClsHistoryRequest graphRequest);

}
