package com.shamrock.reework.activity.InterpretationModule.service;

import com.shamrock.reework.api.request.InterpretationRequest;
import com.shamrock.reework.api.response.InterpretationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InterpretationService
{
    @POST("api/HealthParams/Interpretation")
    Call<InterpretationResponse> getInterpretationData(@Body InterpretationRequest otpRequest);
}
