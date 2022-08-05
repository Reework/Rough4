package com.shamrock.reework.activity.MyProfileModule.service;

import com.shamrock.reework.api.response.MyProfileResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface MyProfileService
{
    @Multipart
    @POST("api/Registration/UserprofileImageUpload")
    Call<MyProfileResponse> Upload
            (
                    @Part("UserId") RequestBody uesreIID,
                    @Part MultipartBody.Part photo,
                    @Part("photo") RequestBody imgName
            );

}
