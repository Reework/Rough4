package com.shamrock.reework.activity.RegistrationModule.service;

import com.shamrock.reework.activity.newregistrationmodule.newregistration.ClsNewRegRequest;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.ClsRegQuenPojo;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.JoinNew;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.RegistrationRespo;
import com.shamrock.reework.api.request.ProfileDataRequest;
import com.shamrock.reework.api.request.RegistrationRequest;
import com.shamrock.reework.api.request.SubscriptionRequest;
import com.shamrock.reework.api.request.UpdateProfileRequest;
import com.shamrock.reework.api.response.MoviesResponse;
import com.shamrock.reework.api.response.ProfileDataResponse;
import com.shamrock.reework.api.response.RegistrationResponse;
import com.shamrock.reework.api.response.SubscriptionResponse;
import com.shamrock.reework.api.response.UpdateProfileResponse;
import com.shamrock.reework.payment.ClsMerchantRequest;
import com.shamrock.reework.payment.MerhantDataResponse;
import com.shamrock.reework.payment.paymentDetails;
import com.shamrock.reework.razerpaypsyment.CreatePaymentResonse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

    public interface RegistrationService
{

//    http://shamrockuat.dweb.in/api/SubscriptionMaster/PaymentDetails


    @POST("api/SubscriptionMaster/PaymentDetails")
    Call<MerhantDataResponse> getMerchantData(@Body ClsMerchantRequest registrationRequest);

    @POST("api/Registration/Registration")
    Call<RegistrationResponse> setRegistration(@Body RegistrationRequest registrationRequest);

    @POST("api/Registration/Registration")
    Call<RegistrationRespo> setRegistrationadd(@Body RegistrationRequest registrationRequest);


    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @POST("api/Registration/UserprofileUpdate")
    Call<UpdateProfileResponse> setUpdateProfileData(@Body UpdateProfileRequest updateProfileRequest);

    @POST("api/Registration/ReworkerProfileData")
    Call<ProfileDataResponse> getProfileData(@Body ProfileDataRequest request);

    @POST("api/SubscriptionMaster/SubcribtionPlan")
    Call<SubscriptionResponse> subResponse(@Body SubscriptionRequest subscriptionRequest);

    @GET("api/Master/JoinSteps")
    Call<JoinNew> getJoinNewSteps();


    @GET("api/Master/UserQ1Master")
    Call<ClsRegQuenPojo> getUserQ1Master();

//    http://localhost:62097/Payment/PaymentRequestByReeworkId?ReeworkerId=3040&Mode=Online&SubscriptionPlanId=1&SubPlanId={optional}

    @GET("Payment/PaymentRequestByReeworkId")
    Call<CreatePaymentResonse> createPayment(@Query("ReeworkerId") int ReeworkerId, @Query("Mode") String Mode, @Query("SubscriptionPlanId") int SubscriptionPlanId, @Query("SubPlanId") String SubPlanId, @Query("CouponCode") String CouponCode);

    @GET("Payment/GetReeworkerPayments")
    Call<List<paymentDetails>> getPaymentHistory(@Query("UserId") int ReeworkerId);
}
