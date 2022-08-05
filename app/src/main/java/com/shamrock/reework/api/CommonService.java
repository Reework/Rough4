package com.shamrock.reework.api;

import com.shamrock.reework.activity.AnalysisModule.activity.water.ClsWaterHistoryPojoMain;
import com.shamrock.reework.activity.FoodModule.history.ClsFoodHistoryPojo;
import com.shamrock.reework.activity.HealthModule.service.HealthRequest;
import com.shamrock.reework.activity.HealthModule.service.UserHealthResponse;
import com.shamrock.reework.activity.HomeModule.lockumlock.ClsLockUnlockMain;
import com.shamrock.reework.activity.HomeModule.service.ClsMessagmasterPojo;
import com.shamrock.reework.activity.MyProfileModule.activity.MyProfileActivity;
import com.shamrock.reework.activity.WeightModule.activity.ClWtUpdateRequest;
import com.shamrock.reework.activity.WeightModule.activity.ClsWtHistoryResponse;
import com.shamrock.reework.activity.WelcomeModule.ClsCommonWellcomaster;
import com.shamrock.reework.activity.dietprofile.ClsDietProfile;
import com.shamrock.reework.activity.mindhistory.ClsMindWeeklyData;
import com.shamrock.reework.activity.reecoach.adapters.ClsReecoachQnDetails;
import com.shamrock.reework.activity.reecoach.adapters.ClsReecoachQnMain;
import com.shamrock.reework.activity.reecoach.postrate.ClsSucessRateMain;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepRequest;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.activity.sleepmodule.ClsSleepHistoryPojo;
import com.shamrock.reework.activity.sleepmodule.ClsupdateSleepRequest;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.ClsDemoSleep;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.ClsNewEditSleepRequest;
import com.shamrock.reework.activity.sleepmodule.newsleephisrtory.SleepDurations;
import com.shamrock.reework.activity.sleepmodule.repeatSleep.ClsRepeatSleepMain;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritaulTypeMain;
import com.shamrock.reework.activity.spirituallibrary.pojo.ClsSpiritualListMain;
import com.shamrock.reework.activity.unfreezeData.ClsUnfreezeDataMain;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterRequest;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.activity.waterhistory.ClsWeightHistoryRequest;
import com.shamrock.reework.api.request.CityRequest;
import com.shamrock.reework.api.request.ProfileDataRequest;
import com.shamrock.reework.api.request.StateRequest;
import com.shamrock.reework.api.response.CityResponse;
import com.shamrock.reework.api.response.CountryResponse;
import com.shamrock.reework.api.response.LanguageResponse;
import com.shamrock.reework.api.response.MyProfileResponse;
import com.shamrock.reework.api.response.ProfileDataResponse;
import com.shamrock.reework.api.response.StateResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.common.ClsHistoryResponse;
import com.shamrock.reework.common.MindHistoryRequest;
import com.shamrock.reework.model.MasterActivty.MyActivtyHistroy;
import com.shamrock.reework.reecoachmodule.activities.pojo.ClsReeworkListMain;
import com.shamrock.reework.reecoachmodule.activities.pojo.ClsmainDashboardData;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface CommonService
{

    @GET("api/UserAddress/GetCountryList")
    Call<CountryResponse> getCountryList();

    @POST("api/UserAddress/GetStateList")
    Call<StateResponse> getStateList(@Body StateRequest cityRequest);

    @POST("api/UserAddress/GetCityList")
    Call<CityResponse> getCityList(@Body CityRequest cityRequest);

    @GET("api/Language/GetLanguageList")
    Call<LanguageResponse> getLanguageList();

    @POST("api/Report/StatusReport")
    Call<ClsHistoryResponse> getHistoryData(@Body MindHistoryRequest request);

    @POST("api/Report/GetFeeling")
    Call<ClsMindWeeklyData> getMindWeeklyHistoryData(@Query("ReeworkerId") int ReeworkerId  );

//    http://shamrockuat.dweb.in/api/Report/GetWaterActivityHistory
    @POST("api/Report/GetWaterActivityHistory")
    Call<ClsWaterHistoryPojoMain> getWaterActivityHistory(@Body ClsHistoryRequest request);


    @POST("api/Report/GetSleepActivityHistory")
    Call<ClsDemoSleep> getSleepHistoryData(@Body ClsHistoryRequest request);


    @POST("api/Report/FoodHistory")
    Call<ClsFoodHistoryPojo> getFoodHistoryData(@Body ClsHistoryRequest request);



//    http://shamrockuat.dweb.in/api/Master/MessageMaster

    @GET("api/Master/MessageMaster")
    Call<ClsMessagmasterPojo> getMessageMaster();


//    http://shamrockuat.dweb.in/api/Master/SpiritualLibraryMaster/
    @GET("api/Master/SpiritualLibraryMaster")
    Call<ClsSpiritaulTypeMain> getSpiritualLibraryMaster();

//    http://shamrockuat.dweb.in/api/SpiritualLibrary/SpiritualLibraryVideosById?SpiritualLibraryId=1


    @GET("api/SpiritualLibrary/SpiritualLibraryVideosById")
    Call<ClsSpiritualListMain> getSpiritualLibraryVideosById(@Query("SpiritualLibraryId") int id);


//    http://shamrockuat.dweb.in/api/Master/DailyDiaryVideoByCategoryId?CategoryId=1
    @GET("api/Master/DailyDiaryVideoByCategoryId")
    Call<ClsSpiritualListMain> getDailyDiaryVideoByCategoryId(@Query("CategoryId") int id);



    @GET("api/Master/WelcomeMessage")
    Call<ClsCommonWellcomaster> getWelcomeMessage(@Query("MessageType") String type);


//    https://reeworkmindbody.co.in/services/ReecoachServices/DashboardAPI/DashboardDeails?ReecoachId=3037

    @GET("services/ReecoachServices/DashboardAPI/DashboardDeails")
    Call<ClsmainDashboardData> getDashboardDeails(@Query("ReecoachId") int  type);




    //    http://shamrockuat.dweb.in/ReeworkerPlan/DietProfile?ReeworkerId=3040&DietId=13
    @GET("ReeworkerPlan/DietProfile")
    Call<ClsDietProfile> getDeitProfile(@Query("ReeworkerId") int type, @Query("DietId") int DietId);


//    http://localhost:62096/api/chat/GetAllReeworker?ReecoachId=3037
@GET("api/chat/GetAllReeworker")
Call<ClsReeworkListMain> getAllReeworker(@Query("ReecoachId") int type);







//    http://shamrockuat.dweb.in/Api/Master/FreezeUnfreezeMaster
@GET("api/Master/FreezeUnfreezeMaster")
Call<ClsUnfreezeDataMain> getUnfreezeMessageAPI();


//    http://shamrockuat.dweb.in/api/Report/GetRepeatSleep?ReeworkerId=3040&EntryDate=2020-12-07
@GET("api/Report/GetRepeatSleep")
Call<ClsRepeatSleepMain> GetRepeatSleep(@Query("ReeworkerId") int ReeworkerId, @Query("EntryDate") String EntryDate);



///api/Login/GetServices?ReeworkerId=3040
    @GET("api/Login/GetServices")
    Call<ClsLockUnlockMain> getLocfckUnlockDataAPI(@Query("ReeworkerId") int ReeworkerId);




    @POST("api/Report/GetHealthHistory")
    Call<ClsWtHistoryResponse> getHealthHistoryData(@Body ClsWeightHistoryRequest request);


    @POST("api/Report/UpdateSleepActivity")
    Call<ClsEditSleepResonse> setUpdateSleepHistoyActivity(@Body ClsupdateSleepRequest request);

    @POST("api/Report/SetSleepActivityHistory")
    Call<ClsEditSleepResonse> SetSleepActivityHistory(@Body ClsNewEditSleepRequest request);

    @POST("api/Report/EditReport")
    Call<ClsEditSleepResonse> setUpdateSleepHistoy(@Body ClsEditSleepRequest request);



//    get /api/Report/DeleteSleepActivityHistory
@GET("api/Report/DeleteSleepActivityHistory/{input}")
Call<ClsEditSleepResonse> deleteSleepPowernapHistroy(@Path("input") String input);



    //    get /api/Report/DeleteSleepActivityHistory
    @GET("api/Report/DeleteWaterActivityHistory/{input}")
    Call<ClsEditSleepResonse> deletewaterPowernapHistroy(@Path("input") String input);


    @GET("api/Report/DeleteSleepActivity/{input}")
    Call<ClsEditSleepResonse> deleteSleepHistroy(@Path("input") String input);



    @POST("api/Report/EditHealthHistory")
    Call<ClsEditSleepResonse> setUpdateWtHistoy(@Body ClWtUpdateRequest request);



    @POST("api/Report/EditReport")
    Call<ClsEditWaterResonse> setUpdateWaterHistoy(@Body ClsEditWaterRequest request);

    @Multipart
    @POST("api/Registration/UserprofileImageUpload")
    Call<MyProfileResponse> uploadImage(@Header("Content-Type") String s, @Part MultipartBody.Part file, @Query("userid") String userID);

    @Multipart
    @POST("localserver/upload_file.php")
    Call<MyProfileActivity.ServerResponse> uploadImageNew(@Part MultipartBody.Part file, @Part("name") RequestBody userID);

    @GET("api/Master/GetRateFeedback")
    Call<ClsReecoachQnMain> getReecoachRatingQn(@Query("ReecoachId") int ReecoachId, @Query("ReeworkerId") int ReeworkerId);


    @POST("api/Master/PostRateFeedback")
    Call<ClsSucessRateMain> postRatingQn(@Query("ReecoachId") int ReecoachId, @Query("ReeworkerId") int ReeworkerId, @Query("Ratings") float Ratings, @Body ArrayList<ClsReecoachQnDetails> request);

}
