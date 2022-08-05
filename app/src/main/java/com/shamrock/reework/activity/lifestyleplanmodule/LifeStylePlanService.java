package com.shamrock.reework.activity.lifestyleplanmodule;

import com.shamrock.reework.activity.lifestyleplanmodule.pojo.ClsPostShotcutWeekly;
import com.shamrock.reework.activity.lifestyleplanmodule.pojo.ClsSuccess;
import com.shamrock.reework.util.ClsUpdateWeekDaysRequest;
import com.shamrock.reework.util.ClsWeeDaysResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rahul on 05,June,2020
 */
public interface LifeStylePlanService {
    @GET("api/MyLifeStylePlan/ReeworkerLifestylePlan")
    Call<ClsMainLifeStylePlanData> getLifeStylePlan(@Query("ReeworkerId") String ReeworkerId,
                                           @Query("dType") String dType,@Query("PlanType") String type
    );

    @POST("api/MyLifeStylePlan/UpdateReeworkerLifestylePlan")
    Call<LifeStylePlanPostsucces> insertLifeStylePlan(@Body LifeStylePlanPost lifeStylePlanPost
    );



//    http://shamrockuat.dweb.in/api/MyLifeStylePlan/UpdateWeekDays
    @POST("api/MyLifeStylePlan/UpdateWeekDays")
    Call<ClsWeeDaysResponse> UpdateWeekDays(@Body ClsUpdateWeekDaysRequest lifeStylePlanPost
    );


    @POST("api/MyLifeStylePlan/UpdateWeeklyList")
    Call<ClsSuccess> insertLifeStylePlanShoutcut(@Body ArrayList<ClsPostShotcutWeekly> lifeStylePlanPost
    );
}
