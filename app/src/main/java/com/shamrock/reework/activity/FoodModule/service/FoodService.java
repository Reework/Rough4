package com.shamrock.reework.activity.FoodModule.service;

import com.google.firebase.crashlytics.internal.report.model.Report;
import com.shamrock.reework.activity.FoodModule.fragment.ClsVegNonVegFoodRequest;
import com.shamrock.reework.activity.FoodModule.model.AddMealRequest;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodHistory;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodHistoryRequest;
import com.shamrock.reework.activity.FoodModule.model.ClsFoodUnitMasterPojo;
import com.shamrock.reework.activity.FoodModule.model.ClsHealthCategoryMaster;
import com.shamrock.reework.activity.FoodModule.model.ClsSuggestion;
import com.shamrock.reework.activity.FoodModule.model.EditMealRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodAnalysisRequest;
import com.shamrock.reework.activity.FoodModule.model.FoodAnalysisResponce;
import com.shamrock.reework.activity.FoodModule.model.RemoveMealItem;
import com.shamrock.reework.activity.FoodModule.model.WeeklyAnalysisRequest;
import com.shamrock.reework.activity.FoodModule.model.WeeklyAnalysisResponce;
import com.shamrock.reework.activity.FoodModule.model.foodtripuser.ClsUSerFoodTripmain;
import com.shamrock.reework.activity.FoodModule.model.repeatdata.ClsRepeatTodaysMeal;
import com.shamrock.reework.activity.HealthModule.activity.newHealth.pojo.ClsNewHealthParamData;
import com.shamrock.reework.activity.MyPlansModule.model.GlosaryMainClass;
import com.shamrock.reework.activity.cheatplan.pojo.ClsMainPlansClass;
import com.shamrock.reework.activity.cheatplan.pojo.ClsOccastionMain;
import com.shamrock.reework.activity.cheatplan.reeplaceitem.CheatPlanDataMain;
import com.shamrock.reework.activity.dietplan.pojo.AddFoodPlanRequest;
import com.shamrock.reework.activity.dietplan.pojo.ClsMealtypeMain;
import com.shamrock.reework.activity.dietplan.pojo.ClsPathoReportMain;
import com.shamrock.reework.activity.dietplan.pojo.RDPFoodPlanMain;
import com.shamrock.reework.activity.dietplan.pojo.RDPRequest;
import com.shamrock.reework.activity.dietplan.pojo.RDPSuccess;
import com.shamrock.reework.activity.newrecipe.ClsAllRecipeMainClass;
import com.shamrock.reework.activity.newrecipe.ClsEditRecipeMain;
import com.shamrock.reework.activity.newrecipe.EditRecipeData;
import com.shamrock.reework.activity.recipe.cusion.CusionMain;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.FoodListByType;
import com.shamrock.reework.api.request.FoodRecipeEditRequest;
import com.shamrock.reework.api.request.FoodTripFavoriteUpdateRequest;
import com.shamrock.reework.api.request.FoodTripRequest;
import com.shamrock.reework.api.request.GetRecipeRequest;
import com.shamrock.reework.api.response.BeforeAfterResponse;
import com.shamrock.reework.api.response.FoodListByMealType;
import com.shamrock.reework.api.response.FoodRecipeEditResponse;
import com.shamrock.reework.api.response.FoodSnippingResp;
import com.shamrock.reework.api.response.FoodSnippingResponse;
import com.shamrock.reework.api.response.FoodTripFavoriteUpdateResponse;
import com.shamrock.reework.api.response.FoodTripResponse;
import com.shamrock.reework.api.response.GetFilterSubFilterResponce;
import com.shamrock.reework.api.response.GetMealType;
import com.shamrock.reework.api.response.RecipeResponse;
import com.shamrock.reework.common.ClsHistoryRequest;
import com.shamrock.reework.model.TodaysMealData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FoodService
{
//    http://shamrockuat.dweb.in/api/Master/OccasionMaster

    @GET("api/Master/OccasionMaster")
    Call<ClsOccastionMain> getOccasionMaster();


    @GET("api/Master/CheatPlanDetails")
    Call<CheatPlanDataMain> getNewCheatMaster();


//    http://shamrockuat.dweb.in/api/Recipe/SaveFavoriteMeal?MealId=413&IsFav=true

    @GET("api/Recipe/SaveFavoriteMeal")
    Call<FoodTripFavoriteUpdateResponse> setuserTripFavoriete(@Query("MealId") int userid, @Query("IsFav") boolean mealtype);



    //    http://shamrockuat.dweb.in/api/Report/SaveFoodPlan
    @POST("api/Report/SaveFoodPlan")
    Call<RDPSuccess> getSaveFoodPlan(@Body AddFoodPlanRequest foodTripRequest);


//    http://shamrockuat.dweb.in/api/Report/DeleteFoodPlan?FoodPlanId=1

    @GET("api/Report/DeleteFoodPlan")
    Call<RDPSuccess> getDeleteFoodPlan(@Query("FoodPlanId") int id);


//    Report/DeleteFeeling?ID=202484

    @POST("api/Report/DeleteFeeling")
    Call<RDPSuccess> getDeleteMind(@Query("ID") int id);



    @POST("Api/Recipe/Get_RecipeList")
    Call<FoodTripResponse> getAllRecipies(@Body ClsVegNonVegFoodRequest foodTripRequest);



//    POST-http://localhost:62097/api/RecipeList/CustomizedVegeterian?UserId=7225
    @POST("api/RecipeList/CustomizedVegeterian")
    Call<FoodTripResponse> getCustomizedVegeterianRecipies(@Query("UserId") int id);



    @POST("api/RecipeList/CustomizedNonVegeterian")
    Call<FoodTripResponse> getCustomizedNonVegeterianRecipies(@Query("UserId") int id);



//    http://localhost:62096/api/Recipe/GetAllRecipeList

    @POST("api/Recipe/GetAllRecipeList")
    Call<FoodTripResponse> getAllRecipeList(@Query("UserId") int id);




    @POST("api/RecipeList/CuisineList")
    Call<CusionMain> getCuisineList();


//    https://reework.in/api/Report/GroceryList?PlanId=4346
    @GET("api/Report/GroceryList")
    Call<GlosaryMainClass> getGroceryList(@Query("PlanId") int id);



//    http://shamrockuat.dweb.in/api/Recipe/GetFoodTrip?ReeworkerId=3040

    @GET("api/Recipe/GetFoodTrip")
    Call<ClsUSerFoodTripmain> getUserFoodTrip(@Query("ReeworkerId") int id);


//    http://shamrockuat.dweb.in/api/Report/SaveRDP
    @POST("api/Report/SaveRDP")
    Call<RDPSuccess> saveRDP(@Body RDPRequest rdpRequest);



//    http://shamrockuat.dweb.in/api/Master/HealthCategoryMaster
    @GET("api/Master/HealthCategoryMaster")
    Call<ClsHealthCategoryMaster> getHealthCategoryMaster();

    //http://shamrockuat.dweb.in/api/BeforeAfter/GetFoodSnappingUserImages
    //FoodNipping APIs
    @POST("api/BeforeAfter/GetFoodSnappingUserImages")
    Call<FoodSnippingResp> getAllImages(@Body FoodTripRequest foodTripRequest);


    @Multipart
    @POST("api/BeforeAfter/FoodSnapping")
    Call<BeforeAfterResponse> Upload
            (
                    @Part MultipartBody.Part photo,
                    @Part("UserID") RequestBody uesreIID,
                    @Part("ImageName") RequestBody imgName,
                     @Part("UploadedOn") RequestBody uploadDate
            );


//    http://shamrockuat.dweb.in/api/MyRecipe/GetRecipe?RecipeId=10177

    @GET("api/MyRecipe/GetRecipe")
    Call<ClsEditRecipeMain> GetEditRecipe(@Query("RecipeId") int userid);



//    http://shamrockuat.dweb.in/api/MyRecipe/Master
    @GET("api/MyRecipe/Master")
    Call<ClsAllRecipeMainClass> getRecipemaster();


   @Multipart
   @POST("api/MyRecipe/SaveRecipe")
   Call<ClsEditWaterResonse> saveEditRecipe( @Part("Recipe") RequestBody uesreIID);


    @POST("api/Recipe/Update_IsFavorite")
    Call<FoodTripFavoriteUpdateResponse> setIsFavoriteStatus(@Body FoodTripFavoriteUpdateRequest request);

    @POST("api/Recipe/Get_RecipeDetails")
    Call<RecipeResponse> GetRecipe(@Body GetRecipeRequest recipeRequest);

    @POST("api/Recipe/Save_UserRecipe")
    Call<FoodRecipeEditResponse> editRecipe(@Body FoodRecipeEditRequest recipeRequest);

    @POST("api/Recipe/Get_FoodListByMealType")
    Call<FoodListByMealType> foodListByType(@Body FoodListByType foodListByType);

    @POST("api/Recipe/Save_TodaysMeal")
    Call<GetMealType> saveToadysMeal(@Body AddMealRequest foodListByType);

    @POST("api/Recipe/Modify_TodaysMeal")
    Call<GetMealType> editToadysMeal(@Body EditMealRequest foodListByType);

    @POST("api/Recipe/Delete_MealItem")
    Call<GetMealType> removeToadysMealItem(@Body RemoveMealItem foodListByType);

    @POST("api/Recipe/Get_TodaysMeal")
    Call<TodaysMealData> getTodyasMeal(@Body BcaRequest bcaRequest);


    @GET("api/Report/RepeatMeal")
    Call<ClsRepeatTodaysMeal> getRepeatsMeal(@Query("UserId") int userid, @Query("MealType") String mealtype);

    @POST("api/Recipe/Get_WeeklyAnalysis")
    Call<WeeklyAnalysisResponce> get_WeeklyAnalysis(@Body WeeklyAnalysisRequest bcaRequest);

    @POST("api/Recipe/Get_FoodAnalysis")
    Call<FoodAnalysisResponce> get_FoodAnalysis(@Body FoodAnalysisRequest bcaRequest);

    @POST("api/Report/Get_FoodAnalysis2")
    Call<FoodAnalysisResponce> get_FoodAnalysisHistory(@Body ClsHistoryRequest bcaRequest);


//    http://shamrockuat.dweb.in/api/Master/MealTypeMaster
    @GET("api/Master/MealTypeMaster")
    Call<ClsMealtypeMain> getMealTypeMaster();

    @GET("api/Recipe/Get_MealType")
    Call<GetMealType> getMealType();


//    http://shamrockuat.dweb.in/api/Master/FoodUnitMaster
//    http://shamrockuat.dweb.in/api/Master/CheatPlan?UserId=3040
    @GET("api/Master/CheatPlan")
    Call<ClsMainPlansClass> getCheatPlan(@Query("UserId") int id);


//    http://shamrockuat.dweb.in/api/Report/ReescoreReport?ReeworkerId=3040
    @GET("api/Report/ReescoreReport")
    Call<ClsPathoReportMain> getReescoreReport(@Query("ReeworkerId") int id);



//    http://shamrockuat.dweb.in/api/Report/GetRDP?ReeworkerId=3040
@GET("api/Report/GetRDP")
Call<RDPFoodPlanMain> getGetRDPReport(@Query("ReeworkerId") int id);



    @GET("api/Master/FoodUnitMaster")
    Call<ClsFoodUnitMasterPojo> getFoodUnitMaster();


    @GET("api/Master/FoodUnitMaster")
    Call<ClsFoodUnitMasterPojo> getFoodUnitMasterNew(@Query("ItemTypeId") int id);

//    http://shamrockuat.dweb.in/api/MyTodayPlan/UomList?UomId=320569
    @GET("api/MyTodayPlan/UomList")
    Call<ClsFoodUnitMasterPojo> getItmeWiseUOMw(@Query("UomId") int id);

//    FinishedProductId
@GET("api/MyTodayPlan/UomList")
Call<ClsFoodUnitMasterPojo> getSupplimentItmeWiseUOMw(@Query("FinishedProductId") int id);


    @GET("api/Master/FoodUnitMaster")
    Call<ClsFoodUnitMasterPojo> getFoodUnitMasterNewIDWise(@Query("FinishedProuctId") int id);



    @GET("api/Recipe/GetFoodSuggestion")
    Call<ClsSuggestion> getFoodSuggestion();


    @GET("api/Recipe/Get_RecipeFilterAndSubFilters")
    Call<GetFilterSubFilterResponce> getFilterSubFilter();


    @POST("services/ReecoachServices/ReecoachReport/FoodHistory")
//    Call<ClsFoodHistory> FoodHistory(@Body ClsFoodHistoryRequest clsFoodHistoryRequest);
    Call<ClsFoodHistory> FoodHistory(@Body ClsFoodHistoryRequest clsFoodHistoryRequest);

}
