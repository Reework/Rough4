package com.shamrock.reework.activity.LoginModule;

import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ClsCalorianalysis;
import com.shamrock.reework.activity.HomeModule.lockumlock.ClsLockUnlockMain;
import com.shamrock.reework.activity.changepassword.ChangeSucess;
import com.shamrock.reework.activity.changepassword.ChangepaawordbyMobrequest;
import com.shamrock.reework.activity.changepassword.ClsChangePasswordRequest;
import com.shamrock.reework.activity.newlogin.ClsPasswordRequest;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.request.AuthenticationRequest;
import com.shamrock.reework.api.request.OtpRequest;
import com.shamrock.reework.api.response.AuthenticationResponse;
import com.shamrock.reework.api.response.OtpResponse;
import com.shamrock.reework.model.MasterActivty.MyActivtyHistroy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginService
{

    @POST("api/Login/Authenticate")
    Call<AuthenticationResponse> authenticateUser(@Body AuthenticationRequest authenticationRequest);



    @POST("api/Login/Login")
    Call<OtpResponse> getVerifyPassword(@Body ClsPasswordRequest authenticationRequest);



    @POST("api/Registration/ChangePassword")
    Call<ChangeSucess> ChangePassword(@Body ClsChangePasswordRequest authenticationRequest);

    @POST("api/Registration/ChangePasswordByMobileNo")
    Call<ChangeSucess> ChangePasswordbymob(@Body ChangepaawordbyMobrequest authenticationRequest);

    @POST("api/Login/OTPVerified")
    Call<OtpResponse> verifyOTP(@Body OtpRequest otpRequest);


//8208096976

    @GET("api/Login/GetServices")
    Call<ClsLockUnlockMain> getLockUnlockDataAPI(@Query("ReeworkerId") int ReeworkerId);

//    GET-http://localhost:62096/api/Notification/ClearNotification?UserId=1
@GET("api/Notification/ClearNotification")
Call<ClsEditSleepResonse> getClearNotification(@Query("UserId") int UserId);


    @GET("CheckUserStatus/{input}")
    Call<UserStatus> getUserStatusHistroy(@Path("input") int input);



    @GET("api/Report/GetCaloriesHistory")
    Call<ClsCalorianalysis> getcaloriesHistory(@Query("ReeworkerId") int id);

}
