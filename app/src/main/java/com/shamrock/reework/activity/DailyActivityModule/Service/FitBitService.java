package com.shamrock.reework.activity.DailyActivityModule.Service;

import com.shamrock.reework.model.GetAllFitbitActivitiesRes;
import com.shamrock.reework.model.TokenResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FitBitService {


    //for fitbit API's
    //for this type of url
    //https://api.fitbit.com/1/user/7P9TX5/activities/date/2019-07-08.json
//    https://api.fitbit.com/1/user/{id}/activities/date/{date}.json
//    hi url aahe get Url and header mdhe authorization code takaych debug krun auth code de

    @GET("1/user/{id}/activities/date/{date}.json")
    Call<GetAllFitbitActivitiesRes> getAllActivities(
            @Header("Authorization") String accessToken,
            @Path("id") String id,
            @Path("date")String data);

/*    @GET("1/user/{id}/activities/date/{date}.json")
    Call<ResponseBody> getAllActivities(
            @Header("Authorization") String accessToken,
            @Path("id") String id,
            @Path("date")String data);*/
 /*   @GET("1/user/{id}/activities/recent.json")
    Call<ResponseBody> getAllActivities(
            @Header("Authorization") String accessToken,
            @Path("id") String id);
           *//* @Path("date")String data);*/


/*    https://api.fitbit.com/1/user/-/activities/recent.json*/
  /*  https://api.fitbit.com/1/user/-/activities/frequent.json*/

    //for Refresh Token
    @FormUrlEncoded
    @POST("oauth2/token")
    Call<TokenResponse> getRefreshToken(@Header("Authorization") String accessToken, @Field("grant_type") String grant_type, @Field("refresh_token")String refresh_token);



    @FormUrlEncoded
    @POST("oauth2/token")
    Call<TokenResponse> getToken(@Header("Authorization") String accessToken, @Field("client_id") String clientId, @Field("grant_type")String grantType, @Field("redirect_uri")String redirect_uri, @Field("code")String code);

}
