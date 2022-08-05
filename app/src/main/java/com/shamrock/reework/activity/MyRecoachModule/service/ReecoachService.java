package com.shamrock.reework.activity.MyRecoachModule.service;

import com.shamrock.reework.activity.FaqActivity.ClsFAQPojo;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachListByType;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsReecoachMasterType;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsSetReecoach;
import com.shamrock.reework.activity.MyRecoachModule.activity.ClsSetReecoachRequest;
import com.shamrock.reework.activity.Pathologists.ClsSetPAthoRequest;
import com.shamrock.reework.activity.health.HealthMain;
import com.shamrock.reework.activity.mybcaplan.ClsBcaMainClass;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.request.ReecoachDetailsRequest;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReecoachService
{
    @POST("api/Registration/ReecoachDetails")
    Call<ReecoachDetailsResponse> getProfileDetails(@Body ReecoachDetailsRequest request);


//    http://shamrockuat.dweb.in/api/Registration/PathologistDetails
    @POST("api/Registration/PathologistDetails")
    Call<ReecoachDetailsResponse> getPathologiesProfileDetails(@Body ReecoachDetailsRequest request);



//    shamrockuat.dweb.in/api/Master/GetReecoachTypeMaster  @GET("api/Master/FoodUnitMaster")
@GET("api/Master/GetReecoachTypeMaster")
    Call<ClsReecoachMasterType> GetReecoachTypeMaster();



//    http://localhost:62096/api/Master/GetExitingplan?ReportTypeId=1&userid=3040
@GET("api/Master/GetExitingplan")
Call<ClsBcaMainClass> getExitingplan(@Query("ReportTypeId")int id, @Query("userid")int ids);





//    http://localhost:62096/api/Master/LabTestPlanMapping?ReportTypeId=1
@GET("api/Master/LabTestPlanMapping")
Call<ClsBcaMainClass> getBCALabTestPlanList(@Query("ReportTypeId")int id);






//    api-http://localhost:62096/api/Master/ChangePlan?Planid=1&ReportTypeId=1&reeworkerid=3040
@POST("api/Master/ChangePlan")
Call<ClsEditSleepResonse> getChangePlan(@Query("Planid")int id, @Query("ReportTypeId")int idd, @Query("reeworkerid")int reeworkerid);




//http://shamrockuat.dweb.in/api/Master/GetReecoachListByType?ReecoachTypeId=1


    @GET("api/Master/GetReecoachListByType")
    Call<ClsReecoachListByType> GetReecoachListByType(@Query("ReecoachTypeId")int id);

//    http://shamrockuat.dweb.in/api/Master/GetUserbyRole?RoleId=4
    @GET("api/Master/GetUserbyRole")
    Call<ClsReecoachListByType> GetPathoListByType(@Query("RoleId")int id);



//    http://shamrockuat.dweb.in/api/MaGetReecoachListByTypester/FAQ_Master
@GET("api/Master/FAQ_Master")
Call<ClsFAQPojo> getFAQ();



//    https://reeworkmindbody.co.in/Api/KnowledgeBank/GetLibraries?UserId=3040
@GET("Api/KnowledgeBank/GetLibraries")
Call<HealthMain> getKnowledgeBank(@Query("UserId")int id);




    //    http://shamrockuat.dweb.in/api/Registration/SetReecoach
    @POST("api/Registration/SetReecoach")
    Call<ClsSetReecoach> SetReecoachDetailAPi(@Body ClsSetReecoachRequest request);

//    http://shamrockuat.dweb.in/api/Registration/SetPathologist
    @POST("api/Registration/SetPathologist")
    Call<ClsSetReecoach> SetReecoachDetailAPi(@Body ClsSetPAthoRequest request);


}
