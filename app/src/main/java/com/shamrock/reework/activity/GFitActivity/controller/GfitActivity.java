package com.shamrock.reework.activity.GFitActivity.controller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import com.shamrock.R;

import net.danlew.android.joda.JodaTimeAndroid;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GfitActivity extends AppCompatActivity implements OnSuccessListener<DataSet> {
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";
    private TextView counter,txt_viewsteps,txt_steps,txt_7days,txt_energy;
    private TextView txt_dailysteps,txt_7cal;
    Button btn_next;
    FitnessOptions fitnessOptions;
    Button btn_logout;
    float abc1=0,abc2=0;
String  steps;
    private static final int REQUEST_OAUTH = 1;

    /**
     *  Track whether an authorization activity is stacking over the current activity, i.e. when
     *  a known auth error is being resolved, such as showing the account chooser or presenting a
     *  consent dialog. This avoids common duplications as might happen on screen rotations, etc.
     */
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private GoogleApiClient mClient = null;
    DecoView arcView;
    int series1Index;
    static DataSource ESTIMATED_STEP_DELTAS = new DataSource.Builder()
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .setAppPackageName("com.google.android.gms")
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_fit);
        JodaTimeAndroid.init(this);

        TextView tvTitle=findViewById(R.id.tvTitle);

            tvTitle.setText("Google Fit");

        ImageView imgLeft=findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_logout = (Button) findViewById(R.id.btn_logout);
        counter = findViewById(R.id.counter);
        txt_7days = findViewById(R.id.txt_7days);
        txt_steps = findViewById(R.id.txt_steps);
        txt_viewsteps = findViewById(R.id.txt_viewsteps);
        txt_dailysteps = findViewById(R.id.txt_dailysteps);
        txt_7cal = findViewById(R.id.txt_7cal);
        txt_energy = findViewById(R.id.txt_energy);
        DecimalFormat formatter = new DecimalFormat("#,###,###");
//        int num=
        String yourFormattedString = formatter.format(Integer.parseInt(getIntent().getStringExtra("step")));

        txt_dailysteps.setText(yourFormattedString+" Steps");
        steps=getIntent().getStringExtra("step");
//        btn_next = findViewById(R.id.btn_next);
//        if (hasFitPermission()) {
//            getTodayStepCount();
////            readStepCountDelta();
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                getHistoryData();
////                Toast.makeText(Fit.this, "Ok!!!" , Toast.LENGTH_LONG).show();
//
//            }
//
//        } else {
//            requestFitnessPermission();
//        }

        buildFitnessClient();
//        initialization();
        checkPermision();

         arcView = (DecoView)findViewById(R.id.dynamicArcView);
        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(22f)
                .build());

//Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setLineWidth(22f)
                .build();

         series1Index = arcView.addSeries(seriesItem1);
        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(1000)
                .setDuration(2000)
                .build());


////        arcView.addEvent(new DecoEvent.Builder(100).setIndex(series1Index).setDelay(8000).build());
////        arcView.addEvent(new DecoEvent.Builder(10).setIndex(series1Index).setDelay(12000).build());
//
//
//
//        btn_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),GfitHomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             abc();
            }
        });




//
    }


    private  void checkPermision(){

        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_DISTANCE_DELTA,FitnessOptions.ACCESS_READ)

                .build();


        GoogleSignInAccount account = getGoogleAccount();

        if(GoogleSignIn.hasPermissions(account,fitnessOptions)){
            GoogleSignIn.requestPermissions(GfitActivity.this,REQUEST_OAUTH,account,fitnessOptions);
        }



    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onStart() {
        super.onStart();
        // Connect to the Fitness API
        Log.i(TAG, "Connecting...");
        mClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mClient.isConnected()) {
            mClient.disconnect();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mClient.isConnecting() && !mClient.isConnected()) {
                    mClient.connect();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    /**
     *  Build a {@link GoogleApiClient} that will authenticate the user and allow the application
     *  to connect to Fitness APIs. The scopes included should match the scopes your app needs
     *  (see documentation for details). Authentication will occasionally fail intentionally,
     *  and in those cases, there will be a known resolution, which the OnConnectionFailedListener()
     *  can address. Examples of this include the user never having signed in before, or having
     *  multiple accounts on the device and needing to specify which account to use, etc.
     */
    private void buildFitnessClient() {
        // Create the Google API Client
//        assert Fitness.API != null;
        final Void API = null;

        mClient = new GoogleApiClient.Builder(this)
//                .addApi(Fitness.API)
                .addApi(Fitness.SENSORS_API)    // Required only if you're making SensorsApi calls
                .addApi(Fitness.RECORDING_API)  // Required only if you're making RecordingApi calls
                .addApi(Fitness.HISTORY_API)    // Required only if you're making HistoryApi calls
                .addApi(Fitness.SESSIONS_API)   // Required only if you're making SessionsApi calls
                // Optional: request more APIs with additional calls to addApi
                .useDefaultAccount()
                .addScope(Fitness.SCOPE_ACTIVITY_READ)
                .addScope(Fitness.SCOPE_ACTIVITY_READ_WRITE)


//                .addScope(new Scope(Scopes.PLUS_LOGIN))
                .addConnectionCallbacks(
                        new GoogleApiClient.ConnectionCallbacks() {

                            @SuppressLint("LongLogTag")
                            @Override
                            public void onConnected(Bundle bundle) {
                                Log.i(TAG, "Connected!!!");
                                // Now you can make calls to the Fitness APIs.
                                // Put application specific code here.


                                getTodayStepCount();
//            readStepCountDelta();


                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    getHistoryData();
//                Toast.makeText(Fit.this, "Ok!!!" , Toast.LENGTH_LONG).show();

                                }


                            }

                            @SuppressLint("LongLogTag")
                            @Override
                            public void onConnectionSuspended(int i) {
                                // If your connection to the sensor gets lost at some point,
                                // you'll be able to determine the reason and react to it here.
                                if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST) {
                                    Log.i(TAG, "Connection lost.  Cause: Network Lost.");
                                } else if (i == GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED) {
                                    Log.i(TAG, "Connection lost.  Reason: Service Disconnected");
                                }
                            }
                        }
                )
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            // Called whenever the API client fails to connect.
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onConnectionFailed(ConnectionResult result) {
                                Log.i(TAG, "Connection failed. Cause: " + result.toString());
                                if (!result.hasResolution()) {
                                    // Show the localized error dialog
                                    GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
                                            GfitActivity.this, 0).show();
                                    return;
                                }
                                // The failure has a resolution. Resolve it.
                                // Called typically when the app is not yet authorized, and an
                                // authorization dialog is displayed to the user.
                                if (!authInProgress) {
                                    try {
                                        Log.i(TAG, "Attempting to resolve failed connection");
                                        authInProgress = true;
                                        result.startResolutionForResult(GfitActivity.this,
                                                REQUEST_OAUTH);
                                    } catch (IntentSender.SendIntentException e) {
                                        Log.e(TAG,
                                                "Exception while starting resolution activity", e);
                                    }
                                }
                            }
                        }
                )
                .build();
    }


    private  void  abc(){
        Fitness.getConfigClient(this, GoogleSignIn.getAccountForExtension(this, fitnessOptions))
                .disableFit()
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(GfitActivity.this, "DisConnected!!!" , Toast.LENGTH_LONG).show();
                                finish();




                            }
                        });
    }
//
    private void requestFitnessPermission() {
        GoogleSignIn.requestPermissions(
                this,
                REQUEST_OAUTH_REQUEST_CODE,
                getGoogleAccount(),
                getFitnessSignInOptions());
    }

    private boolean hasFitPermission() {
        // Request permission to collect Google Fit data
        fitnessOptions = getFitnessSignInOptions();
        return GoogleSignIn.hasPermissions(getGoogleAccount(), fitnessOptions);
    }
    private GoogleSignInAccount getGoogleAccount(){
        fitnessOptions = getFitnessSignInOptions();
        return  GoogleSignIn.getAccountForExtension(GfitActivity.this,fitnessOptions);
    }
    private FitnessOptions getFitnessSignInOptions() {
        // Request access to step count data from Fit history
        return FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA,FitnessOptions.ACCESS_READ)
//                .addDataType(DataType.TYPE_STEP_COUNT_DELTA,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_CALORIES_EXPENDED,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_DISTANCE_DELTA,FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_DISTANCE_DELTA,FitnessOptions.ACCESS_READ)
                .build();
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // When the user has accepted the use of Fit data, subscribeStepCount to record data
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
//                Log.i(TAG, "Fitness permission granted");
//
//                getTodayStepCount();
////                subscribeStepCount();
////                readStepCountDelta(); // Read today's data
////                readHistoricStepCount(); // Read last weeks data
//            }
//        } else {
//            Log.i(TAG, "Fitness permission denied");
//        }
//    }

    private void getTodayStepCount() {
        if (!hasFitPermission()) {
            requestFitnessPermission();
            return;
        }
//        subscribeStepCount();
//        String supportedType = DataType.getMimeType(DataType.TYPE_STEP_COUNT_DELTA);
////        Toast.makeText(MainActivity.this, "" + supportedType, Toast.LENGTH_LONG).show();
//
//        if (Intent.ACTION_VIEW.equals(getIntent().getAction()) && supportedType.equals(getIntent().getType()))
//        {
//            // Get the intent extras
//            long startTime = Fitness.getStartTime(getIntent(), TimeUnit.MILLISECONDS);
//            long endTime = Fitness.getEndTime(getIntent(), TimeUnit.MILLISECONDS);
//            DataSource dataSource = DataSource.extract(getIntent());
//            Toast.makeText(GfitActivity.this, "" + startTime+endTime+" "+dataSource, Toast.LENGTH_LONG).show();
//
//        }



        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener(this);
        Fitness.getHistoryClient(this,  getGoogleAccount())
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener(this);
        Fitness.getHistoryClient(this,  getGoogleAccount())
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener(this);
        Fitness.getHistoryClient(this,  getGoogleAccount())
                .readDailyTotal(DataType.TYPE_WEIGHT)
                .addOnSuccessListener(this);
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readDailyTotal(DataType.TYPE_HEART_POINTS)
                .addOnSuccessListener(this);
        Fitness.getHistoryClient(this,  getGoogleAccount())
                .readDailyTotal(DataType.TYPE_HEIGHT)
                .addOnSuccessListener(this);


    }


    @Override
    public void onSuccess(DataSet dataSet){

            List<DataPoint> dataPoints = dataSet.getDataPoints();
            for (DataPoint dataPoint : dataPoints) {

                for (Field field : dataPoint.getDataType().getFields()) {
                    float value = Float.parseFloat(dataPoint.getValue(field).toString());

//                float abc =Float.parseFloat(new DecimalFormat("#.##").format(value));
//                float abc1 =Float.parseFloat(new DecimalFormat("#.##").format(value));
//                float abc2 =Float.parseFloat(new DecimalFormat("#.##").format(value));

//                Toast.makeText(MainActivity.this, "" + value+" "+abc1, Toast.LENGTH_LONG).show();


                    if (field.getName().equals(Field.FIELD_STEPS.getName())) {
                        float abc = Float.parseFloat(new DecimalFormat("#.##").format(value));
//                        Toast.makeText(GfitActivity.this, "step " + abc, Toast.LENGTH_LONG).show();
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String yourFormattedString = formatter.format((abc));
                    counter.setText(String.valueOf(yourFormattedString));
                        txt_steps.setText(String.valueOf(yourFormattedString));
                        txt_viewsteps.setText(String.valueOf(yourFormattedString+"\n Steps"));
                        int val= (int) (abc/Integer.parseInt(steps)*100);

                        arcView.addEvent(new DecoEvent.Builder(val).setIndex(series1Index).setDelay(4000).build());
                    }
                    if (field.getName().equals(Field.FIELD_CALORIES.getName())) {
                        float abc1 = Float.parseFloat(new DecimalFormat("#.##").format(value));
//                        Toast.makeText(GfitActivity.this, "cal " + abc1, Toast.LENGTH_LONG).show();
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String yourFormattedString = formatter.format((abc1));
                    txt_energy.setText(String.valueOf(yourFormattedString)+" cal");
                        txt_7cal.setText(String.valueOf(yourFormattedString)+" cal");

                    }
                    if (field.getName().equals(Field.FIELD_DISTANCE.getName())) {
                        float abc2 = Float.parseFloat(new DecimalFormat("#.##").format(value));
//                        Toast.makeText(GfitActivity.this, "distance " + abc2, Toast.LENGTH_LONG).show();
//                    weekCounter.setText(String.valueOf((int) abc2));
                    }
                    if (field.getName().equals(Field.FIELD_HEIGHT.getName())) {
                        float abc2 = Float.parseFloat(new DecimalFormat("#.##").format(value));
//                        Toast.makeText(GfitActivity.this, "distance " + abc2, Toast.LENGTH_LONG).show();
//                    txt_energy.setText(String.valueOf((int) abc2));
                    }
                    if (field.getName().equals(Field.FIELD_WEIGHT.getName())) {
                        float abc2 = Float.parseFloat(new DecimalFormat("#.##").format(value));
//                        Toast.makeText(GfitActivity.this, "distance " + abc2, Toast.LENGTH_LONG).show();
//                    txt_weight.setText(String.valueOf((int) abc2));
                    }
                    if (field.getName().equals(Field.FIELD_INTENSITY.getName())) {
                        float abc2 = Float.parseFloat(new DecimalFormat("#.##").format(value));
//                        Toast.makeText(GfitActivity.this, "distance " + abc2, Toast.LENGTH_LONG).show();
//                    txt_heart.setText(String.valueOf((int) abc2));
                    }


                }


            }


        }







    @RequiresApi(api = Build.VERSION_CODES.O)
    private  void  getHistoryData(){
        Calendar cal =Calendar.getInstance();

        cal.setTime(new Date());
//    long endTime = cal.getTimeInMillis();

        cal.set(2022,2,28);

        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
//    long startTime = cal.getTimeInMillis();
        ZonedDateTime endTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
        ZonedDateTime startTime = endTime.minusWeeks(1);

        DataReadRequest readRequest = new DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
                .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
                .aggregate(DataType.TYPE_CALORIES_EXPENDED)
                .aggregate(DataType.AGGREGATE_CALORIES_EXPENDED)
                .aggregate(DataType.TYPE_DISTANCE_DELTA)
                .aggregate(DataType.AGGREGATE_DISTANCE_DELTA)
//            .bucketByTime(1,TimeUnit.DAYS)
//            .bucketByActivityType(1, TimeUnit.SECONDS)

                .setTimeRange(startTime.toEpochSecond(),endTime.toEpochSecond(),TimeUnit.SECONDS)
                .bucketByTime(1,TimeUnit.DAYS)
                .build();
//
        Fitness.getHistoryClient(this, getGoogleAccount())
                .readData(readRequest)
//                .addOnSuccessListener( this);


                .addOnSuccessListener(
                        new OnSuccessListener<DataReadResponse>() {
                            @Override
                            public void onSuccess(DataReadResponse dataReadResponset) {
//                                long total = dataSet.isEmpty() ? 0 : dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
//                                dataSet.getDataPoints().firstOrNull()?.getValue(Field.FIELD_STEPS)?.asInt() ?: 0
//                                counter.setText(String.format(Locale.ENGLISH, "%d", total));

                                DataReadResponse dataReadResponse=(DataReadResponse) dataReadResponset;
                                List<Bucket> bucketList = dataReadResponse.getBuckets();
                                for (Bucket bucket : bucketList) {
//if() {
//    DataSet dataSet1 = bucket.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
//}
                                    DataSet dataSet2=bucket.getDataSet(DataType.TYPE_STEP_COUNT_DELTA);
                                    getresult(dataSet2);
                                    DataSet dataSet3=bucket.getDataSet(DataType.TYPE_CALORIES_EXPENDED);
                                    getresult(dataSet3);
                                    DataSet dataSet4=bucket.getDataSet(DataType.TYPE_DISTANCE_DELTA);
                                    getresult(dataSet4);

//                                        Toast.makeText(MainActivity.this, "" + dataSet1+dataSet2+dataSet3+dataSet4, Toast.LENGTH_LONG).show();




                                }
//dataSet.getDataType().getFields().get(0)

//                                Toast.makeText(MainActivity.this, ""+total, Toast.LENGTH_LONG).show();
                            }
                        });




    }

    private void getresult(DataSet dataSet){

        List<DataPoint> dataPoints = dataSet.getDataPoints();
        for (DataPoint dataPoint : dataPoints) {

            for (Field field : dataPoint.getDataType().getFields()) {
                float value = Float.parseFloat(dataPoint.getValue(field).toString());

                if (field.getName().equals(Field.FIELD_STEPS.getName())) {
                    float abc = Float.parseFloat(new DecimalFormat("#.##").format(value));

                    abc1 = abc+abc1;
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String yourFormattedString = formatter.format((abc1));

                    txt_7days.setText(String.valueOf(yourFormattedString));
//                    Toast.makeText(GfitActivity.this, "" + abc1, Toast.LENGTH_LONG).show();
                }
                if (field.getName().equals(Field.FIELD_CALORIES.getName())) {
                    float abc1 = Float.parseFloat(new DecimalFormat("#.##").format(value));
                    abc2 = abc1+abc2;
                    DecimalFormat formatter = new DecimalFormat("#,###,###");
                    String yourFormattedString = formatter.format((abc2));

                    txt_7cal.setText(String.valueOf(yourFormattedString));
//                    Toast.makeText(GfitActivity.this, "" + abc1, Toast.LENGTH_LONG).show();
                }
                if (field.getName().equals(Field.FIELD_DISTANCE.getName())) {
                    float abc2 = Float.parseFloat(new DecimalFormat("#.##").format(value));
//                    Toast.makeText(GfitActivity.this, "" + abc2, Toast.LENGTH_LONG).show();
                }



            }
        }



    }











}
