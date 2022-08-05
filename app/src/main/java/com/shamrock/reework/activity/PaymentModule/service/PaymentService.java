package com.shamrock.reework.activity.PaymentModule.service;

import com.shamrock.reework.activity.PaymentModule.model.request.CheckSumRequest;
import com.shamrock.reework.activity.PaymentModule.model.request.CheckSumRequestForPO;
import com.shamrock.reework.activity.PaymentModule.model.request.PaymentVerify_SaveRequest;
import com.shamrock.reework.activity.PaymentModule.model.responce.GetCheckSumForPOResponce;
import com.shamrock.reework.activity.PaymentModule.model.responce.GetCheckSumResponce;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PaymentService
{

    @POST("api/Payment/calculateChecksum")
    Call<GetCheckSumResponce> getCheckSum(@Body CheckSumRequest request);

    @POST("api/Payment/verifyAndSave_Response")
    Call<ReecoachDetailsResponse> verify_savePayment(@Body PaymentVerify_SaveRequest request);

    @POST("api/Payment/check_TransactionStatus")
    Call<GetCheckSumForPOResponce> getCheckSumForPO(@Body CheckSumRequestForPO request);



}
