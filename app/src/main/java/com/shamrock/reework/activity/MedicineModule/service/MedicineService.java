package com.shamrock.reework.activity.MedicineModule.service;

import com.shamrock.reework.activity.testimals.ClsSuccessTest;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.api.request.AddMedicineRequest;
import com.shamrock.reework.api.request.DeleteMedicineRequest;
import com.shamrock.reework.api.request.EditMedicineApiRequest;
import com.shamrock.reework.api.request.MedicineListRequest;
import com.shamrock.reework.api.request.MedicineRequest;
import com.shamrock.reework.api.response.AddMedicineResponse;
import com.shamrock.reework.api.response.DeleteMedicineResponse;
import com.shamrock.reework.api.response.EditMedicineResponse;
import com.shamrock.reework.api.response.MedicineListResponse;
import com.shamrock.reework.api.response.MedicineResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface MedicineService
{





    @Multipart
    @POST("api/Testimonial/SaveTestimonial")
    Call<ClsSuccessTest> newPosttestominalss
            (
                    @Part("Id") RequestBody id,
                    @Part("Testimonial") RequestBody Testimonial,
                    @Part("UserId") RequestBody ReeworkerId,
                    @Part("filetype") RequestBody filetype,
                        @Part MultipartBody.Part photo



            );












    @Multipart
    @POST("api/MyRecipe/SaveRecipe")
    Call<ClsEditWaterResonse> saveEditRecipe(@Part("Recipe") RequestBody uesreIID,@Part MultipartBody.Part photo);


    @POST("api/Medicine/MedicineList")
    Call<MedicineListResponse> getMedicinesList(@Body MedicineListRequest medicineListRequest);

    @POST("api/Medicine/ShowMyMedicine")
    Call<MedicineListResponse> downloadMedicines(@Body MedicineRequest medicineRequest);

    @POST("api/Medicine/AddMyMedicine")
    Call<AddMedicineResponse> addMedicines(@Body AddMedicineRequest addMedicineRequest);

    @Multipart
    @POST("api/Medicine/UpdateMedicine")
//    Call<AddMedicineResponse> UpdateMedicine(@Part MultipartBody.Part photo,@Part("Medicine") RequestBody params);
    Call<AddMedicineResponse> UpdateMedicine(@Part MultipartBody.Part photo, @Part("Medicine") RequestBody uesreIID);


    @POST("api/Medicine/MedicineEdit")
    Call<EditMedicineResponse> editMedicines(@Body EditMedicineApiRequest request);

    @POST("api/Medicine/MedicineDelete")
    Call<DeleteMedicineResponse> deleteMedicines(@Body DeleteMedicineRequest request);


    @POST("/api/Medicine/ShowMyMedicine")
    Call<DeleteMedicineResponse> ShowMyMedicine(@Body DeleteMedicineRequest request);
}
