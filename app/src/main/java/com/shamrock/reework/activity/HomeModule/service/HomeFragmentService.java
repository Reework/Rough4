package com.shamrock.reework.activity.HomeModule.service;

import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.Coloriesmonth.ClsCaloriesMonth;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsAddWaterRequest;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterAddSuccessData;
import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterUOMMain;
import com.shamrock.reework.activity.DailyActivityModule.model.AddUpdateActivityRequest;
import com.shamrock.reework.activity.DailyActivityModule.model.AddUpdateActivityResponse;
import com.shamrock.reework.activity.HomeModule.pojo.ClsProfileHeaderData;
import com.shamrock.reework.activity.healthmonitoring.ClsHealthMonitorMain;
import com.shamrock.reework.activity.healthmonitoring.ClsPostReemonitor;
import com.shamrock.reework.activity.healthmonitoring.ClsReemonitorHistory;
import com.shamrock.reework.activity.healthmonitoring.ReemonitorHistoryRequest;
import com.shamrock.reework.activity.tips.ClsSleepTips;
import com.shamrock.reework.api.request.AddMoodRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.GetTodayActivityRequest;
import com.shamrock.reework.api.request.HomeFragmentRequest;
import com.shamrock.reework.api.request.WaterRequest;
import com.shamrock.reework.api.response.GetRecoachByUserResponse;
import com.shamrock.reework.api.response.HomeFragmentResponse;
import com.shamrock.reework.api.response.HomeFragmentResponse1;
import com.shamrock.reework.api.response.MoodResponse;
import com.shamrock.reework.api.response.MoodUpdateResponce;
import com.shamrock.reework.api.response.SleepResponse;
import com.shamrock.reework.api.response.TipsResponce;
import com.shamrock.reework.api.response.WaterResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.model.ActivityWeeklyResponse;
import com.shamrock.reework.model.MasterActivty.MyActivtyHistroy;
import com.shamrock.reework.model.TokenResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeFragmentService
{
    @POST("api/ScheduledPlan/GetTodaysActivity")
    Call<HomeFragmentResponse> getInitialData(@Body HomeFragmentRequest homeFragmentRequest);

    @POST("api/ScheduledPlan/SetTodaysActivity")
    Call<HomeFragmentResponse> UpdateHomeData(@Body HomeFragmentRequest homeFragmentRequest);

    @POST("api/ScheduledPlan/GetReecoachByUser")
    Call<GetRecoachByUserResponse> GetReecoachId(@Body GetAllAppointmentReq appointmentReq);

    @POST("api/ReeWorkerWeeklyAnalysis/GetWeeklyWaterAnalysis")
    Call<WaterResponse> getWaterData(@Body WaterRequest waterRequest);

    @POST("api/ReeWorkerWeeklyAnalysis/GetWeeklyMoodAnalysis")
    Call<MoodResponse> getMoodData(@Body WaterRequest waterRequest);

     @POST("api/Report/GetReeworkMoodHistory")
Call<MoodResponse> getMoodHistoryData(@Body ClsHistoryRequest clsHistoryRequest);

    @POST("api/ReeWorkerWeeklyAnalysis/Update_IsBingeOnLargeQuantity")
    Call<MoodUpdateResponce> addMoodData(@Body AddMoodRequest waterRequest);

    @POST("api/ReeWorkerWeeklyAnalysis/GetWeeklySleepAnalysis")
    Call<SleepResponse> getSleepData(@Body WaterRequest waterRequest);

    @GET("api/ReeWorkerWeeklyAnalysis/Get_SleepAndMindTips")
    Call<TipsResponce> getTipsData();


    @GET("api/Master/GetSleepTips")
    Call<ClsSleepTips> getMasterSleepTipsData();



//    http://shamrockuat.dweb.in/api/Master/GetProfileHeader
    @GET("api/Master/GetProfileHeader")
    Call<ClsProfileHeaderData> getProfileHeaderApi();




//    http://shamrockuat.dweb.in/api/Master/GetWaterUoM
    @GET("api/Master/GetWaterUoM")
    Call<ClsWaterUOMMain> GetWaterUoMApi();


//    http://shamrockuat.dweb.in/api/Report/SetWaterActivityHistory
    @POST("api/Report/SetWaterActivityHistory")
    Call<ClsWaterAddSuccessData> SetWaterActivityHistory(@Body ClsAddWaterRequest waterRequest);




    @GET("api/Master/GetHealthyTips")
    Call<ClsSleepTips> getMasterFoodTipsData(@Query("tipsType") int id);


    @GET("api/ReeworkerActivity/GetActivityHistory?")
    Call<ActivityWeeklyResponse> GetActivityHistory(@Query("intUserId")int GetActivityHistory);


    @POST("api/ReeworkerActivity/AddUpdateReeworkerDailyActivity")
    Call<AddUpdateActivityResponse> addUpdateDailyActivity(@Body AddUpdateActivityRequest dailyActivityRequest);

//    http://shamrockuat.dweb.in/api/Master/HealthMonitoringMaster?ReeworkerId=3040
//    http://shamrockuat.dweb.in/api/HealthMonitor/HealthMonitoringMaster?ReeworkerId=3040
    @GET("api/HealthMonitor/HealthMonitoringMaster")
    Call<ClsHealthMonitorMain> getParametersListApi(@Query("ReeworkerId") int id);


//    POST /api/HealthMonitor/UpdateHealthMonitor

    @POST("api/HealthMonitor/UpdateHealthMonitor")
    Call<ClsWaterAddSuccessData> posthealthmonitirData(@Body ClsPostReemonitor waterRequest);


//    http://shamrockuat.dweb.in/api/Report/GetHealthMonitorHistory
    @POST("api/Report/GetHealthMonitorHistory")
    Call<ClsReemonitorHistory> getHealthMonitorHistory(@Body ReemonitorHistoryRequest waterRequest);


//    http://shamrockuat.dweb.in/api/HealthMonitor/DeleteHealthMonitor/{Id}
@GET("api/HealthMonitor/DeleteHealthMonitor/{input}")
Call<ClsWaterAddSuccessData> deleteHealthMonitor(@Path("input") int input);
}
