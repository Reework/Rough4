package com.shamrock.reework.activity.MyPlansModule.service;

import com.shamrock.reework.activity.FoodModule.model.repeatdata.ClsRepeatTodaysMeal;
import com.shamrock.reework.activity.MyPlansModule.addfood.ClsTodaysPlanAddFood;
import com.shamrock.reework.activity.MyPlansModule.addfood.UpdatePlanItem;
import com.shamrock.reework.activity.newmyplan.ClsTodaysPlanNewMainPojo;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.activity.todaysplan.model.ClsTodaysPlanMain;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.MyPlanRequest;
import com.shamrock.reework.api.request.MyPlansCheckRequest;
import com.shamrock.reework.api.response.MyPlanMastersResponse;
import com.shamrock.reework.api.response.MyPlanResponse;
import com.shamrock.reework.model.MasterActivty.ActivityResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyPlansService
{

    @POST("api/MyTodayPlan/UpdatePlanItem")
    Call<ClsEditSleepResonse> setFooodPlan(@Body UpdatePlanItem req);

    @POST("api/ScheduledPlan/GetMasterPlan")
    Call<MyPlanMastersResponse> getMasterPlans(@Body GetAllAppointmentReq req);

    @POST("api/ScheduledPlan/GetAllPlanForMyPlan")
    Call<MyPlanResponse> getTodaysPlan(@Body MyPlanRequest req);

    @POST("api/ScheduledPlan/GetPlanByReeworkerIDAndPlanId")
    Call<MyPlanResponse> getAllPlans(@Body MyPlanRequest req);

    @POST("api/ScheduledPlan/SetMyActivity")
    Call<MyPlanResponse> setDonePlans(@Body MyPlansCheckRequest req);


//    http://shamrockreework.dweb.in/ReeworkerPlan/ItemList?ItemTypeId=6
@GET("ReeworkerPlan/ItemList")
Call<ClsTodaysPlanAddFood> getItemList(@Query("ItemTypeId") int ItemTypeId);


//    https://reeworkmindbody.co.in/api/MyLifeStylePlan/DeleteLifestyelPlan?Id=3951
@POST("api/MyLifeStylePlan/DeleteLifestyelPlan")
Call<ActivityResponse> getDeleteLifestyelPlan(@Query("Id") int Id);


//    http://shamrockuat.dweb.in/api/Report/TodaysPlan?ReeworkerId=3040&PlanTypeId=0

    @POST("api/Report/TodaysPlan")
    Call<ClsTodaysPlanMain> getTodaysPlan(@Query("ReeworkerId") int userid, @Query("PlanTypeId") int mealtype,@Query("PlanFor")String date);



    @POST("api/Report/weeklyplan")
    Call<ClsTodaysPlanMain> getAllTodaysPlan(@Query("ReeworkerId") int userid, @Query("PlanTypeId") int mealtype,@Query("PlanFor")String date,@Query("WeeklyID")String WeeklyID);


//    http://localhost:62096/api/Report/TodaysPlan?ReeworkerId=3040&PlanTypeId=0&PlanFor=2021-06-10&WeeklyID=All




//    http://shamrockuat.dweb.in/api/MyTodayPlan/SetTodaysPlan/1?IsAdded=true&Notifiy=true&AddedOn=2020-11-25

    @GET("api/MyTodayPlan/SetTodaysPlan/{input}")
    Call<ClsEditSleepResonse> SetTodaysPlan(@Path("input") int input, @Query("IsAdded") boolean IsAdded, @Query("Notifiy") boolean Notifiy, @Query("AddedOn") String AddedOn,  @Query("MappingId") int alternate);



    @POST("api/Report/TodaysPlan")
    Call<ClsTodaysPlanNewMainPojo> getTodaysPlanNew(@Query("ReeworkerId") int userid, @Query("PlanTypeId") int mealtype, @Query("PlanFor")String date);

}
