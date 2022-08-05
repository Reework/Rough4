package com.shamrock.reework.activity.NotificationModule.service;

import com.shamrock.reework.activity.AnalysisModule.activity.activityanalysis.ClsActivityHistoryPojo;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ClsCalorianalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Coloriesmonth.ClsCaloriesMonth;
import com.shamrock.reework.activity.AnalysisModule.activity.food.ClsMainFoodNap;
import com.shamrock.reework.activity.AnalysisModule.activity.newregistration.ClsregQn;
import com.shamrock.reework.activity.AnalysisModule.activity.sleep.ClsSleepAnalysis;
import com.shamrock.reework.activity.AnalysisModule.activity.sleepNap.ClsSleepNap;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterAnalyisPojo;
import com.shamrock.reework.activity.HomeModule.pojo.ClsLogoutMsg;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.request.FcmRequest;
import com.shamrock.reework.api.request.GetAllNotificationRequest;
import com.shamrock.reework.api.request.SetNotificationStatusRequest;
import com.shamrock.reework.api.response.FcmResponse;
import com.shamrock.reework.api.response.GetAllNotificationResponse;
import com.shamrock.reework.api.response.NotifCountResponse;
import com.shamrock.reework.api.response.SetNotificationStatusResponse;
import com.shamrock.reework.common.ClsHistoryRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NotificationService
{
    //    GET-http://localhost:62096/api/Notification/ClearNotification?UserId=1
    @GET("api/Notification/ClearNotification")
    Call<ClsEditSleepResonse> getClearNotification(@Query("UserId") int UserId);

    @POST("api/Reminders/Get_Notification")
    Call<GetAllNotificationResponse> getAllNotifications(@Body GetAllNotificationRequest request);

    @POST("api/Reminders/Get_NotificationCount")
    Call<NotifCountResponse> getAllNotificationCount(@Body GetAllNotificationRequest request);

    @POST("api/Reminders/Set_Notification")
    Call<SetNotificationStatusResponse> setNotificationStatus(@Body SetNotificationStatusRequest request);

    @POST("api/Login/ReSendFCMDeviceToken")
    Call<FcmResponse> PushFcmToServer(@Body FcmRequest request);


    @POST("api/Report/GetCaloriesHistory")
    Call<ClsCalorianalysis> getcaloriesHistory(@Body ClsHistoryRequest clsHistoryRequest);

    @POST("api/Report/SleepActivityAnalysis")
    Call<ClsSleepAnalysis> getSleepActivityAnalysis(@Body ClsHistoryRequest clsHistoryRequest);


    @POST("api/Report/ActivityAnalysis2")
    Call<ClsActivityHistoryPojo> getActivityAnalysis(@Body ClsHistoryRequest clsHistoryRequest);


    @POST("api/Report/NapWiseSleepAnalysis")
    Call<ClsSleepNap> getSleepNapAnalysis(@Body ClsHistoryRequest clsHistoryRequest);



    @POST("api/Report/FoodAnalysis")
    Call<ClsMainFoodNap> getFoodAnalysis(@Body ClsHistoryRequest clsHistoryRequest);


//    http://shamrockuat.dweb.in/api/Master/LogoutMessage?ReeworkerId=3040
    @GET("api/Master/LogoutMessage")
    Call<ClsLogoutMsg> getLogoutMessage(@Query("ReeworkerId") int id);




    @GET("api/Master/GetTrailQuestion")
    Call<ClsregQn> getTrailQuestion(@Query("ReeworkerId") int id);


    @GET("api/Report/GetCaloriesMonth")
    Call<ClsCaloriesMonth> getCaloriesMonth(@Query("ReeworkerId") int id);



    @GET("api/Report/GetMoodAnalysisMonth")
    Call<ClsCaloriesMonth> getMoodAnaMonth(@Query("ReeworkerId") int id);

    @POST("api/Report/WaterAnalysis")
    Call<ClsWaterAnalyisPojo> getWaterHistory(@Body ClsHistoryRequest  clsHistoryRequest);


}
