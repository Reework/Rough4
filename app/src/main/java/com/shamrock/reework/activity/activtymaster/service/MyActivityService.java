package com.shamrock.reework.activity.activtymaster.service;


import com.shamrock.reework.activity.activtyhistory.WeeklyActivitymainPojo;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.ClsRepeatActivityRequest;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.ClsRepeatActivtyMainPojo;
import com.shamrock.reework.activity.activtymaster.service.repeatactivity.RepeatActivityAddRequest;
import com.shamrock.reework.api.request.AppoinmentEditRequest;
import com.shamrock.reework.api.request.AppoinmentRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.response.AppoinmentEditResponse;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;
import com.shamrock.reework.model.AddFitbitDataToServerRes;
import com.shamrock.reework.model.MasterActivty.AcivityHistory;
import com.shamrock.reework.model.MasterActivty.ActivityResponse;
import com.shamrock.reework.model.MasterActivty.AddActivityRequest;
import com.shamrock.reework.model.MasterActivty.MyActivtyHistroy;
import com.shamrock.reework.model.MasterActivty.MyActivtyListMaster;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyActivityService
{

    @GET("GetCombinedActivityMasters")
    Call<MyActivtyListMaster> getAllActivityListMaster();


//    http://shamrockuat.dweb.in/SaveRepeatActivity
@POST("SaveRepeatActivity")
Call<ActivityResponse> addrepeatActivityData(@Body ArrayList<ClsRepeatActivityRequest> addActivityRequest);

    @POST("AddAddUserActivity")
    Call<ActivityResponse> setActivityData(@Body AddActivityRequest addActivityRequest);

    @GET("DeleteUserActivity")
    Call<ActivityResponse> DeleteUserActivity(@Query("Id") String id);

//    http://shamrockuat.dweb.in/api/Report/RepeatActivity?UserId=3040
    @GET("api/Report/RepeatActivity")
    Call<ClsRepeatActivtyMainPojo> RepeatActivity(@Query("UserId") int id);


    @POST("AddFitibitActivities")
    Call<ActivityResponse> addFitibitActivities(@Body AddFitbitDataToServerRes addFitbiActivityRequest);


    @GET("GetUserActivityHistory/{input}/{inputs}")
    Call<MyActivtyHistroy> getUserActivityHistroy(@Path("input") int input,@Path("inputs") String inputdate);


//    http://shamrockuat.dweb.in/api/ReeWorkerWeeklyAnalysis/GetWeeklyActivityAnalysis?UserId=3040

    @GET("api/ReeWorkerWeeklyAnalysis/GetWeeklyActivityAnalysis")
    Call<WeeklyActivitymainPojo> GetWeeklyActivityAnalysis(@Query("UserId") int id);

    @POST("EditAddUserActivity")
    Call<ActivityResponse> editUserActivity(@Body AddActivityRequest addActivityRequest);

    @POST("api/UserReecoachAppointments/EditAppointments")
    Call<AppoinmentEditResponse> editAppoinmentResponse(@Body AppoinmentEditRequest appoinmentEditRequest);

    @POST("api/UserReecoachAppointments/DeleteAppointments")
    Call<AppoinmentResponse> deleteAppoinment(@Body AppoinmentEditRequest appoinmentEditRequest);
}
