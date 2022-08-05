package com.shamrock.reework.activity.BloodTestModule.service;

import com.shamrock.reework.activity.BloodTestModule.activity.GetBloodTestReportRes;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsGetotherreportmain;
import com.shamrock.reework.activity.BloodTestModule.pojo.ClsScheduleData;
import com.shamrock.reework.activity.ReminderModule.model.ClsReminderMaster;
import com.shamrock.reework.activity.testimals.ClsMyTestimonialsMain;
import com.shamrock.reework.activity.testimals.ClsPostTestominals;
import com.shamrock.reework.activity.testimals.ClsSuccessTest;
import com.shamrock.reework.activity.testimals.ClsTestimalMain;
import com.shamrock.reework.api.request.BcaRequest;
import com.shamrock.reework.api.request.BloodTestReportRequest;
import com.shamrock.reework.api.request.ReetestRequest;
import com.shamrock.reework.api.response.BCAResponce;
import com.shamrock.reework.api.response.BloodTestReportResponse;
import com.shamrock.reework.api.response.MyProfileResponse;
import com.shamrock.reework.api.response.ReetestResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ReeTestService
{

//    http://shamrockuat.dweb.in/api/Report/ReeScoreReprtCompare?UserId=3040&ReportTypeId=2&From=2020-04-22&To=2020-12-1

    @GET("api/Report/ReeScoreReprtCompare")
    Call<ResponseBody> getReeScoreReprtCompare(@Query("UserId") int id, @Query("ReportTypeId") int ReportTypeId, @Query("From") String From, @Query("To") String To);





//    http://shamrockuat.dweb.in/api/Report/TestReportCompare?UserId=3040&ReportTypeId=2&From=2019-01-01&To=2021-01-01

    @GET("api/Report/TestReportCompare")
    Call<ResponseBody> getCompareReportList(@Query("UserId") int id, @Query("ReportTypeId") int ReportTypeId, @Query("From") String From, @Query("To") String To);


//    http://shamrockuat.dweb.in/api/HealthMonitor/ReemonitorSummary?UserId=3040&ReportTypeId=1&From=2010-01-01&To=2021-01-01
    @GET("api/HealthMonitor/ReemonitorSummary")
    Call<ResponseBody> getReemonitorCompareReportList(@Query("UserId") int id, @Query("ReportTypeId") int ReportTypeId, @Query("From") String From, @Query("To") String To);



    @GET("api/Testimonial/GetTestimonial")
    Call<ClsMyTestimonialsMain> getMyTestominals(@Query("ReeworkerId") int id);


    @GET("api/Testimonial/Testimonials")
    Call<ClsTestimalMain> getTestimalList();



    @POST("api/Testimonial/SaveTestimonial")
    Call<ClsSuccessTest> posttestominals(@Body ClsPostTestominals reetestRequest);





    @Multipart
    @POST("api/Testimonial/SaveTestimonial")
    Call<ClsSuccessTest> newPosttestominals
            (
                    @Part("Id") RequestBody id,
                    @Part("Testimonial") RequestBody Testimonial,
                    @Part("UserId") RequestBody ReeworkerId,
                    @Part("filetype") RequestBody filetype


            );



//    @Part MultipartBody.Part photo



    //    http://shamrockuat.dweb.in/api/Report/OtherReportList?ReeworkerId=3040
    @GET("api/Report/OtherReportList")
    Call<ClsGetotherreportmain> getOtherReportList(@Query("ReeworkerId") int id);

    @POST("api/UserBloodTestSchedule/PostBloodSchedule")
    Call<ReetestResponse> scheduleBloodTest(@Body ReetestRequest reetestRequest);

    @POST("api/UserBloodTestSchedule/BloodTestReport")
    Call<BloodTestReportResponse> getBloodTestReports(@Body BloodTestReportRequest reetestRequest);

    @POST("api/UserBloodTestSchedule/Get_BloodReportDetails")
    Call<BCAResponce> getBloodReport(@Body BcaRequest bcaRequest);


    @GET("api/UserBloodTestSchedule/GetTestReport")
    Call<BCAResponce> getAllBloodReportNewHistory(@Query("UserId") String id);


//    http://shamrockreework.dweb.in/api/UserBloodTestSchedule/GetScheduledBloodTest?UserId=3040

    @GET("api/UserBloodTestSchedule/GetScheduledBloodTest")
    Call<ClsScheduleData> getAlreadyScheduledBloodTest(@Query("UserId") String id);

    @GET("api/UserBloodTestSchedule/GetScheduledHistories")
    Call<GetBloodTestReportRes> getAllBloodReportHistory(@Query("UserId") String id);

    @GET("api/Master/ReminderActivityMaster")
    Call<ClsReminderMaster> getAllRemiderActivityMaster();

}
