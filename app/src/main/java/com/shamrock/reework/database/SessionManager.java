package com.shamrock.reework.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.shamrock.reework.activity.LoginModule.ChooseLoginActivity;
import com.shamrock.reework.activity.LoginModule.LoginActivity;
import com.shamrock.reework.activity.wellcomepage.WellcomePageActivity;
import com.shamrock.reework.api.response.SubscriptionFeaturesResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class SessionManager
{
    private SharedPreferences pref;
    private Editor editor;
    private Context _context;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_REECOACH_ID = "user_reecoach_id";
    public static final String KEY_USER_PATHO_ID = "user_patho_id";
    public static final String KEY_USER_F_NAME = "user_first_name";
    public static final String KEY_USER_L_NAME = "user_last_name";
    public static final String KEY_USER_EMAIL = "user_email";
    public static final String KEY_USER_MOBILE_NO = "user_mobile_no";
    public static final String KEY_USER_DOB = "user_dob";
    public static final String KEY_USER_GENDER = "user_gender";
    public static final String KEY_USER_ADDRESS = "user_address";
    private static final String KEY_USER_ROLE_ID = "user_role_ID";
    public static final String KEY_USER_COUNTRY_ID = "user_country_id";
    public static final String KEY_USER_STATE_ID = "user_state_id";
    public static final String KEY_USER_CITY_ID = "user_city_id";
    public static final String KEY_USER_COUNTRY = "user_country";
    public static final String KEY_USER_STATE = "user_state";
    public static final String KEY_USER_CITY = "user_city";
    public static final String KEY_USER_SUBSCRIPTION_LIST = "user_subscription_list";
    public static final String KEY_USER_LANGUAGE = "user_lang";
    public static final String KEY_USER_LANGUAGE_CODE = "user_lang_code";
    public static final String KEY_USER_PLAN_ID = "user_Plan_ID";
    public static final String KEY_USER_IS_SUBSCRIBE_FOR_FREE = "user_subscribe_for_free";
    private static final String KEY_USER_IS_VERIFIED = "user_IsVerified";
    public static final String KEY_USER_IS_FREEZE = "user_IsFreeze";
    private static final String KEY_USER_IS_DELETED = "user_IsDeleted";
    private static final String KEY_USER_DELETED_ON = "user_DeletedOn";
    private static final String KEY_USER_OTP_STATUS = "user_otp_status";
    public static final String KEY_USER_TOKEN = "user_token";
    public static final String KEY_USER_WEIGHT = "user_weight";
    public static final String KEY_USER_IDEAL_WEIGHT = "user_ideal_weight";
    public static final String KEY_USER_PROFILE_IMAGE = "user_profile_image";
    public static final String KEY_REECOACH_PROFILE_IMAGE = "reecoach_profile_image";
    public static final String KEY_FCM_DEVICE_ID = "fcm_device_id";

    public static final String KEY_USER_HEALTH_IS_FOUND = "user_health_found";
    public static final String KEY_USER_IS_BLOOD_TEST_SCHEDULE = "user_blood_test_schedule";
    public static final String KEY_USER_IS_BLOOD_REPORT_DONE = "user_blood_report_done";

    // Constructor
    public SessionManager(Context context)
    {
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }

    public void createLoginSession(int user_id, int reecoach_id, String first_name, String last_name, String email,
                                   String mobileNo, String dob, int gender, String address, int roleId, int countryId, int state_ID,
                                   int cityId, String langCode, int plan_id, boolean isVerified, boolean isFreeze, boolean isDeleted,
                                   String otpStatus, String token, boolean healthFound, boolean isBloodtestScedule, boolean isBloodReportDone,
                                   String imageUrl, String country, String state, String city, String subscriptionList)
    {
        // Storing login value as TRUE
        editor = pref.edit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putInt(KEY_USER_ID, user_id);
        editor.putInt(KEY_USER_REECOACH_ID, reecoach_id);
        editor.putString(KEY_USER_F_NAME, first_name);
        editor.putString(KEY_USER_L_NAME, last_name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_MOBILE_NO, mobileNo);
        editor.putString(KEY_USER_DOB, dob);
        editor.putInt(KEY_USER_GENDER, gender);
        editor.putString(KEY_USER_ADDRESS, address);
        editor.putInt(KEY_USER_ROLE_ID, roleId);
        editor.putInt(KEY_USER_COUNTRY_ID, countryId);
        editor.putInt(KEY_USER_STATE_ID, state_ID);
        editor.putInt(KEY_USER_CITY_ID, cityId);
        editor.putString(KEY_USER_LANGUAGE_CODE, langCode);
        editor.putInt(KEY_USER_PLAN_ID, plan_id);
        editor.putBoolean(KEY_USER_IS_VERIFIED, isVerified);
        editor.putBoolean(KEY_USER_IS_FREEZE, isFreeze);
        editor.putBoolean(KEY_USER_IS_DELETED, isDeleted);
        //editor.putString(KEY_USER_DELETED_ON, deletedOn);
        editor.putString(KEY_USER_OTP_STATUS, otpStatus);
        editor.putString(KEY_USER_TOKEN, token);

        editor.putBoolean(KEY_USER_HEALTH_IS_FOUND, healthFound);
        editor.putBoolean(KEY_USER_IS_BLOOD_TEST_SCHEDULE, isBloodtestScedule);
        editor.putBoolean(KEY_USER_IS_BLOOD_REPORT_DONE, isBloodReportDone);

        editor.putString(KEY_USER_PROFILE_IMAGE, imageUrl);

        editor.putString(KEY_USER_COUNTRY, country);
        editor.putString(KEY_USER_STATE, state);
        editor.putString(KEY_USER_CITY, city);
        editor.putString(KEY_USER_SUBSCRIPTION_LIST,subscriptionList);

        // commit changes
        editor.apply();
    }

    public void saveSubscriptionFeaturesInPrefrence(Context context, ArrayList<SubscriptionFeaturesResponse.FeatureList> list)
    {
        editor = pref.edit();

        Gson gson = new Gson();
        String jsonFeatures = gson.toJson(list);

        editor.putString("SUB_FEATURE_LIST", jsonFeatures);
        editor.commit();
    }

    public void checkLogin()
    {
        // Check login status
        if (!this.isLoggedIn())
        {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    private HashMap<String, Object> getUserDetails()
    {
        HashMap<String, Object> user = new HashMap<>();
        user.put(KEY_USER_ID, pref.getInt(KEY_USER_ID, 0));
        user.put(KEY_USER_REECOACH_ID, pref.getInt(KEY_USER_REECOACH_ID, 0));
        user.put(KEY_USER_F_NAME, pref.getString(KEY_USER_F_NAME, null));
        user.put(KEY_USER_L_NAME, pref.getString(KEY_USER_L_NAME, null));
        user.put(KEY_USER_EMAIL, pref.getString(KEY_USER_EMAIL, null));
        user.put(KEY_USER_MOBILE_NO, pref.getString(KEY_USER_MOBILE_NO, null));
        user.put(KEY_USER_DOB, pref.getString(KEY_USER_DOB, null));
        user.put(KEY_USER_GENDER, pref.getInt(KEY_USER_GENDER, 0));
        user.put(KEY_USER_ADDRESS, pref.getString(KEY_USER_ADDRESS, null));
        user.put(KEY_USER_ROLE_ID, pref.getInt(KEY_USER_ROLE_ID, 0));
        user.put(KEY_USER_COUNTRY_ID, pref.getInt(KEY_USER_COUNTRY_ID, 0));
        user.put(KEY_USER_CITY_ID, pref.getInt(KEY_USER_CITY_ID, 0));
        user.put(KEY_USER_LANGUAGE_CODE, pref.getString(KEY_USER_LANGUAGE_CODE, null));
        user.put(KEY_USER_PLAN_ID, pref.getInt(KEY_USER_PLAN_ID, 0));
        user.put(KEY_USER_IS_VERIFIED, pref.getBoolean(KEY_USER_IS_VERIFIED, false));
        user.put(KEY_USER_IS_FREEZE, pref.getBoolean(KEY_USER_IS_FREEZE, false));
        user.put(KEY_USER_IS_DELETED, pref.getBoolean(KEY_USER_IS_DELETED, false));
        user.put(KEY_USER_DELETED_ON, pref.getString(KEY_USER_DELETED_ON, null));
        user.put(KEY_USER_OTP_STATUS, pref.getString(KEY_USER_OTP_STATUS, null));
        user.put(KEY_USER_TOKEN, pref.getString(KEY_USER_TOKEN, null));
        return user;
    }

    public void logoutUser()
    {
        // Clearing all data from Shared Preferences
        editor = pref.edit();
        editor.clear();
        editor.apply();

        // After logout redirect user to Logging Activity
        Intent i = new Intent(_context, WellcomePageActivity.class);
        i.putExtra("ISReework",true);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Staring Login Activity
        _context.startActivity(i);

    }


    public void clearSession()
    {
        // Clearing all data from Shared Preferences
        editor = pref.edit();
        editor.clear();
        editor.apply();





    }

    public String getStringValue(String Key)
    {
        return pref.getString(Key, "");
    }
    public void setStringValue(String Key, String value)
    {
        editor = pref.edit();
        editor.putString(Key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String Key)
    {
        return pref.getBoolean(Key, false);
    }

    public void setBooleanValue(String Key, boolean value)
    {
        editor = pref.edit();
        editor.putBoolean(Key, value);
        editor.apply();
    }

    public int getIntValue(String Key)
    {
        return pref.getInt(Key, 0);
    }

    public void setIntValue(String key, int value)
    {
        editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public double getDoubleValue(String key)
    {
        return Double.longBitsToDouble(pref.getLong(key, 0));
    }


    public void setHealthParameterData(double weight, double ideal_weight, boolean healthFound)
    {
        editor = pref.edit();
        editor.putLong(KEY_USER_WEIGHT, Double.doubleToRawLongBits(weight));
        editor.putLong(KEY_USER_IDEAL_WEIGHT, Double.doubleToRawLongBits(ideal_weight));
        editor.putBoolean(KEY_USER_HEALTH_IS_FOUND, healthFound);

        // commit changes
        editor.apply();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn()
    {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void saveProfileData(String user_f_name, String user_l_name, String user_email, String user_mobile_no, String user_dob, int user_gender,
                                String user_address, int user_country_id, int user_state_id, int user_city_id, String user_language_code,
                                String user_token, String user_image, String country, String state, String city)
    {
        // Storing login value as TRUE
        editor = pref.edit();
        editor.putString(KEY_USER_F_NAME, user_f_name);
        editor.putString(KEY_USER_L_NAME, user_l_name);
        editor.putString(KEY_USER_EMAIL, user_email);
        editor.putString(KEY_USER_MOBILE_NO, user_mobile_no);
        editor.putString(KEY_USER_DOB, user_dob);
        editor.putInt(KEY_USER_GENDER, user_gender);
        editor.putString(KEY_USER_ADDRESS, user_address);
        editor.putInt(KEY_USER_COUNTRY_ID, user_country_id);
        editor.putInt(KEY_USER_STATE_ID, user_state_id);
        editor.putInt(KEY_USER_CITY_ID, user_city_id);
        editor.putString(KEY_USER_LANGUAGE_CODE, user_language_code);
        editor.putString(KEY_USER_TOKEN, user_token);
        editor.putString(KEY_USER_PROFILE_IMAGE, user_image);
        editor.putString(KEY_USER_COUNTRY, country);
        editor.putString(KEY_USER_STATE, state);
        editor.putString(KEY_USER_CITY, city);
        // commit changes
        editor.apply();
    }
}