package com.shamrock.reework.activity.AppoinmentModule.service;


import com.shamrock.reework.activity.AppoinmentModule.pojo.ClsChargableMain;
import com.shamrock.reework.activity.FoodModule.history.ClsHistrydeleteRequest;
import com.shamrock.reework.activity.foodguide.ClsFoodGuideMain;
import com.shamrock.reework.activity.foodguide.FoodGuidePojo;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterRequest;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.api.request.AppoinmentEditRequest;
import com.shamrock.reework.api.request.AppoinmentRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.response.AppoinmentEditResponse;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppoinmentService
{
    @POST("api/UserReecoachAppointments/ReecoachAppointments")
    Call<AppoinmentResponse> sentAppoinmentRequest(@Body AppoinmentRequest appoinmentRequest);

    @POST("api/UserReecoachAppointments/GetAllScheduledAppointments")
    Call<GetAllAppointmentResponse> getAllAppoinments(@Body GetAllAppointmentReq appoinmentRequest);

//    https://reeworkmindbody.co.in/api/Recipe/FoodGuide?ReeworkerId=72

    @GET("api/Recipe/FoodGuide")
    Call<ClsFoodGuideMain> getFoodGuide(@Query("ReeworkerId") int ReeworkerId);




    @POST("api/UserReecoachAppointments/EditAppointments")
    Call<AppoinmentEditResponse> editAppoinmentResponse(@Body AppoinmentEditRequest appoinmentEditRequest);

    @POST("api/UserReecoachAppointments/DeleteAppointments")
    Call<AppoinmentResponse> deleteAppoinment(@Body AppoinmentEditRequest appoinmentEditRequest);




//    http://localhost:62096/api/Recipe/Delete_MealItem

    @POST("api/Recipe/Delete_MealItem")
    Call<ClsEditWaterResonse> deleteMealhistory(@Body ClsHistrydeleteRequest appoinmentEditRequest);


//    http://shamrockuat.dweb.in/api/Master/GetNutritionist?RoleId=3
//    http://shamrockuat.dweb.in/api/Master/GetNutritionist?RoleId=3&UserId=4147
    @GET("api/Master/GetNutritionist")
    Call<ClsChargableMain> getNutritionist(@Query("RoleId") int rolID,@Query("UserId") int dd);

}
