package com.shamrock.reework.activity.BeforeAfterModule.service;

import com.shamrock.reework.activity.BloodTestModule.pojo.ClsGetotherreportmain;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsScheduleData;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsUplaodReportSucess;
import com.shamrock.reework.activity.sleepmodule.ClsEditSleepResonse;
import com.shamrock.reework.activity.tips.ClsSleepTips;
import com.shamrock.reework.api.request.TempReqForBeforeAfter;
import com.shamrock.reework.api.response.BeforeAfterResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface BeforeAfterService
{

//    http://shamrockuat.dweb.in/api/Report/OtherReportList?ReeworkerId=3040
@GET("api/Report/OtherReportList")
Call<ClsGetotherreportmain> getOtherReportList(@Query("ReeworkerId") int id);


    @Multipart
    @POST("api/BeforeAfter/BeforeAfter")
    Call<BeforeAfterResponse> Upload
            (
                    @Part MultipartBody.Part photo,
                    @Part("UserID") RequestBody uesreIID,
                    @Part("IsAfter") RequestBody isAfter,
                    @Part("ImageName") RequestBody imgName,
                    @Part("UploadedOn") RequestBody uploadDate
            );


//    ReportFile:  multipart-image
//    Report: {"Id":"0","ReeworkerId":"3040","ReportName":"logo report","ReportFilePath":""}

    @Multipart
    @POST("api/Report/UploadOtherReport")
    Call<ClsUplaodReportSucess> UploadReportFile
            (
                    @Part MultipartBody.Part photo,
                    @Part("Report") RequestBody uesreIID



            );
//    http://shamrockuat.dweb.in/api/Report/UploadOtherReport


    /* Temp request */
    @POST("api/BeforeAfter/GetUserBeforeAfterImages")
    Call<BeforeAfterResponse> getAllImages(@Body TempReqForBeforeAfter request);


//    http://localhost:62096//api/BeforeAfter/DeleteUserBeforeAfters?Id=2183
    @GET("api/BeforeAfter/DeleteUserBeforeAfters")
    Call<ClsEditSleepResonse> getDeleteUserBeforeAfters(@Query("Id") int id);


    /*@Multipart
    @POST("api/BeforeAfter/BeforeAfter")
    Call<TempRespForBeforeAfter> Upload
    (
            @Part MultipartBody.Part photo,
            @Query("UserID") int uesreIID,
            @Query("IsAfter") boolean isAfter,
            @Query("ImageName") String imgName
    );*/
}
