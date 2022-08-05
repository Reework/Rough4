package com.shamrock.reework.database;

import android.content.Context;
import android.content.SharedPreferences;

public class FitBitSessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidFitBitPref";

    // All Shared Preferences Keys
    public static final String IS_FITBIT_LOGIN = "IsFitBitLoggedIn";

    public static final String FITBIT_REFRESH_TOKEN = "FitBitRefreshToken";
    public static final String FITBIT_ACCESS_TOKEN = "FitBitAccessToken";
    public static final String FITBIT_AUTH_CODE = "FitBitAuthCode";
    public static final String FITBIT_TOKEN_TYPE = "FitBitTokenType";
    public static final String FITBIT_USER_ID = "FitBitUserId";
    public static final String FITBIT_CONNECT_OR_DISSCONNECT = "FitBitIsConnectNDisconnect";



    // Constructor
    public FitBitSessionManager(Context context)
    {
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

    }


    public void clearSharePreference(){
        editor = pref.edit();
        editor.clear().commit();
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

}
