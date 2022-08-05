package com.shamrock.reework.activity.HealthModule.service;

import com.shamrock.reework.activity.HealthModule.activity.newHealth.ReecoachHealthParamData;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo.ClsNewHealthParamData;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo.ReecoachClsNewHealthParamData;
import com.shamrock.reework.activity.HealthModule.entity.PaidSubscriptionList;
import com.shamrock.reework.activity.WeightModule.MyWeight;
import com.shamrock.reework.activity.WeightModule.UserWeightRequest;
import com.shamrock.reework.activity.WeightModule.adapters.ClsWeightPojo;
import com.shamrock.reework.activity.WeightModule.adapters.ClsWeightRequest;
import com.shamrock.reework.activity.WeightModule.adapters.WeightHistory;
import com.shamrock.reework.activity.activtyhistory.ClsNewActivityHistoryPojo;
import com.shamrock.reework.activity.parameter.pojo.ClsBcaPathoGraphMain;
import com.shamrock.reework.activity.parameter.pojo.ClsGraphDataMainClass;
import com.shamrock.reework.activity.recipe.model.CLsNutritionResponse;
import com.shamrock.reework.activity.recipe.model.ClsIngradientMain;
import com.shamrock.reework.activity.recipe.model.ClsNutritionDataMain;
import com.shamrock.reework.activity.recipe.model.ClsPostIngradeints;
import com.shamrock.reework.activity.recipe.model.ClsRecipeMasterMain;
import com.shamrock.reework.activity.recipeanalytics.ClsRecipeAnalyticDataMain;
import com.shamrock.reework.activity.recipeanalytics.ClsRecipeLibraryAnalyticRequest;
import com.shamrock.reework.activity.reeworkerhealth.app.ClsPostHealthData;
import com.shamrock.reework.activity.reeworkerhealth.app.ClsgetPostData;
import com.shamrock.reework.activity.services.pojo.ClsExistingPlan;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.api.request.HealthParameterRequest;
import com.shamrock.reework.api.response.AuthenticationResponse;
import com.shamrock.reework.api.response.FoodCulturesResponse;
import com.shamrock.reework.api.response.FoodTypesResponse;
import com.shamrock.reework.api.response.HealthParameterResponse;
import com.shamrock.reework.api.response.MedicalConditionsResponse;
import com.shamrock.reework.model.ClsGadgetmainData;
import com.shamrock.reework.payment.ClsCouponMainClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HealthParametersService
{


    @GET("api/Master/GetGadget")
    Call<ClsGadgetmainData> getGadgetData();


    @GET("api/HealthParams/GetFoodTypeList")
    Call<FoodTypesResponse> getFoodTypesList();



    @POST("api//HealthParams/GetHealthParameter")
    Call<UserHealthResponse> getHealthparametdata(@Body HealthRequest request);



//    https://reeworkmindbody.co.in/api/ReeworkerQuestionnaire/GetQuestionnaire?ReeworkerId=3040

    @GET("api/ReeworkerQuestionnaire/GetQuestionnaire")
    Call<ReecoachClsNewHealthParamData> getReecoachQuestionnaire(@Query("ReeworkerId") int id);

    @GET("api/HealthParams/GetReeworkerHealthParam")
    Call<ClsNewHealthParamData> getReeowrkerHealthData(@Query("ReeworkerId") int id);

    @POST("api/HealthParams/SaveReeworkerHealthParam")
    Call<ClsgetPostData> saveHealthParameter(@Body ClsPostHealthData otpRequest);

//    https://reeworkmindbody.co.in/api/ReeworkerQuestionnaire/SaveQuestionnaire
    @POST("api/ReeworkerQuestionnaire/SaveQuestionnaire")
    Call<ClsgetPostData> saveReecoachQuestionParameter(@Body ArrayList<ReecoachHealthParamData> otpRequest);



    @POST("api/Report/GetReeworkerWeight")
    Call<ClsWeightPojo> GetReeworkerWeight(@Body ClsWeightRequest otpRequest);



    @POST("api/Report/GetActivityHistory")
    Call<ClsNewActivityHistoryPojo> GetActivityHistory(@Body ClsWeightRequest otpRequest);


    @POST("api/Report/SaveReeworkerWeight")
    Call<AuthenticationResponse> updateReeworkerWeight(@Body WeightHistory otpRequest);


    @GET("api/HealthParams/GetFoodCultureList")
    Call<FoodCulturesResponse> getFoodCulturesList();

    @GET("api/HealthParams/GetMedicalConditionList")
    Call<MedicalConditionsResponse> getMedicalConditionList();

    @POST("api/HealthParams/PostHealthParameter")
    Call<HealthParameterResponse> sendHealthParameter(@Body HealthParameterRequest otpRequest);


    @POST("api/HealthParams/SaveUserHealthDetail")
    Call<MyWeight> sendUserHealthDetail(@Body UserWeightRequest userWeightRequest);

//    http://shamrockmrockuat.dweb.in/api/SubscriptionMaster/PaidSubscriptionList?ReeworkerId=3040

    @GET("api/SubscriptionMaster/PaidSubscriptionList")
    Call<PaidSubscriptionList> getPaymentDetails(@Query("ReeworkerId") int id);

//    http://shamrockuat.dweb.in/api/SubscriptionMaster/GetUserSubscriptionDetails?UserId=3040
    @GET("api/SubscriptionMaster/GetUserSubscriptionDetails")
    Call<ClsExistingPlan> getUserSubscriptionDetails(@Query("UserId") int id);



//    http://shamrockuat.dweb.in/api/Report/BCAPathoTestQuestion?ReeworkerId=3040&ReportTypeId=2
    @GET("api/Report/BCAPathoTestQuestion")
    Call<ClsBcaPathoGraphMain> BCAPathoTestQuestion(@Query("ReeworkerId") int ReeworkerId, @Query("ReportTypeId") int ReportTypeId);



//    http://shamrockuat.dweb.in/api/Report/GetGraphData
//    UserId=3040&ReportTypeId=2&From=2021-03-03&To=2021-06-10&TestId=20063
//@GET("api/Report/GetGraphData")
    @GET("api/Report/GetGraphDataApi")
    Call<ClsGraphDataMainClass> getGraphData(@Query("UserId") int ReeworkerId, @Query("ReportTypeId") int ReportTypeId, @Query("From") String From , @Query("To") String To, @Query("TestId") int TestId);




    @POST("api/SubscriptionMaster/SaveMembershipPlan")
    Call<ClsEditSleepResonse> SaveMembershipPlan(@Query("ReeworkerId") int id, @Query("SubscriptioPlanId")int planID, @Query("SubPlanId")String SubPlanId);


//    https://reeworkmindbody.co.in/api/SubscriptionMaster/GetCouponDiscount?PlanId=2&CouponCode=07EPvs22
@GET("api/SubscriptionMaster/GetCouponDiscount")
Call<ClsCouponMainClass> getCouponDiscountApi(@Query("PlanId") String PlanId, @Query("CouponCode") String CouponCode);





//    http://shamrockuat.dweb.in/api/Recipe/MasterList
    @GET("api/Recipe/MasterList")
    Call<ClsRecipeMasterMain> getMasterList();



//    http://shamrockuat.dweb.in/api/Recipe/IngredientList

    @GET("api/Recipe/IngredientList")
    Call<ClsIngradientMain> getIngradientMasterList();


//    POST /api/Recipe/GetNutrition
    @POST("api/Recipe/GetNutrition")
    Call<ClsNutritionDataMain> GetNutrition(@Body ArrayList<ClsPostIngradeints> otpRequest);


//    http://shamrockuat.dweb.in/api/Recipe/Analytics

    @POST("api/Recipe/Analytics")
    Call<ClsRecipeAnalyticDataMain> getRecipeLibraryAnalytics(@Body ClsRecipeLibraryAnalyticRequest otpRequest);

}


