package com.shamrock.reework.common;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDexApplication;

//import com.crashlytics.android.Crashlytics;
import com.shamrock.reework.activity.WelcomeModule.ClsCommonWellcomaster;
import com.shamrock.reework.activity.otpmodule.AppSignatureHelper;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.CommonService;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

//import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;

public class ClsApplication extends Application {
    private CommonService commonService;

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
//        getBeforewe
//
//        llcomeMEssageMasterSlogan();


//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)
//                .build();
//        Fabric.with(fabric);
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//
//                Log.d("uncought",e.getMessage());
//            }
//        });
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignatures();

//        Crashlytics.getInstance().crash();


//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)
//                .build();
//        Fabric.with(fabric);
    }

    private void getBeforewellcomeMEssageMasterSlogan(){



        commonService = Client.getClient().create(CommonService.class);

        Call<ClsCommonWellcomaster> call = commonService.getWelcomeMessage("BEFORE WELCOME");
        call.enqueue(new Callback<ClsCommonWellcomaster>()
        {
            @Override
            public void onResponse(Call<ClsCommonWellcomaster> call, retrofit2.Response<ClsCommonWellcomaster> response)
            {
                if (response.code() == Client.RESPONSE_CODE_OK){


                    try {
                        ClsCommonWellcomaster moodResponse = response.body();

                        if (moodResponse!=null){
                            if (moodResponse.getCode().equals("1")){

                                if (moodResponse.getData()!=null){
                                    SessionManager session=new SessionManager(getApplicationContext());

                                    session.setStringValue("KEY_BEFORE_WELCOME_TEXT",moodResponse.getData().getMessage());
                                    session.setStringValue("KEY_BEFORE_WELCOME_IMAGE",moodResponse.getData().getImagePath());
                                }




//                                Toast.makeText(mContext, ""+moodResponse.getData(), Toast.LENGTH_SHORT).show();


                            }else {



                            }
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                new UserHealthResponse().Data=moodResponse.getData();


                }


            }

            @Override
            public void onFailure(Call<ClsCommonWellcomaster> call, Throwable t)
            {

                Log.e("ERROR------>", t.toString());
            }
        });
    }

}
