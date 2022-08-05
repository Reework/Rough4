package com.shamrock.reework.fragment;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;


import com.shamrock.R;
import com.shamrock.reework.activity.DailyActivityModule.Service.FitBitClient;
import com.shamrock.reework.activity.DailyActivityModule.Service.FitBitService;
import com.shamrock.reework.activity.FoodModule.activity.MasterDetailsActivity;
import com.shamrock.reework.database.FitBitSessionManager;
import com.shamrock.reework.model.TokenResponse;
import com.shamrock.reework.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FitBitActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    FitBitService client;
    Utils utils;
    Context mContext;
    String clientID = "22B76H";
    String strRedirectURl ="com.intelegain.fitbitgadgetintegration.FitBitActivity://auth/fitbit";
    String response_type  = "code";
    String scope = "activity+nutrition+heartrate+location+nutrition+profile+settings+sleep+social+weight";
    String AuthCode  = "";
    String CLIENT_SECRETE_KEY ="77b850fbf1561676aaf4f1aa9fad3b3b";
    String base64Encoded;
    String fT;
    String SharedPre="SHARED_PREFERENCE";
    //Authorization Code Combination of ClientId:SecreteKey
    String txtAuthorization = "Basic"+" "+"MjJCNzZIOjc3Yjg1MGZiZjE1NjE2NzZhYWY0ZjFhYTlmYWQzYjNi";
    onGetTokenListner listner;
    String coming_from="";
    int checkCount = 0;

/*
    For Getting Daily Activity
GET https://api.fitbit.com/1/user/-/activities/frequent.json*/

public interface  onGetTokenListner{
    public void onGetToken(String Token);

}
    FitBitSessionManager sessionFitBitSessionManager;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_bit);
        sessionFitBitSessionManager = new FitBitSessionManager(FitBitActivity.this);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        checkCount ++;
        if(checkCount>=2){
            finish();
        }else{
            initialise();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initialise(){
        mContext = FitBitActivity.this;
        coming_from = getIntent().getStringExtra(getResources().getString(R.string.coming_from));
        utils = new Utils();
        client = FitBitClient.getClient().create(FitBitService.class);
        onNewIntent(getIntent());

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    //1st time register from app then remove promt para and directly get code from the api and backgroud it will call all functionality.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            boolean isLoggedIn = sessionFitBitSessionManager.getBooleanValue(FitBitSessionManager.IS_FITBIT_LOGIN);// getApplicationContext().getSharedPreferences(SharedPre, 0).getBoolean("isFitbitLoggedIn", false);
            //check wheather already logged in user

            if (isLoggedIn == false) {
                AuthCode = intent.getDataString();
                fT = AuthCode;
                //if Authcode Null means 1st time user come For Authentication
                if (AuthCode == null) {
                    //Check if Token is Already Stored or Not
                    String Token = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_ACCESS_TOKEN);// getApplicationContext().getSharedPreferences(SharedPre, 0).getString("RefreshToken", "");
                    if (Token.equalsIgnoreCase("")) {
                        launchLoginScreen("new");
                    } else {
                        String RefreshToken = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN);//getApplicationContext().getSharedPreferences(SharedPre, 0).getString("RefreshToken", "");
                        String TokenType = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_TOKEN_TYPE);//getApplicationContext().getSharedPreferences(SharedPre, 0).getString("TokenType", "");
                        String UserId = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_USER_ID);//getApplicationContext().getSharedPreferences(SharedPre, 0).getString("UserId", "");
                        // callToGetAllActivitiesApi(TokenType + " " + Token, UserId, "2019-07-08.json");
                    }
                } else {
                    //Check Whater AuthCode Contains Additional Special Character
                    String test = null;
                    if (AuthCode.contains("=") && AuthCode.contains("#")) {
                        test = AuthCode;
                        int startIndex = test.indexOf("=");
                        int endIndex = test.indexOf("#");

                        test = test.substring(startIndex + 1, endIndex);
                    }

                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_AUTH_CODE, test);
                    sessionFitBitSessionManager.setBooleanValue(FitBitSessionManager.IS_FITBIT_LOGIN, true);
                /*editor.putString("AuthCode", test);
                editor.putBoolean("isLoggedIn", true);
                editor.commit();*/
                    callingForToken();
                    // convertIntoBase64(clientID + ":" + CLIENT_SECRETE_KEY);
                }
            } else {
                String Token = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_ACCESS_TOKEN);
                String RefreshToken = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN);
                String TokenType = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_TOKEN_TYPE);
                String UserId = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_USER_ID);
                Intent resultInt = new Intent();
                resultInt.putExtra("action", "fetch");
                setResult(Activity.RESULT_OK, resultInt);
                finish();
                //String beare, String id, String date)
        /*    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            callToGetAllActivitiesApi(TokenType+" "+Token,UserId,dateFormat.format(date));*/
           /* String RefreshToken = getApplicationContext().getSharedPreferences(SharedPre, 0).getString("RefreshToken", "");
            String TokenType = getApplicationContext().getSharedPreferences(SharedPre, 0).getString("TokenType", "");
             String Token = getApplicationContext().getSharedPreferences(SharedPre, 0).getString("RefreshToken", "");
            String UserId = getApplicationContext().getSharedPreferences(SharedPre, 0).getString("UserId", "");*/
                // callToGetAllActivitiesApi(TokenType + " " + Token, UserId, "2019-07-08.json");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



  /*  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void convertIntoBase64(String value){
        // Sending side
        byte[] data = value.getBytes(StandardCharsets.UTF_8);
         base64Encoded = Base64.encodeToString(data, Base64.DEFAULT);

        // Receiving side
        byte[] data1 = Base64.decode(base64Encoded, Base64.DEFAULT);
        String text = new String(data1, StandardCharsets.UTF_8);



    }*/
//   https://www.fitbit.com/oauth2/authorize?response_type=code&client_id=22B6SC&redirect_uri=https%3A%2F%2Fwww.intelegain.com%2F&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800
    private void launchLoginScreen(String test) {
        String url;
        //prompt=consent&
        if(test.equalsIgnoreCase("new")) {
             url = "https://www.fitbit.com/oauth2/authorize?response_type=code&prompt=login consent&client_id=22B76H&redirect_uri=fitbittester://logincallback&scope=activity%20nutrition%20heartrate%20nutrition%20sleep%20weight";
        }else{
            url = "https://www.fitbit.com/oauth2/authorize?response_type=code&&client_id=22B76H&redirect_uri=fitbittester://logincallback&scope=activity%20nutrition%20heartrate%20nutrition%20sleep%20weight";
        }
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(FitBitActivity.this, R.color.browser_actions_divider_color));
        builder.addDefaultShareMenuItem();

        CustomTabsIntent anotherCustomTab = new CustomTabsIntent.Builder().build();


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.common_google_signin_btn_icon_dark);
        int requestCode = 100;
        Intent intent = anotherCustomTab.intent;
        intent.setData(Uri.parse(url));

        PendingIntent pendingIntent = PendingIntent.getActivity(FitBitActivity.this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setActionButton(bitmap, "Android", pendingIntent, true);
        builder.setShowTitle(true);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(FitBitActivity.this, Uri.parse(url));
        //launchTab(mContext,Uri.parse(url));
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         data.getData();
    }

    void launchTab(final Context context, final Uri uri){
        final CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient client) {
                final CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                final CustomTabsIntent intent = builder.build();
                client.warmup(0L); // This prevents backgrounding after redirection
                intent.launchUrl(context, uri);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {}
        };
        CustomTabsClient.bindCustomTabsService(context, "com.android.chrome", connection);
    }*/



 /*   private void callFoodTypesApi()
    {
        if (!((Activity) mContext).isFinishing())
        {

        }
        //response_type
        Call<ResponseBody> call = client.getAuthorization(clientID,strRedirectURl,scope);

       String finalURL =  client.getAuthorization(clientID,strRedirectURl,scope).request().url().toString();
       String test = finalURL;
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                Toast.makeText(mContext,response.toString(),Toast.LENGTH_LONG).show();
                CustomTabsIntent customTabsIntent = buildCustomTabsIntent();
                //customTabsIntent.launchUrl(FitBitActivity.this, Uri.parse(exampleUrl));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                // Log error here since request failed
                String strError = t.getMessage();
                Toast.makeText(mContext,strError.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }*/

 //Calling Activities API
    private void callToGetAllActivitiesApi(String beare, String id, String date)
    {
       /* if (!((Activity) mContext).isFinishing())
        {

        }
        //response_type
        Call<ResponseBody> call = client.getAllActivities(beare,id,date);
        //Call<ResponseBody> call = client.getAllActivities(beare,id,date);

        //String finalURL  = client.getAllActivities(beare,id,date).request().url().toString();
        String finalURL  = client.getAllActivities(beare,id,date).request().url().toString();
        String test = finalURL;
        call.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                //Check Wheater token is Expire if yes the refresh token.
                if(response.code()==401){
                    callingForRefreshToken();
                }else{
                    Toast.makeText(mContext,response.body().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                // Log error here since request failed
                String strError = t.getMessage();
                Toast.makeText(mContext,strError.toString(), Toast.LENGTH_LONG).show();
            }
        });*/
    }




    //Calling Token Refresh API
    private void callingForRefreshToken()
    {
        if (!((Activity) mContext).isFinishing())
        {

        }



        String AuthCode  = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_AUTH_CODE);//getApplicationContext().getSharedPreferences(SharedPre,0).getString("AuthCode","");
        String RefreshToken  = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN);//getApplicationContext().getSharedPreferences(SharedPre,0).getString("RefreshToken","");
        Call<TokenResponse> call = client.getRefreshToken(txtAuthorization,"refresh_token",RefreshToken);
        call.enqueue(new Callback<TokenResponse>()
        {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response)
            {
                if(response.isSuccessful()) {

                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_ACCESS_TOKEN,response.body().getAccessToken());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN,response.body().getRefreshToken());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_TOKEN_TYPE,response.body().getTokenType());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_USER_ID,response.body().getUserId());
                    //callToGetAllActivitiesApi(response.body().getTokenType()+" "+response.body().getAccessToken(),response.body().getUserId(),"2019-08-08.json");
                }
            }
            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t)
            {
                // Log error here since request failed
                String strError = t.getMessage();
                Toast.makeText(mContext,strError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }




    //Calling Token API
    private void callingForToken()
    {
        utils.showProgressbar(mContext);
//        fitbittester://logincallback?code=30a9817ad63f13d72b83484d7e3a00603b8f0160#_=_
        String AuthCode = sessionFitBitSessionManager.getStringValue(FitBitSessionManager.FITBIT_AUTH_CODE);//mContext.getSharedPreferences(SharedPre, 0).getString("AuthCode", "");
        Call<TokenResponse> call = client.getToken(txtAuthorization,clientID,"authorization_code","fitbittester://logincallback", AuthCode);
        String finalURL =  client.getToken(txtAuthorization,clientID,"authorization_code","fitbittester://logincallback", AuthCode).request().url().toString();
        String url = finalURL;
        call.enqueue(new Callback<TokenResponse>()
        {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response)
            {
                utils.hideProgressbar();
                if(response.isSuccessful()) {
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_ACCESS_TOKEN,response.body().getAccessToken());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_REFRESH_TOKEN,response.body().getRefreshToken());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_TOKEN_TYPE,response.body().getTokenType());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_USER_ID,response.body().getUserId());
                    sessionFitBitSessionManager.setStringValue(FitBitSessionManager.FITBIT_CONNECT_OR_DISSCONNECT,"Remove My Device");


                    Intent intent = new Intent(mContext, MasterDetailsActivity.class);
                    intent.putExtra("FRAGMENT_POSITION", 4);
                    startActivity(intent);
                   // callToGetAllActivitiesApi(response.body().getTokenType()+" "+response.body().getAccessToken(),response.body().getUserId(),"2019-07-08.json");
                }
            }
            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                // Log error here since request failed
                String strError = t.getMessage();
                Toast.makeText(mContext,strError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
