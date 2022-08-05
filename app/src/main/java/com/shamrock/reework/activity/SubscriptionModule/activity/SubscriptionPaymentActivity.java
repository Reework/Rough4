package com.shamrock.reework.activity.SubscriptionModule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.SubscriptionModule.adapter.SubscribeFeatureAdapter;
import com.shamrock.reework.activity.SubscriptionModule.dialog.PlanModel;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.CityRequest;
import com.shamrock.reework.api.request.StateRequest;
import com.shamrock.reework.api.request.SubscriptionRequest;
import com.shamrock.reework.api.response.City;
import com.shamrock.reework.api.response.CityResponse;
import com.shamrock.reework.api.response.Country;
import com.shamrock.reework.api.response.CountryResponse;
import com.shamrock.reework.api.response.Language;
import com.shamrock.reework.api.response.LanguageResponse;
import com.shamrock.reework.api.response.State;
import com.shamrock.reework.api.response.StateResponse;
import com.shamrock.reework.api.response.SubscriptionFeaturesResponse;
import com.shamrock.reework.api.response.SubscriptionResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.activity.SubscriptionModule.dialog.PlanDialog;
import com.shamrock.reework.dialog.CityDialog;
import com.shamrock.reework.dialog.CountryDialog;
import com.shamrock.reework.dialog.LanguageDialog;
import com.shamrock.reework.dialog.StateDialog;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPaymentActivity extends AppCompatActivity
                                         implements View.OnClickListener,
                                         CountryDialog.GetCountryListener,
                                         StateDialog.GetStateListener,
                                         CityDialog.GetCityListener,
                                         LanguageDialog.GetLanguageListener,
                                         PlanDialog.GetPlanListener
{
    private static final String TAG = "SubPaymentActivity";
    Context context;
    Toolbar toolbar;
    private EditText etAddress;
    Button btnSubmitFeature;

    ArrayList<Country> countryList;
    ArrayList<State> stateList;
    ArrayList<City> cityList;
    ArrayList<Language> languageList;
    ArrayList<PlanModel> planList;

    CommonService commonService;
    RegistrationService regService;
    private Utils utils;
    private SessionManager session;
    SubscribeFeatureAdapter subscribeFeatureAdapter;
    int country_id = 0, state_id = 0, city_id = 0, plan_id, userID;
    int mPlanIdForTitle = 0;
    double dPlanPrice = 0.0;
    String address, email, lang_code, planName, lsCountry, lsCity, lsState, lsLanguage;
    String errorMsg;
    SessionManager sessionManager;

    /* Dialopgs for country, state, city and language */
    private CountryDialog countryDialog;
    private StateDialog stateDialog;
    private CityDialog cityDialog;
    private LanguageDialog languageDialog;
    private PlanDialog planDialog;
    private TextView tvCountry, tvState, tvCity, tvLanguage, tvPlan, tvPrice;

    ArrayList<HashMap<Integer,SubscriptionFeaturesResponse.PlanHeadingList>> hashPlanPriceList;

    RelativeLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_payment);
        context = SubscriptionPaymentActivity.this;

        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        sessionManager = new SessionManager(context);
        utils = new Utils();
        commonService= Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);

        userID = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        mPlanIdForTitle = sessionManager.getIntValue(SessionManager.KEY_USER_PLAN_ID);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
           try
           {
               hashPlanPriceList = (ArrayList<HashMap<Integer,SubscriptionFeaturesResponse.PlanHeadingList>>)
                       extras.getSerializable("HASH_PRICE_LIST");
           }
           catch (Exception e){e.printStackTrace();}
        }

    }

    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        if (mPlanIdForTitle == 2)
            tvTitle.setText("Upgrade");
        else
            tvTitle.setText("Membership Plan");
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    private void findViews()
    {
        btnSubmitFeature = findViewById(R.id.buttonProceedToPay);
        etAddress = (EditText) findViewById(R.id.edtText_Address);

        tvCountry = (TextView) findViewById(R.id.tvCountry);
        tvState = (TextView) findViewById(R.id.tvState);
        tvCity = (TextView) findViewById(R.id.tvCity);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        tvPlan = (TextView) findViewById(R.id.tvPlan);
        tvPrice = (TextView) findViewById(R.id.tvPrice);

        btnSubmitFeature.setOnClickListener(this);
        tvCountry.setOnClickListener(this);
        tvState.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvLanguage.setOnClickListener(this);
        tvPlan.setOnClickListener(this);

        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh= findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);

        if (Utils.isNetworkAvailable(context))
            callCountryApi();
        else
            showLayouts();

        getDataFromPrefrence();
    }

    public void showLayouts()
    {
        if (!Utils.isNetworkAvailable(context))
        {
            mainLayout.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mainLayout.setVisibility(View.VISIBLE);
            noInternetLayout.setVisibility(View.GONE);
        }
    }

    private void getDataFromPrefrence()
    {
        country_id = sessionManager.getIntValue(SessionManager.KEY_USER_COUNTRY_ID);
        state_id = sessionManager.getIntValue(SessionManager.KEY_USER_STATE_ID);
        city_id = sessionManager.getIntValue(SessionManager.KEY_USER_CITY_ID);
        lang_code = sessionManager.getStringValue(SessionManager.KEY_USER_LANGUAGE_CODE);

        lsCountry = sessionManager.getStringValue(SessionManager.KEY_USER_COUNTRY);
        lsState = sessionManager.getStringValue(SessionManager.KEY_USER_STATE);
        lsCity = sessionManager.getStringValue(SessionManager.KEY_USER_CITY);
        lsLanguage = sessionManager.getStringValue(SessionManager.KEY_USER_LANGUAGE);

        if (!TextUtils.isEmpty(lsCountry))
            tvCountry.setText(lsCountry);

        if (!TextUtils.isEmpty(lsState))
            tvState.setText(lsState);

        if (!TextUtils.isEmpty(lsCity))
            tvCity.setText(lsCity);

        if (!TextUtils.isEmpty(lsLanguage))
            tvLanguage.setText(lsLanguage);
    }

    @Override
    public void onClick(View v)
    {
        FragmentManager fm;
        Bundle bundle;

        switch (v.getId())
        {
            case R.id.tvCountry:

                fm = getSupportFragmentManager();
                countryDialog = new CountryDialog();
                bundle = new Bundle();
                bundle.putSerializable("COUNTRY_LIST", countryList);
                countryDialog.setArguments(bundle);
                countryDialog.show(fm, "COUNTRY_FRAGMENT");
                break;

            case R.id.tvState:

                if (country_id <= 0)
                    Snackbar.make(findViewById(android.R.id.content), "Select country first", Snackbar.LENGTH_SHORT).show();
                else
                {
                    fm = getSupportFragmentManager();
                    stateDialog = new StateDialog();
                    bundle = new Bundle();
                    bundle.putSerializable("STATE_LIST", stateList);
                    stateDialog.setArguments(bundle);
                    stateDialog.show(fm, "STATE_FRAGMENT");
                }
                break;

            case R.id.tvCity:

                if (state_id <= 0)
                    Snackbar.make(findViewById(android.R.id.content), "Select state first", Snackbar.LENGTH_SHORT).show();
                else
                {
                    fm = getSupportFragmentManager();
                    cityDialog = new CityDialog();
                    bundle = new Bundle();
                    bundle.putSerializable("CITY_LIST", cityList);
                    cityDialog.setArguments(bundle);
                    cityDialog.show(fm, "CITY_FRAGMENT");
                }
                break;

            case R.id.tvLanguage:

                fm = getSupportFragmentManager();
                languageDialog = new LanguageDialog();
                bundle = new Bundle();
                bundle.putSerializable("LANGUAGE_LIST", languageList);
                languageDialog.setArguments(bundle);
                languageDialog.show(fm, "LANGUAGE_FRAGMENT");
                break;

            case R.id.tvPlan:

                fm = getSupportFragmentManager();
                planDialog = new PlanDialog();
                bundle = new Bundle();
                bundle.putSerializable("HASH_PRICE_LIST", hashPlanPriceList);
                planDialog.setArguments(bundle);
                planDialog.show(fm, "PLAN_FRAGMENT");
                break;

            case R.id.buttonProceedToPay:

                String address = etAddress.getText().toString();
                boolean isValid = validateAllData(address);

                if (isValid)
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        showLayouts();
                        calSubscriptionApi(userID, plan_id);
                    }
                    else

                        showLayouts();
                }
                break;

            case R.id.btnRefresh:
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                   callCountryApi();
                }
                else
                    showLayouts();
        }
    }

    /* Get Country click here */
    @Override
    public void onSubmitCountryData(Country model)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("COUNTRY_FRAGMENT");
        if (fragment != null)
        {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null)
        {
            country_id = model.getCountryId();

            if (!TextUtils.isEmpty(model.getCountryName()))
                tvCountry.setText(model.getCountryName());
            else
                tvCountry.setText("Select country");

            if (country_id > 0)
            {
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                    callStateApi(country_id);
                }
                else
                    showLayouts();
            }
        }
    }

    /* Get state click here */
    @Override
    public void onSubmitStateData(State model)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("STATE_FRAGMENT");
        if (fragment != null)
        {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null)
        {
            state_id = model.getStateId();

            if (!TextUtils.isEmpty(model.getStateName()))
                tvState.setText(model.getStateName());
            else
                tvCountry.setText("Select state");

            if (state_id > 0)
            {
                if (Utils.isNetworkAvailable(context))
                {
                    showLayouts();
                    callCityApi(state_id);
                }
                else
                    showLayouts();
            }
        }
    }

    /* Get city click here */
    @Override
    public void onSubmitCityeData(City model)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("CITY_FRAGMENT");
        if (fragment != null)
        {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null)
        {
            city_id = model.getCityId();

            if (!TextUtils.isEmpty(model.getCityName()))
                tvCity.setText(model.getCityName());
            else
                tvCity.setText("Select city");
        }
        else
            city_id = 0;
    }

    /* Get Language click here */
    @Override
    public void onSubmitLanguageeData(Language model)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("LANGUAGE_FRAGMENT");
        if (fragment != null)
        {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null)
        {
            lang_code = model.getLangCode();

            if (!TextUtils.isEmpty(model.getLangName()))
                tvLanguage.setText(model.getLangName());
            else
                tvLanguage.setText("Select language");
        }
        else
            lang_code = "";
    }

    /* Plan Model click*/
    @Override
    public void onSubmitCityeData(PlanModel model)
    {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("PLAN_FRAGMENT");
        if (fragment != null)
        {
            DialogFragment df = (DialogFragment) fragment;
            df.dismiss();
        }

        if (model != null)
        {
            plan_id = 0;
            planName = "";
            dPlanPrice = 0.0;

            plan_id = model.getPlanId();
            planName = model.getPlanName();
            dPlanPrice = model.getdPrice();

            if (dPlanPrice > 0)
                tvPrice.setText(String.format("%.2f", dPlanPrice));
            else
                tvPrice.setText("0.00");
          /*  if (plan_id == 1)
                tvPrice.setText("0.00 INR");
            else if (plan_id == 2)
                tvPrice.setText("3650 INR");*/

            if (!TextUtils.isEmpty(planName))
                tvPlan.setText(planName);
            else
                tvPlan.setText("Select your plan");
        }
        else
        {
            plan_id = 0;
            planName = "";
            tvPrice.setText("0.00 INR");
        }
    }

    private boolean validateAllData(String address)
    {
        boolean valid = true;
        String errorMsg = "";

        if (TextUtils.isEmpty(address))
        {
            valid = false;
            errorMsg = "Please enter address";
        }

        if (valid)
        {
            if (country_id <= 0)
            {
                valid = false;
                errorMsg = "Please select country";
            }
        }

        if (valid)
        {
            if (state_id <= 0)
            {
                valid = false;
                errorMsg = "Please select state";
            }
        }

        if (valid)
        {
            if (city_id <= 0)
            {
                valid = false;
                errorMsg = "Please select City";
            }
        }

        if (valid)
        {
            if (lang_code.equalsIgnoreCase("-1"))
            {
                valid = false;
                errorMsg = "Please select Language";
            }
        }

        if (valid)
        {
            if (plan_id <= 0)
            {
                valid = false;
                errorMsg = "Please select your plan";
            }
        }

        if (!errorMsg.isEmpty())
        {
            Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    private void calSubscriptionApi(int userid, final int planid)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        SubscriptionRequest request = new SubscriptionRequest();
        request.setPlanID(planid);
        request.setUserid(userid);

        Call<SubscriptionResponse> call = regService.subResponse(request);

        call.enqueue(new Callback<SubscriptionResponse>()
        {
            @Override
            public void onResponse(Call<SubscriptionResponse> call, Response<SubscriptionResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    SubscriptionResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        //Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        if (planid == 2)
                            sessionManager.setBooleanValue(SessionManager.KEY_USER_IS_SUBSCRIBE_FOR_FREE, true);

                        sessionManager.setIntValue(SessionManager.KEY_USER_PLAN_ID, planid);

                        startActivity(new Intent(context, WelcomeSubscriptionActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_INDEFINITE).show();
//                    ShowRetryBar(response.message());
                }
            }

            @Override
            public void onFailure(Call<SubscriptionResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }
        });

    }

    private void callCountryApi()
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        Call<CountryResponse> call = commonService.getCountryList();
        call.enqueue(new Callback<CountryResponse>()
        {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    CountryResponse countryResponse = response.body();

                    if (countryResponse != null && countryResponse.getCode() == 1)
                    {
                        countryList = response.body().getData();

                        if (country_id > 0)
                            callStateApi(country_id);

                        if (state_id > 0)
                            callCityApi(state_id);

                        callLanguageApi();

                       /* Country country = new Country();
                        country.setCountryId(-1);
                        country.setCountryName("----------Select Your Country----------");
                        countryList.add(0, country);*/

                        /*countryAdapter = new CountryAdapter(context, countryList);
                        spinnerCountry.setAdapter(countryAdapter);

                        int position = 0;
                        for (int i=0; i<countryList.size(); i++)
                        {
                            if (countryList.get(i).getCountryName().toLowerCase().equals("india"))
                            {
                                position = i;
                                break;
                            }
                        }
                        spinnerCountry.setSelection(position);
                        callLanguageApi();*/
                    }
                }
                else
                {
                    Toast.makeText(context, "" + response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void callStateApi(int countryId)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        StateRequest request = new StateRequest();
        request.setCountryId(countryId);

        Call<StateResponse> call = commonService.getStateList(request);
        call.enqueue(new Callback<StateResponse>()
        {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response)
            {
                utils.hideProgressbar();

                stateList = response.body().getData();
                //Log.d("STATE_LIST", stateList.toString());

                if (stateList == null)
                {
                    state_id = 0;
                    city_id = 0;
                    Toast.makeText(context, "States not available for this country", Toast.LENGTH_LONG);
                }

               /* if (stateList != null)
                {
                    State state = new State();
                    state.setStateName("Select Your State");
                    state.setStateId(-1);
                    stateList.add(0, state);

                   *//* stateAdapter = new StateAdapter(context, stateList);
                    spinnerState.setAdapter(stateAdapter);*//*
                }
                else
                {
                    state_id = 0;
                    city_id = 0;
                    Toast.makeText(context, "States not available for this country", Toast.LENGTH_LONG);

                    *//*Toast.makeText(context, "States of " + countryList.get(spinnerCountry.getSelectedItemPosition()).getCountryName()
                            + " not found", Toast.LENGTH_SHORT).show();*//*
                }*/
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void callCityApi(int id)
    {
        CityRequest request = new CityRequest();
        request.setStateId(id);

        Call<CityResponse> call = commonService.getCityList(request);
        call.enqueue(new Callback<CityResponse>()
        {
            @Override
            public void onResponse(Call<CityResponse> call, Response<CityResponse> response)
            {
                cityList = response.body().getData();

                if (cityList == null)
                {
                    city_id = 0;
                    Toast.makeText(context, "City not available for this state", Toast.LENGTH_LONG);
                }

               /* if (cityList != null)
                {
                    City city = new City();
                    city.setCityName("Select Your City");
                    city.setCityId(-1);
                    cityList.add(0, city);

                    //cityAdapter = new CityAdapter(context, cityList);
                    //spinnerCity.setAdapter(cityAdapter);
                }
                else
                {
                    *//*spinnerCity.setVisibility(View.GONE);
                    spinnerCity.setAdapter(null);
                    city_id = 0;
                    Toast.makeText(context, "Cities of " + stateList.get(spinnerState.getSelectedItemPosition()).getStateName()
                            + " not found", Toast.LENGTH_SHORT).show();*//*
                }*/
            }
            @Override
            public void onFailure(Call<CityResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void callLanguageApi()
    {
        Call<LanguageResponse> call = commonService.getLanguageList();
        call.enqueue(new Callback<LanguageResponse>()
        {
            @Override
            public void onResponse(Call<LanguageResponse> call, Response<LanguageResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    LanguageResponse languageResponse = response.body();
                    if (languageResponse != null && languageResponse.getCode() == 1)
                    {
                        languageList = response.body().getData();

                        /*Language language = new Language();
                        language.setLangName("Select Your Language");
                        language.setLangCode("-1");
                        languageList.add(0, language);*/

                        //languageAdapter = new LanguageAdapter(context, languageList);
                        //spinnerLanguage.setAdapter(languageAdapter);

                       /* int position = 0;
                        for (int i = 0; i < languageList.size(); i++)
                        {
                            Language c = languageList.get(i);
                            if (c.getLangCode().equalsIgnoreCase("en"))
                            {
                                position = i;
                                break;
                            }
                        }
                        spinnerLanguage.setSelection(position);*/
                    }
                }
                else
                {
                    Toast.makeText(context, "" + response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LanguageResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


    /*  *//* Get Country click here *//*
    @Override
    public void GetCountryPosition(int pos, Country model)
    {

    }*/
}
