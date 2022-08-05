package com.shamrock.reework.activity.RegistrationModule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.shamrock.R;
import com.shamrock.reework.activity.LoginModule.LoginActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.RegistrationModule.service.RegistrationService;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.api.request.CityRequest;
import com.shamrock.reework.api.request.RegistrationRequest;
import com.shamrock.reework.api.request.StateRequest;
import com.shamrock.reework.api.response.City;
import com.shamrock.reework.api.response.CityResponse;
import com.shamrock.reework.api.response.Country;
import com.shamrock.reework.api.response.CountryResponse;
import com.shamrock.reework.api.response.Language;
import com.shamrock.reework.api.response.LanguageResponse;
import com.shamrock.reework.api.response.RegistrationResponse;
import com.shamrock.reework.api.response.State;
import com.shamrock.reework.api.response.StateResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.dialog.CityDialog;
import com.shamrock.reework.dialog.CountryDialog;
import com.shamrock.reework.dialog.LanguageDialog;
import com.shamrock.reework.dialog.StateDialog;
import com.shamrock.reework.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,
                                                        DatePickerDialog.OnDateSetListener,
                                                        CompoundButton.OnCheckedChangeListener,
                                                        DialogInterface.OnCancelListener,
                                                        CountryDialog.GetCountryListener,
                                                        StateDialog.GetStateListener,
                                                        CityDialog.GetCityListener,
                                                        LanguageDialog.GetLanguageListener, TextWatcher
{


    private static final String TAG = "RegisterActivity";
    Context context;
    Toolbar toolbar;
    Typeface font;
    EditText editText_FirstName, editText_LastName, editText_Email, editText_Mobile, editText_Address,edt_pincode;
    TextView textView_DOB;
    RadioButton radioButton_Male, radioButton_Female, radioButton_Transgender;
    Button btnSubmit;

    String pincode="";
    ArrayList<Country> countryList;
    ArrayList<State> stateList;
    ArrayList<City> cityList;
    ArrayList<Language> languageList;

    CommonService commonService;
    RegistrationService regService;
    DatePickerDialog datepickerdialog;
    int country_id = 0, state_id = 0, city_id = 0, gender=0;
    String firstName, lastName, DOB, email, mobile, lsCountry, lsCity, lsState, lsLanguage;
    String address="";
    private Utils utils;
    SessionManager sessionManager;
    String lang_code="-1";

    /* Dialopgs for country, state, city and language */
    private CountryDialog countryDialog;
    private StateDialog stateDialog;
    private CityDialog cityDialog;
    private LanguageDialog languageDialog;
    private TextView tvCountry, tvState, tvCity, tvLanguage;
    private String token="NA.";

    private TextView textSignUp_new;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = RegisterActivity.this;
        init();
        setToolBar();
        findViews();
    }

    private void init()
    {
        utils = new Utils();
        sessionManager = new SessionManager(context);
        commonService = Client.getClient().create(CommonService.class);
        regService = Client.getClient().create(RegistrationService.class);

        gender = 0;
        countryList = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        languageList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                 token = instanceIdResult.getToken();



            }
        });

        datepickerdialog = new DatePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
        datepickerdialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datepickerdialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialogInterface)
            {
                Button buttonNeg = datepickerdialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNeg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                Button buttonPos = datepickerdialog.getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });
    }

    /**
     * Set the toolbar related task here like setting icon, header Name etc
     */
    private void setToolBar()
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        tvTitle.setText("Registration");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                startActivity(new Intent(context, LoginActivity.class));
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        editText_FirstName = findViewById(R.id.edtText_Reg_FirstName);
        editText_LastName = findViewById(R.id.edtText_Reg_LastName);
        editText_Email = findViewById(R.id.edtText_Reg_Email);
        editText_Mobile = findViewById(R.id.edtText_Reg_Mobile);
        editText_Mobile.addTextChangedListener(this);
        editText_Address = findViewById(R.id.edtText_Reg_Address);
        textView_DOB = findViewById(R.id.textView_Reg_DOB);
        radioButton_Male = findViewById(R.id.radioButton_Reg_Male);
        radioButton_Female = findViewById(R.id.radioButton_Reg_Female);
        radioButton_Transgender = findViewById(R.id.radioButton_Reg_Transgender);

        btnSubmit = findViewById(R.id.buttonSubmitRegistration);
        edt_pincode = findViewById(R.id.edt_pincode);

        tvCountry = findViewById(R.id.tvCountry);
        tvState = findViewById(R.id.tvState);
        tvCity = findViewById(R.id.tvCity);
        tvLanguage = findViewById(R.id.tvLanguage);












        textView_DOB.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        radioButton_Male.setOnCheckedChangeListener(this);
        radioButton_Female.setOnCheckedChangeListener(this);
        radioButton_Transgender.setOnCheckedChangeListener(this);
        tvCountry.setOnClickListener(this);
        tvState.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvLanguage.setOnClickListener(this);

        /* call your webApi */
        if (Utils.isNetworkAvailable(context))
            callCountryApi();
        else
            showNoInternetDialog();
    }

    private void showNoInternetDialog()
    {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);

        builder.setTitle("No internet Connection !");
        builder.setMessage("Please turn on internet connection to continue.");
        builder.setCancelable(false);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                finish();
            }
        });
        builder.setPositiveButton("try again", new DialogInterface.OnClickListener()
            {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                if (Utils.isNetworkAvailable(context))
                    callCountryApi();
                else
                    showNoInternetDialog();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view)
    {
        FragmentManager fm;
        Bundle bundle;

        firstName = editText_FirstName.getText().toString().trim();
        lastName = editText_LastName.getText().toString().trim();
        email = editText_Email.getText().toString().trim();
        mobile = editText_Mobile.getText().toString().trim();
        address = editText_Address.getText().toString().trim();
        pincode = edt_pincode.getText().toString().trim();

        buttonCheckChanged();

        switch (view.getId())
        {
            case R.id.tvCountry:

                if (countryList != null && countryList.size() > 0)
                {
                    fm = getSupportFragmentManager();
                    countryDialog = new CountryDialog();
                    bundle = new Bundle();
                    bundle.putSerializable("COUNTRY_LIST", countryList);
                    countryDialog.setArguments(bundle);
                    countryDialog.show(fm, "COUNTRY_FRAGMENT");
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Countries not available."
                            , Snackbar.LENGTH_SHORT).show();
                }

                break;

            case R.id.tvState:

                if (country_id <= 0)
                    Snackbar.make(findViewById(android.R.id.content), "Select country first."
                            , Snackbar.LENGTH_SHORT).show();
                else
                {
                    if (stateList != null && stateList.size() > 0)
                    {
                        fm = getSupportFragmentManager();
                        stateDialog = new StateDialog();
                        bundle = new Bundle();
                        bundle.putSerializable("STATE_LIST", stateList);
                        stateDialog.setArguments(bundle);
                        stateDialog.show(fm, "STATE_FRAGMENT");
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), "States not available.", Snackbar.LENGTH_SHORT).show();
                }
                break;

            case R.id.tvCity:

                if (state_id <= 0)
                    Snackbar.make(findViewById(android.R.id.content), "Select state first."
                            , Snackbar.LENGTH_SHORT).show();
                else
                {
                    if (cityList !=null && cityList.size() > 0)
                    {
                        fm = getSupportFragmentManager();
                        cityDialog = new CityDialog();
                        bundle = new Bundle();
                        bundle.putSerializable("CITY_LIST", cityList);
                        cityDialog.setArguments(bundle);
                        cityDialog.show(fm, "CITY_FRAGMENT");
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), "Cities not available."
                                , Snackbar.LENGTH_SHORT).show();
                }
                break;

            case R.id.tvLanguage:

                if (languageList != null && languageList.size() > 0)
                {
                    fm = getSupportFragmentManager();
                    languageDialog = new LanguageDialog();
                    bundle = new Bundle();
                    bundle.putSerializable("LANGUAGE_LIST", languageList);
                    languageDialog.setArguments(bundle);
                    languageDialog.show(fm, "LANGUAGE_FRAGMENT");
                }
                else
                    Snackbar.make(findViewById(android.R.id.content), "Languages not available."
                            , Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.buttonSubmitRegistration:

                boolean dataIsValid = validateAllData(firstName, lastName, email, mobile, address,pincode);

                if (Utils.isNetworkAvailable(context))
                {
                    if (dataIsValid)
                    {
                        callRegistrationApi(firstName, lastName, DOB, gender, email, mobile, address, country_id,
                                state_id, city_id, lang_code);
                    }
                }
                else
                    Snackbar.make(findViewById(android.R.id.content), "Check internet connection."
                            , Snackbar.LENGTH_SHORT).show();
                break;

            case R.id.textView_Reg_DOB:

                datepickerdialog.show();
                break;

            default:
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
            lsCountry = model.getCountryName();

            if (!TextUtils.isEmpty(model.getCountryName()))
                tvCountry.setText(model.getCountryName());
            else
                tvCountry.setText("Select country");

            if (country_id > 0)
            {
                callStateApi(country_id);
            }
        }
        else
        {
            country_id = 0;
            lsCountry = "";
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
            lsState = model.getStateName();

            if (!TextUtils.isEmpty(model.getStateName()))
                tvState.setText(model.getStateName());
            else
                tvCountry.setText("Select state");

            if (state_id > 0)
            {
                callCityApi(state_id);
            }
        }
        else
        {
            state_id = 0;
            lsState = "";
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
            lsCity = model.getCityName();

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
            lsLanguage = model.getLangName();

            if (!TextUtils.isEmpty(model.getLangName()))
                tvLanguage.setText(model.getLangName());
            else
                tvLanguage.setText("Select language");
        }
        else
            lang_code = "";
    }

    private boolean validateAllData(String firstName, String lastName, String email, String mobile, String address, String pincode)
    {
        boolean valid = true;
        String errorMsg = "";


//        String regex = "(91)?[7-9][0-9]{9}";


        if (firstName.isEmpty())
        {
            valid = false;
            errorMsg = "Please enter first name";
        }

        if (valid)
        {
            if (lastName.isEmpty())
            {
                valid = false;
                errorMsg = "Please enter last name";
            }
        }



        buttonCheckChanged();
        if (valid)
        {
            if (email.isEmpty())
            {
                valid = false;
                errorMsg = "Email ID can not be blank";
            }
          /*  else if (email.length() > 25)
            {
                valid = false;
                errorMsg = "Email ID Max 25 characters";
            }*/
            else if (!Utils.isValidEmail(email))
            {
                valid = false;
                errorMsg = "Please enter valid email ID";
            }
        }

        if (valid)
        {
            if (mobile.isEmpty())
            {
                valid = false;
                errorMsg = "Mobile Number can not be blank";
            }
            else if (mobile.length() < 13)
            {
                valid = false;
                errorMsg = "Please enter 10 digit Mobile Number";
            }


//            else if (!mobile.matches(regex)){
//                valid = false;
//                errorMsg = "Please enter valid Mobile Number";
//            }





            else if (!Utils.isValidMobileNo(mobile))
            {
                valid = false;
                errorMsg = "Enter valid mobile number";
            }
        }

        if (valid)
        {
            if (address.isEmpty())
            {
                valid = false;
                errorMsg = "Enter the address";
            }
        }

        if (valid)
        {
            if (country_id <= 0)
            {
                valid = false;
                errorMsg = "This Value can not be blank";
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
                errorMsg = "Please select city";
            }
        }



        if (valid){
            if (pincode.isEmpty()){
                valid = false;
                errorMsg = "Please enter pincode";
            }

            if (!pincode.isEmpty()){
                if (pincode.length()<6){
                    valid = false;
                    errorMsg = "Please enter correct pincode";
                }
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
//        if (languageList.isEmpty()) {
//            valid = false;
//            ShowRetryBar_Language("Unable to load languages");
//        } else if (countryList.isEmpty()) {
//            valid = false;
//            ShowRetryBar_Country("Unable to load Countries");
//        }

        if (!errorMsg.isEmpty())
        {
            Toast.makeText(context, "" + errorMsg, Toast.LENGTH_SHORT).show();
        }
        return valid;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
//        startActivity(new Intent(context, LoginActivity.class));
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
                    CountryResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        countryList = response.body().getData();





                        if (countryList != null)
                        {


                            for (int i = 0; i <countryList.size() ; i++) {

                                if (countryList.get(i).getCountryName().equalsIgnoreCase("india")){

                                    country_id = countryList.get(i).getCountryId();
                                    lsCountry = countryList.get(i).getCountryName();

                                    if (!TextUtils.isEmpty(countryList.get(i).getCountryName()))
                                        tvCountry.setText(countryList.get(i).getCountryName());
                                    else
                                        tvCountry.setText("Select country");

                                    if (country_id > 0)
                                    {
                                        callStateApi(country_id);
                                    }

                                    break;
                                }

                            }

                        }
                        else
                        {
                            country_id = 0;
                            lsCountry = "";
                        }











                        callLanguageApi();




                       /* Country country = new Country();
                        country.setCountryName("Select Your Country");
                        country.setCountryId(-1);
                        countryList.add(0, country);

                        countryAdapter = new CountryAdapter(context, countryList);
                        spinnerCountry.setAdapter(countryAdapter);

                        int position = 0;
                        for (int i = 0; i < countryList.size(); i++)
                        {
                            Country c = countryList.get(i);
                            if (c.getCountryName().equalsIgnoreCase("india"))
                            {
                                position = i;
                                break;
                            }
                        }
                        spinnerCountry.setSelection(position);*/
                    }
                    else
                    {
                        Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar(response.message());
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e(TAG, t.toString());
            }

            private void ShowRetryBar(String msg)
            {
                String strMessage;
//                if (msg != null) {
//                    strMessage = msg;
//                } else {
//                    strMessage = "Unable to load countries";
//                }
                strMessage = "Unable to load countries";
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

                if (stateList == null)
                {
                    state_id = 0;
                    city_id = 0;
                    Toast.makeText(context, "States not available for this country", Toast.LENGTH_LONG);
                }

               /* if (stateList != null)
                {
                    State country = new State();
                    country.setStateName("Select Your State");
                    country.setStateId(-1);
                    stateList.add(0, country);

                    stateAdapter = new StateAdapter(context, stateList);
                    spinnerState.setAdapter(stateAdapter);
                }
                else
                {
                    spinnerState.setAdapter(null);
                    spinnerCity.setAdapter(null);
                    state_id = 0;
                    city_id = 0;
                    Toast.makeText(context, "States of " + countryList.get(spinnerCountry.getSelectedItemPosition()).getCountryName()
                            + " not found", Toast.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    private void callCityApi(int stateId)
    {
        CityRequest request = new CityRequest();
        request.setStateId(stateId);

        Call<CityResponse> call = commonService.getCityList(request);
        call.enqueue(new Callback<CityResponse>()
        {
            @Override
            public void onResponse(@NonNull Call<CityResponse> call, @NonNull Response<CityResponse> response)
            {
                cityList = response.body().getData();

                if (cityList == null)
                {
                    city_id = 0;
                    Toast.makeText(context, "City not available for this state", Toast.LENGTH_LONG);
                }

               /* if (cityList != null)
                {
                    City country = new City();
                    country.setCityName("Select Your City");
                    country.setCityId(-1);
                    cityList.add(0, country);

                    cityAdapter = new CityAdapter(context, cityList);
                    spinnerCity.setAdapter(cityAdapter);
                }
                else
                {
                    spinnerCity.setAdapter(null);
                    city_id = 0;
                    Toast.makeText(context, "Cities of " + stateList.get(spinnerState.getSelectedItemPosition()).getStateName()
                            + " not found", Toast.LENGTH_SHORT).show();
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
                    LanguageResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        languageList = response.body().getData();

                       /* Language country = new Language();
                        country.setLangName("Select Your Language");
                        country.setLangCode("-1");
                        languageList.add(0, country);

                        languageAdapter = new LanguageAdapter(context, languageList);
                        spinnerLanguage.setAdapter(languageAdapter);

                        int position = 0;
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
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    ShowRetryBar_Language(response.message());
                }
            }

            @Override
            public void onFailure(Call<LanguageResponse> call, Throwable t)
            {
                // Log error here since request failed
                utils.hideProgressbar();
                Log.e(TAG, t.toString());
            }
        });
    }

    private void ShowRetryBar_Language(String msg)
    {
        String strMessage;
        strMessage = "Unable to load Languages";

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        callLanguageApi();
                    }
                });
        snackbar.show();
    }

    private void callRegistrationApi(String firstName, String lastName, String DOB, int gender, String email, String mobile,
                                     String address, int country_id, int state_id, int city_id, String lang_code)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }













        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setEmail(email);
        request.setMobileNo(mobile.replace("+91",""));
        request.setGender(gender);
        request.setDOB(DOB);
        request.setCountryId(country_id);
        request.setStateId(state_id);
        request.setCityId(city_id);
        request.setLangCode(lang_code);
        request.setRoleID(2);    // 2 = Reeworker
        request.setAddress(address);
        request.setToken(token);
        request.setPincode(edt_pincode.getText().toString());

        Call<RegistrationResponse> call = regService.setRegistration(request);
        call.enqueue(new Callback<RegistrationResponse>()
        {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    RegistrationResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        //Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();


                        final Dialog dialog=new Dialog(RegisterActivity.this);
                        dialog.setContentView(R.layout.dialog_sucess);
                        dialog.setCancelable(false);
                        Button btn_ok=dialog.findViewById(R.id.btn_ok);
                        TextView txt_msg=dialog.findViewById(R.id.txt_msg);
                        txt_msg.setText(listResponse.getMessage());
                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                sessionManager.setStringValue(SessionManager.KEY_USER_COUNTRY, lsCountry);
                                sessionManager.setStringValue(SessionManager.KEY_USER_STATE, lsState);
                                sessionManager.setStringValue(SessionManager.KEY_USER_CITY, lsCity);
                                sessionManager.setStringValue(SessionManager.KEY_USER_LANGUAGE,lsLanguage );
                                 clearTask();

                            }
                        });
                        dialog.show();


                    }
                    else
                    {
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_SHORT).show();
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t)
            {
                Log.e(TAG, t.toString());
                utils.hideProgressbar();
            }

            private void ShowRetryBar(String msg)
            {
                String strMessage;
                if (msg != null)
                {
                    strMessage = msg;
                } else {
                    strMessage = "Unable to load data";
                }
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), strMessage, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //callCountryApi();
                            }
                        });
                snackbar.show();
            }
        });

    }

    public void clearTask()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day)
    {


        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0); ////disable dates
        cal.set(year, month, day, 0, 0, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = dateFormat.format(cal.getTime());


        if (getAge(dateString)<18){

            Toast.makeText(context, "You must be above 18 years of age.", Toast.LENGTH_LONG).show();


        }else {
            textView_DOB.setText(dateString);

        }





        // change date format for webAPI
        //lsDate = String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, (month + 1), year);
        //DOB = String.format("%02d/%02d/%04d", day, (month + 1), year);
        DOB = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        buttonCheckChanged();
    }

    private void buttonCheckChanged()
    {
        if (radioButton_Male.isChecked())
        {
            gender = 1;
        }
        else if (radioButton_Female.isChecked())
        {
            gender = 2;
        }
        else if (radioButton_Transgender.isChecked())
        {
            gender = 3;
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface)
    {
        Toast.makeText(context, "Registration canceled", Toast.LENGTH_LONG).show();
    }



    private int getAge(String dobString){

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month+1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }



        return age;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        String text_value = editText_Mobile.getText().toString().trim();
        if(text_value.equalsIgnoreCase("+91"))
        {
            editText_Mobile.setText("");
        }else
        {
            if(!text_value.startsWith("+91") && text_value.length()>0) {
                editText_Mobile.setText("+91" + charSequence.toString());
                Selection.setSelection(editText_Mobile.getText(), editText_Mobile.getText().length());
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
