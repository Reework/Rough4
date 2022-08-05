package com.shamrock.reework.activity.reevaluate;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.service.HealthParametersService;
import com.shamrock.reework.activity.HealthModule.service.HealthRequest;
import com.shamrock.reework.activity.HealthModule.service.UserHealthResponse;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.ProfileDataRequest;
import com.shamrock.reework.api.response.FoodCulture;
import com.shamrock.reework.api.response.FoodCulturesResponse;
import com.shamrock.reework.api.response.FoodType;
import com.shamrock.reework.api.response.FoodTypesResponse;
import com.shamrock.reework.api.response.MedicalCondition;
import com.shamrock.reework.api.response.MedicalConditionsResponse;
import com.shamrock.reework.api.response.ProfileDataResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class ReevaluateActivity extends AppCompatActivity {
    ImageView logo;
    RegistrationService regService;
    CommonService commonService;
    SessionManager sessionManager;
    Utils utils;
    private HealthParametersService healthParametersService;
    private ArrayList<UserHealthResponse.Data> arylst_healthdata;
    private List<FoodType> foodTypeList;
    private List<FoodCulture> foodCultureList;
    private List<MedicalCondition> medicalConditionList;
    Toolbar toolbar;
    LinearLayout ll_add;

    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reevaluate);
        setToolBar();
        logo=findViewById(R.id.logo);
        ll_add=findViewById(R.id.ll_add);
        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);
        sessionManager = new SessionManager(ReevaluateActivity.this);
        healthParametersService = Client.getClient().create(HealthParametersService.class);
        foodTypeList=new ArrayList<>();
        utils = new Utils();

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        callGetProfileDataApi(userID);

    }
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("REEvaluate");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }



    private void callGetProfileDataApi(int userId)
    {


        ProfileDataRequest request = new ProfileDataRequest();
        request.setUserid(userId);

        Call<ProfileDataResponse> call = regService.getProfileData(request);
        call.enqueue(new Callback<ProfileDataResponse>()
        {
            @Override
            public void onResponse(Call<ProfileDataResponse> call, Response<ProfileDataResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ProfileDataResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        try {
                            ProfileDataResponse.DataResponse data = listResponse.getData();
                            String USER_ADDRESS = "";
                            String USER_F_NAME = data.getFirstName();
                            String USER_L_NAME = data.getLastName();
                            String USER_MOBILE_NO = data.getMobileNo();
                            String USER_DOB = data.getDOB();
                            if (data.getAddress() != null) {
                                USER_ADDRESS = data.getAddress();

                            }
                            String USER_Image = data.getImageUrl();
                            TextView txt_full_name = findViewById(R.id.txt_full_name);
                            txt_full_name.setText(USER_F_NAME + " " + USER_L_NAME);

                            TextView txt_dob = findViewById(R.id.txt_dob);
                            txt_dob.setText(USER_DOB);
                            TextView txt_mobile = findViewById(R.id.txt_mobile);
                            txt_mobile.setText(USER_MOBILE_NO);


                            if (USER_ADDRESS.isEmpty()) {
                                ll_add.setVisibility(View.GONE);
                            } else {
                                ll_add.setVisibility(View.VISIBLE);

                            }
                            TextView txt_Address = findViewById(R.id.txt_Address);
                            if (USER_ADDRESS != null)
                                txt_Address.setText(USER_ADDRESS);

                            setProfileImage(USER_Image);
                            callHealthParametergetApi();

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileDataResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });

    }

    private void callHealthParametergetApi() {

        HealthRequest healthRequest=new HealthRequest();
        int userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        Log.e("idd", String.valueOf(userID));

        healthRequest.setUserId(userID);


        utils.showProgressbar(ReevaluateActivity.this);

        Call<UserHealthResponse> call = healthParametersService.getHealthparametdata(healthRequest);
        call.enqueue(new Callback<UserHealthResponse>()
        {
            @Override
            public void onResponse(Call<UserHealthResponse> call, retrofit2.Response<UserHealthResponse> response)
            {

                utils.hideProgressbar();



                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        UserHealthResponse moodResponse = response.body();
                        if (moodResponse.getCode()!=null){

                            if (moodResponse.getCode().equals("1")){
                                arylst_healthdata=new ArrayList<>();
                                if (moodResponse.getData()!=null){
                                    arylst_healthdata.add(moodResponse.getData());
                                    setHealthData(arylst_healthdata);
                                }


                            }else {
                                arylst_healthdata=new ArrayList<>();
                            }



                        }else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<UserHealthResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });

    }


    private void callFoodCulturesApi(final ArrayList<UserHealthResponse.Data> arylst_my_health_data, final TextView txt_fc)
    {


        Call<FoodCulturesResponse> call = healthParametersService.getFoodCulturesList();
        call.enqueue(new Callback<FoodCulturesResponse>()
        {
            @Override
            public void onResponse(Call<FoodCulturesResponse> call, Response<FoodCulturesResponse> response)
            {
                utils.hideProgressbar();

                int statusCode = response.code();
                if (response.body() != null)
                {
                    foodCultureList = response.body().getData();



                    if (arylst_my_health_data!=null){

                        try {
                            if (!arylst_my_health_data.isEmpty()){
                                if (arylst_my_health_data.get(0).getFoodcultureID()!=null){
                                    if (!arylst_my_health_data.get(0).getFoodcultureID().isEmpty()){


                                        for (int i = 0; i <foodTypeList.size() ; i++) {

                                            if (arylst_my_health_data.get(0).getFoodcultureID().equalsIgnoreCase(String.valueOf(foodCultureList.get(i).getFoodcultureID()))){
                                                txt_fc.setText(foodCultureList.get(i).getFoodculture());
                                                break;
                                            }
                                        }
                                    }


                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    callHealthConditionsApi(arylst_my_health_data);


                }
                else
                {
                }

            }

            @Override
            public void onFailure(Call<FoodCulturesResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
            }
        });
    }
    private void setHealthData(ArrayList<UserHealthResponse.Data> arylst_my_health_data) {

        try {

            TextView txt_healt_wellness = findViewById(R.id.txt_healt_wellness);
            TextView txt_getWeight_Control = findViewById(R.id.txt_getWeight_Control);
            TextView txt_getWeight_Gain = findViewById(R.id.txt_getWeight_Gain);
            TextView txt_getEasy_Monitoring_of_Body_Parameters = findViewById(R.id.txt_getEasy_Monitoring_of_Body_Parameters);
            TextView txt_body_shape = findViewById(R.id.txt_body_shape);
            TextView txt_wt = findViewById(R.id.txt_wt);
            TextView txt_ht = findViewById(R.id.txt_ht);
            TextView txt_fp = findViewById(R.id.txt_fp);
            TextView txt_fc = findViewById(R.id.txt_fc);
            TextView txt_as = findViewById(R.id.txt_as);
            TextView txt_bt = findViewById(R.id.txt_bt);
            TextView txt_sleep = findViewById(R.id.txt_sleep);


            try {
                if (arylst_my_health_data.get(0).getHealth_And_Wellness() != null) {
                    if (arylst_my_health_data.get(0).getHealth_And_Wellness().equalsIgnoreCase("1")) {
                        txt_healt_wellness.setText("Yes");
                    } else {
                        txt_healt_wellness.setText("No");

                    }
                }


                if (arylst_my_health_data.get(0).getWeight_Control() != null) {
                    if (arylst_my_health_data.get(0).getWeight_Control().equalsIgnoreCase("1")) {
                        txt_getWeight_Control.setText("Yes");
                    } else {
                        txt_getWeight_Control.setText("No");

                    }
                }


                if (arylst_my_health_data.get(0).getWeight_Gain() != null) {
                    if (arylst_my_health_data.get(0).getWeight_Gain().equalsIgnoreCase("1")) {
                        txt_getWeight_Gain.setText("No");
                    } else {
                        txt_getWeight_Gain.setText("No");

                    }

                }


                if (arylst_my_health_data.get(0).getEasy_Monitoring_of_Body_Parameters() != null) {
                    if (arylst_my_health_data.get(0).getEasy_Monitoring_of_Body_Parameters().equalsIgnoreCase("1")) {
                        txt_getEasy_Monitoring_of_Body_Parameters.setText("Yes");
                    } else {
                        txt_getEasy_Monitoring_of_Body_Parameters.setText("No");

                    }

                }


                if (arylst_my_health_data.get(0).getBody_shape() != null) {
                    if (arylst_my_health_data.get(0).getBody_shape().equalsIgnoreCase("1")) {
                        txt_body_shape.setText("Pear");

                    } else {
                        txt_body_shape.setText("Apple");


                    }

                }


                try {
                    if (arylst_my_health_data.get(0).getWeight() != null) {
                        double wt = Double.parseDouble(arylst_my_health_data.get(0).getWeight());
                        String newwt = String.valueOf(Math.round(wt));

                        txt_wt.setText(newwt);

                    }
                } catch (NumberFormatException e) {
                    txt_wt.setText(arylst_my_health_data.get(0).getWeight());
                    e.printStackTrace();

                }


                if (arylst_my_health_data.get(0).getHeight() != null) {
                    txt_ht.setText(arylst_my_health_data.get(0).getHeight());

                }

                callFoodTypesApi(arylst_my_health_data, txt_fp, txt_fc);


                if (arylst_my_health_data.size() != 0) {
                    if (arylst_my_health_data.get(0).getActivityStatus() != null) {

                        String activitySatatus = arylst_my_health_data.get(0).getActivityStatus();
                        switch (activitySatatus) {

                            case "1":
                                txt_as.setText("Sedentary");
                                break;
                            case "2":
                                txt_as.setText("Mildly Active");

                                break;
                            case "3":
                                txt_as.setText("Moderately Active");

                                break;
                            case "4":
                                txt_as.setText("Highly Active");

                                break;
                        }


                    }


                    if (arylst_my_health_data.get(0).getBody_Type() != null) {

                        String bodytype = arylst_my_health_data.get(0).getBody_Type();

                        switch (bodytype) {
                            case "1":
                                txt_bt.setText("Mesomorph");
                                break;
                            case "2":
                                txt_bt.setText("Ectomorph");

                                break;
                            case "3":
                                txt_bt.setText("Endomorph");

                                break;


                        }


                    }


                    if (arylst_my_health_data.get(0).getAvgsleep() != null) {

                        int pos = Integer.parseInt(arylst_my_health_data.get(0).getAvgsleep()) - 1;
                        txt_sleep.setText(String.valueOf(pos) + " " + "Hours");
                    }


                    if (arylst_my_health_data.get(0).getWaterintake() != null) {

                        TextView txt_water = findViewById(R.id.txt_water);
                        int pos = Integer.parseInt(arylst_my_health_data.get(0).getWaterintake()) - 1;
                        txt_water.setText(String.valueOf(pos) + " " + "Glasses");
                    }


                    if (arylst_my_health_data.get(0).getDrink() != null) {
                        TextView txt_alcohol = findViewById(R.id.txt_alcohol);


                        switch (arylst_my_health_data.get(0).getDrink()) {


                            case "1":
                                txt_alcohol.setText("No");
                                break;


                            case "2":
                                txt_alcohol.setText("1-2 times a week");

                                break;

                            case "3":
                                txt_alcohol.setText("3-4 times a week");

                                break;
                        }
                    }


                    if (arylst_my_health_data.get(0).getSmoke() != null) {
                        TextView txt_smoke = findViewById(R.id.txt_smoke);
                        switch (arylst_my_health_data.get(0).getSmoke()) {


                            case "2":
                                txt_smoke.setText("No");
                                break;


                            case "1":
                                txt_smoke.setText("Yes");
                                break;

                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void callHealthConditionsApi(final ArrayList<UserHealthResponse.Data> arylst_my_health_data)
    {


        Call<MedicalConditionsResponse> call = healthParametersService.getMedicalConditionList();
        call.enqueue(new Callback<MedicalConditionsResponse>()
        {
            @Override
            public void onResponse(Call<MedicalConditionsResponse> call, Response<MedicalConditionsResponse> response)
            {
                try {
                    int statusCode = response.code();
                    if (response.body() != null) {
                        medicalConditionList = response.body().getData();
                        ArrayList<String> kaminey_dost_array_list = new ArrayList<>();
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i = 0; i < medicalConditionList.size(); i++) {
                            kaminey_dost_array_list.add(medicalConditionList.get(i).getMedicalCondition());

                        }

                        if (arylst_my_health_data != null) {

                            if (!arylst_my_health_data.isEmpty()) {
                                if (arylst_my_health_data.get(0).getMedicalConditionID() != null) {


                                    List<String> list = new ArrayList<String>(Arrays.asList(arylst_my_health_data.get(0).getMedicalConditionID().split(",")));

                                    for (int i = 0; i < medicalConditionList.size(); i++) {

                                        for (int j = 0; j < list.size(); j++) {
                                            if (list.get(j).toString().trim().equalsIgnoreCase(String.valueOf(medicalConditionList.get(i).getMedicalConditionID().toString().trim()))) {
                                                stringBuilder.append(medicalConditionList.get(i).getMedicalCondition() + ",");
                                            }


                                        }


                                    }
                                    TextView txt_mc = findViewById(R.id.txt_mc);
                                    if (stringBuilder.length() > 1) {
                                        try {
                                            txt_mc.setText(stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), ""));

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }
                            }
                        }

                    } else {
                    }
                    utils.hideProgressbar();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MedicalConditionsResponse> call, Throwable t)
            {
                // Log error here since request failed
//                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });
    }


    private void callFoodTypesApi(final ArrayList<UserHealthResponse.Data> arylst_my_health_data, final TextView txt_fp, final TextView txt_fc)
    {

        Call<FoodTypesResponse> call = healthParametersService.getFoodTypesList();
        call.enqueue(new Callback<FoodTypesResponse>()
        {
            @Override
            public void onResponse(Call<FoodTypesResponse> call, Response<FoodTypesResponse> response)
            {
                try {
                    if (response.body() != null) {

                        foodTypeList = response.body().getData();


                        if (arylst_my_health_data != null) {
                            if (!arylst_my_health_data.isEmpty()) {
                                try {
                                    if (arylst_my_health_data.get(0).getFoodtypeID() != null) {

                                        if (!arylst_my_health_data.get(0).getFoodtypeID().isEmpty()) {

                                            for (int i = 0; i < foodTypeList.size(); i++) {

                                                if (arylst_my_health_data.get(0).getFoodtypeID().equalsIgnoreCase(String.valueOf(foodTypeList.get(i).getFoodtypeID()))) {

                                                    txt_fp.setText(foodTypeList.get(i).getFoodtype());
                                                    break;
                                                }
                                            }
                                        }


                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        try {

                            callFoodCulturesApi(arylst_my_health_data, txt_fc);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                    }
                    utils.hideProgressbar();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FoodTypesResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
            }
        });
    }

    private void setProfileImage(String USER_Image)
    {

        try {


            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.ic_profile_pic_bg)
                    .error(R.drawable.ic_profile_pic_bg)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .priority(Priority.HIGH);

            if (isValidContextForGlide(ReevaluateActivity.this)) {
                Glide.with(ReevaluateActivity.this)
                        .load(USER_Image)
                        .apply(options.circleCrop())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                           DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .into(logo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
