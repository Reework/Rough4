package com.shamrock.reework.activity.ReminderModule.service;

import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.ReminderDeleteRequest;
import com.shamrock.reework.api.request.ReminderEditRequest;
import com.shamrock.reework.api.request.ScheduleReminderRequest;
import com.shamrock.reework.api.response.ReminderDeleteResponse;
import com.shamrock.reework.api.response.ReminderEditResponse;
import com.shamrock.reework.api.response.ReminderResponse;
import com.shamrock.reework.api.response.ReminderScheduleResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RemindersService
{
    @POST("api/Reminders/Get_AllRemindersList")
    Call<ReminderResponse> getAllReminder(@Body GetAllAppointmentReq sheduleRequest);

    @POST("api/Reminders/DeleteReminder")
    Call<ReminderDeleteResponse> deleteReminder(@Body ReminderDeleteRequest deleteRequest);

    @POST("api/Reminders/PostReminders")
    Call<ReminderScheduleResponse> setReminder(@Body ScheduleReminderRequest scheduleReminderRequest);

    @POST("api/Reminders/ModifyReminder")
    Call<ReminderEditResponse> editReminder(@Body ReminderEditRequest reminderEditRequest);
}
