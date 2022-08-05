package com.shamrock.reework.activity.CheatPlanModule.service;

import com.shamrock.reework.activity.CheatPlanModule.model.request.WishNCheatMealRequest;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.CheatPlanResponse;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.OccasionResponce;
import com.shamrock.reework.activity.CheatPlanModule.model.responce.WishToEatResponse;
import com.shamrock.reework.api.request.FoodTripRequest;
import com.shamrock.reework.api.response.FoodSnippingResp;
import com.shamrock.reework.api.response.GetMealType;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CheatMealService {


    @GET("api/MyCheatPlan/Get_OccasionList")
    Call<OccasionResponce> getOccassion();


    @POST("api/MyCheatPlan/Get_WishToEatList")
    Call<WishToEatResponse> getWishToEatList(@Body WishNCheatMealRequest request);

    @POST("api/MyCheatPlan/Get_CheatPlanList")
    Call<CheatPlanResponse> getCheatPlanList(@Body WishNCheatMealRequest request);

}

