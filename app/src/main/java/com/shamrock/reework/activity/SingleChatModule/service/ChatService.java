package com.shamrock.reework.activity.SingleChatModule.service;

import com.shamrock.reework.activity.AnalysisModule.activity.coloriesanalysis.ClsCalorianalysis;
import com.shamrock.reework.activity.community.ChatData;
import com.shamrock.reework.activity.community.ClsGetCommunityChatMain;
import com.shamrock.reework.api.request.SaveSingleChatRequest;
import com.shamrock.reework.api.request.SingleChatRequest;
import com.shamrock.reework.api.response.FoodCulturesResponse;
import com.shamrock.reework.api.response.SingleChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ChatService
{
    @GET("api/ChatNotification/Get_AllChatList")
    Call<SingleChatResponse> getAllChat(@Query("SenderId") int id, @Query("UserId") int ReeworkerId);
//    Call<SingleChatResponse> getAllChat(@Body SingleChatRequest singleChatRequest);



//    https://reeworkmindbody.co.in/api/ChatNotification/Get_AllChatList?SenderId=3037&UserId=7225





//    http://localhost:62096/api/chat/ChatHistory?ReecoachId=3037&ReeworkerId=3040
@GET("api/chat/ChatHistory")
Call<SingleChatResponse> getChatHistory(@Query("ReecoachId") int id, @Query("ReeworkerId") int ReeworkerId);


    //    http://localhost:62097/api/chat/ChatHistory?ReecoachId=3037&ReeworkerId=3040
    @GET("api/chat/AdminChat")
    Call<SingleChatResponse> getAdminChatHistory(@Query("ReecoachId") int id, @Query("ReeworkerId") int ReeworkerId);



    @POST("api/ChatNotification/Save_Chat")
    Call<SingleChatResponse> saveChat(@Body SaveSingleChatRequest saveSingleChatRequest);


    @GET("api/ChatNotification/SendCommunityMesage")
    Call<ChatData> setchatApi(@Query("UserId") int id, @Query("Message") String message);

//    http://shamrockuat.dweb.in/api/ChatNotification/GetCommunityMesage

    @GET("api/ChatNotification/GetCommunityMesage")
    Call<ClsGetCommunityChatMain> getChatCommunityMesage();



//    http://shamrockuat.dweb.in/api/ChatNotification/SendCommunityMesage?UserId=3040&Mesage=test
}
