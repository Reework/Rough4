package com.shamrock.reework.activity.NewHealthProfile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.HealthModule.activity.StateListAdapter;
import com.shamrock.reework.activity.HealthModule.dialog.NewCityDialog;
import com.shamrock.reework.activity.HealthModule.dialog.NewStateDialog;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.activity.newregistrationmodule.newregistration.NewHealthparamterActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.CityRequest;
import com.shamrock.reework.api.request.StateRequest;
import com.shamrock.reework.api.response.City;
import com.shamrock.reework.api.response.CityResponse;
import com.shamrock.reework.api.response.Country;
import com.shamrock.reework.api.response.CountryResponse;
import com.shamrock.reework.api.response.State;
import com.shamrock.reework.api.response.StateResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.dialog.CityDialog;
import com.shamrock.reework.dialog.StateDialog;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewHealthProfileActivity extends AppCompatActivity implements NewStateDialog.GetStateListener, NewCityDialog.GetCityListener {

    private CommonService commonService;
    ArrayList<Country> countryList;
    ArrayList<State> stateList;
    ArrayList<City> cityList;
    private ListView lst_state;
    TextView txt_select_state,txt_select_city,txt_btn_next,txt_btn_back;
    EditText edt_height;
    private NewStateDialog stateDialog;
    TextView txt_btn_pound,txt_btn_kg;
    private NewCityDialog cityDialog;
    private ViewFlipper vp_health;
    EditText edt_pincode;
    int pageCount=0;
    TextView txt_btn_inches,txt_btn_feet;


    private String strSubmitState;
    private String strSubmitCity;
    private String strSubmitPinCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_health_profile);
        commonService = Client.getClient().create(CommonService.class);
        initView();
        setNextClick();
        setBackClick();
        callStateApi(101);
        setSecondPageData();
        setThirdPageData();
        setPinCodeData();
        setWeightData();
        setHeightData();




    }

    private void setHeightData() {
        txt_btn_feet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_btn_feet.setBackground(getResources().getDrawable(R.drawable.squarebtn_new));
                txt_btn_inches.setBackground(getResources().getDrawable(R.drawable.squarebtn_unselect));

            }
        });
        txt_btn_inches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_btn_inches.setBackground(getResources().getDrawable(R.drawable.squarebtn_new));
                txt_btn_feet.setBackground(getResources().getDrawable(R.drawable.squarebtn_unselect));
            }
        });
    }

    private void setWeightData() {
        txt_btn_kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_btn_kg.setBackground(getResources().getDrawable(R.drawable.squarebtn_new));
                txt_btn_pound.setBackground(getResources().getDrawable(R.drawable.squarebtn_unselect));

            }
        });
        txt_btn_pound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_btn_pound.setBackground(getResources().getDrawable(R.drawable.squarebtn_new));
                txt_btn_kg.setBackground(getResources().getDrawable(R.drawable.squarebtn_unselect));
            }
        });
    }

    private void setBackClick() {
        txt_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCount--;
                vp_health.setDisplayedChild(pageCount);
            }
        });
    }

    private void setPinCodeData() {
        strSubmitPinCode=edt_pincode.getText().toString();
    }

    private void setNextClick() {
        txt_btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vp_health.getDisplayedChild()==1){
                    if (txt_select_state.getText().toString().equalsIgnoreCase("Select State")){
                        Toast.makeText(NewHealthProfileActivity.this, "Please select State", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                pageCount++;
                vp_health.setDisplayedChild(pageCount);




            }
        });
    }

    private void setThirdPageData() {
        txt_select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm;
                Bundle bundle;
                fm = getSupportFragmentManager();
                cityDialog = new NewCityDialog();
                bundle = new Bundle();
                bundle.putSerializable("CITY_LIST", cityList);
                cityDialog.setArguments(bundle);
                cityDialog.show(fm, "CITY_FRAGMENT");
            }
        });
    }

    private void setSecondPageData() {
        txt_select_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm;
                Bundle bundle;
                fm = getSupportFragmentManager();
                stateDialog = new NewStateDialog();
                bundle = new Bundle();
                bundle.putSerializable("STATE_LIST", stateList);
                stateDialog.setArguments(bundle);
                stateDialog.show(fm, "STATE_FRAGMENT");
            }
        });

    }

    private void initView() {
        txt_select_state=findViewById(R.id.txt_select_state);
        txt_select_city=findViewById(R.id.txt_select_city);
        txt_btn_next=findViewById(R.id.txt_btn_next);
        txt_btn_back=findViewById(R.id.txt_btn_back);
        vp_health=findViewById(R.id.vp_health);
        edt_pincode=findViewById(R.id.edt_pincode);
        txt_btn_kg=findViewById(R.id.txt_btn_kg);
        txt_btn_pound=findViewById(R.id.txt_btn_pound);
        txt_btn_inches=findViewById(R.id.txt_btn_inches);
        txt_btn_feet=findViewById(R.id.txt_btn_feet);
    }

    private void callStateApi(int countryId) {
        StateRequest request = new StateRequest();
        request.setCountryId(101);

        Call<StateResponse> call = commonService.getStateList(request);
        call.enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                stateList = response.body().getData();

                if (stateList == null) {




                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {
            }
        });

    }
    private void callCountryApi() {

        Call<CountryResponse> call = commonService.getCountryList();
        call.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.code() == Client.RESPONSE_CODE_OK) {
                    CountryResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1) {
                        countryList = response.body().getData();


                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
            }

            private void ShowRetryBar(CountryResponse listResponse) {
                String strMessage;
                if (listResponse.getMessage() != null) {
                    strMessage = listResponse.getMessage();
                } else {
                    strMessage = "Unable to load data";
                }
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                callCountryApi();
                            }
                        });
                snackbar.show();
            }
        });
    }


    @Override
    public void onSubmitStateData(State model) {
        txt_select_state.setBackground(getResources().getDrawable(R.drawable.squarebtn_new));
        txt_select_state.setTextColor(getResources().getColor(R.color.white));
        txt_select_state.setText(model.getStateName());
        strSubmitState=model.getStateName();
        callCityApi(model.getStateId());


    }
    private void callCityApi(int stateid) {
        CityRequest request = new CityRequest();
        request.setStateId(stateid);

        Call<CityResponse> call = commonService.getCityList(request);
        call.enqueue(new Callback<CityResponse>() {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response) {
                cityList = response.body().getData();

                if (cityList == null) {

                }
            }

            @Override
            public void onFailure(Call<CityResponse> call, Throwable t) {
            }
        });

    }

    @Override
    public void onSubmitCityeData(City model) {
        txt_select_city.setBackground(getResources().getDrawable(R.drawable.squarebtn_new));
        txt_select_city.setTextColor(getResources().getColor(R.color.white));
        txt_select_city.setText(model.getCityName());
        strSubmitCity=model.getCityName();

    }
}
