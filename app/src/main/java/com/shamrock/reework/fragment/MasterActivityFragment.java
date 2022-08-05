package com.shamrock.reework.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shamrock.R;
import com.shamrock.reework.activity.DailyActivityModule.Service.FitBitClient;
import com.shamrock.reework.activity.DailyActivityModule.Service.FitBitService;
import com.shamrock.reework.activity.DailyActivityModule.model.AddUpdateActivityRequest;
import com.shamrock.reework.activity.DailyActivityModule.model.AddUpdateActivityResponse;
import com.shamrock.reework.activity.HomeModule.service.HomeFragmentService;
import com.shamrock.reework.adapter.MasterLastActivityAdapter;
import com.shamrock.reework.adapter.MasterTrackActivityAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.model.ActivityWeeklyResponse;
import com.shamrock.reework.model.MasterActivityModel;
import com.shamrock.reework.model.MasterLastActivityModel;
import com.shamrock.reework.model.TokenResponse;
import com.shamrock.reework.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MasterActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MasterActivityFragment extends Fragment implements FitBitActivity.onGetTokenListner
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Utils utils;
    HomeFragmentService service;
    SessionManager sessionManager;
    private int userId;
    FitBitService fitBitClient;

    private Context context;
    Button button;
    LinearLayout linearLayout;
    RecyclerView recyclerView_MyActivity, recyclerView_LastActivity;
    private ArrayList<MasterActivityModel> dataSet;
    private ArrayList<MasterLastActivityModel> dataSetLast;
    MasterTrackActivityAdapter masterTrackActivityAdapter;
    MasterLastActivityAdapter masterLastActivityAdapter;
    Animation animSlideDown;
    static String[] nameArray = {"Walking ", "Running", "Sports", "Swimming", "Gym", "Yoga", "Climbing stairs", "Dancing", "Other"};
    static String[] hrsArray = {"1hr", "30min", "1hr", "25min", "1hr", "20min", "30min", "50min", "0hr"};
    static Integer[] activity_type = {101, 102, 103, 104, 105, 106, 107, 108, 0};
    static Integer[] activity_calories = {1500, 500, 500, 600, 1000, 100, 300, 400, 0};

    static String[] lastDates = {"20, Nov 2018", "19, Nov 2018", "18, Nov 2018", "17, Nov 2018", "16, Nov 2018", "15, Nov 2018", "14, Nov 2018"};
    static Integer[] lastCalories = {3000, 2000, 1000, 4000, 2000, 1000, 1500};



    //for Fitbit API Values
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String clientID = "22B6SC";
    String strRedirectURl ="com.intelegain.fitbitgadgetintegration.FitBitActivity://auth/fitbit";
    String response_type  = "code";
    String scope = "activity+nutrition+heartrate+location+nutrition+profile+settings+sleep+social+weight";
    String AuthCode  = "";
    String CLIENT_SECRETE_KEY ="60d48429f9e4968d39ad9dce9ddec68e";
    String base64Encoded;
    String SharedPre="SHARED_PREFERENCE";
    String txtAuthorization = "Basic"+" "+"MjJCNlNDOjYwZDQ4NDI5ZjllNDk2OGQzOWFkOWRjZTlkZGVjNjhl";
    String formattedDate="",dt_InsertedOn = "";
    public static final String EXTRA_KEY_TEST = "testKey";
    boolean  isLoggedIn = false;
    public static final int REQUEST_CODE = 11;
    public static final int RESULT_CODE = 12;

    private OnFragmentInteractionListener mListener;

    public MasterActivityFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MasterActivityFragment.
     */
    public static MasterActivityFragment newInstance(String param1, String param2)
    {
        MasterActivityFragment fragment = new MasterActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sessionManager = new SessionManager(context);
        service = Client.getClient().create(HomeFragmentService.class);
        fitBitClient = FitBitClient.getClient().create(FitBitService.class);
        utils = new Utils();
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_master_activity, container, false);

        linearLayout = view.findViewById(R.id.linLay_activity_week_analysis);
        button = view.findViewById(R.id.buttonActivity_ViewMore);
        recyclerView_MyActivity = view.findViewById(R.id.recycleView_TrackMyActivity);
        recyclerView_LastActivity = view.findViewById(R.id.recycleView_LastActivity);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-mm");
        SimpleDateFormat dfInsertedOn = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);
        dt_InsertedOn  = dfInsertedOn.format(c);
     /*   getActivity().getSharedPreferences(SharedPre,0).edit().clear().commit();*/
          isLoggedIn  = getActivity().getSharedPreferences(SharedPre,0).getBoolean("isLoggedIn",false);

        dataSet = new ArrayList<>();
        // below condition used to add header row
        try {
            if (nameArray.length > 0) {
                MasterActivityModel model = new MasterActivityModel();
                model.setActivityType(0);
                model.setActivityName("");
                model.setActivityDuration("");
                model.setCalories(0);
                dataSet.add(model);
            }
            // below condition adds data
            for (int i = 0; i < nameArray.length; i++) {
                MasterActivityModel model = new MasterActivityModel();
                model.setActivityType(activity_type[i]);
                model.setActivityName(nameArray[i]);
                model.setActivityDuration(hrsArray[i]);
                model.setCalories(activity_calories[i]);
                dataSet.add(model);
            }
            masterTrackActivityAdapter = new MasterTrackActivityAdapter(context, dataSet);
            recyclerView_MyActivity.setLayoutManager(new LinearLayoutManager(context));
            recyclerView_MyActivity.setAdapter(masterTrackActivityAdapter);


        }catch (Exception e){
            e.printStackTrace();
        }
        dataSetLast = new ArrayList<>();
        // below condition used to add header row
       /* if (lastDates.length > 0) {
            MasterLastActivityModel model = new MasterLastActivityModel();
            dataSetLast.add(model);
        }
        // below condition adds data
        for (int i = 0; i < lastDates.length; i++) {
            MasterLastActivityModel model = new MasterLastActivityModel();
            model.setDate(lastDates[i]);
            model.setCalories(lastCalories[i]);
            dataSetLast.add(model);
        }*/
        masterLastActivityAdapter = new MasterLastActivityAdapter(context, dataSetLast);
        recyclerView_LastActivity.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_LastActivity.setAdapter(masterLastActivityAdapter);

        // load the animation
        animSlideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (linearLayout.getVisibility() == View.VISIBLE) {
                    linearLayout.clearAnimation();
//                    linearLayout.startAnimation(animSlideUp);
                    linearLayout.setVisibility(View.GONE);
                    button.setText("View Last 7 days");
//                    flipCard();
                } else {
                    linearLayout.clearAnimation();
                    linearLayout.setVisibility(View.VISIBLE);
                    button.setText("Less");
                    linearLayout.startAnimation(animSlideDown);
//                    flipCard();
                }
            }
        });
        if (Utils.isNetworkAvailable(context)) {

            try {
                CallToFetchWeeklyActivities();

            }catch (Exception e){
                e.printStackTrace();
            }

        }else
            Toast.makeText(context, "No internet !", Toast.LENGTH_LONG).show();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }else{
            if(isLoggedIn==false) {
                Intent intent = new Intent(context, FitBitActivity.class);
                intent.putExtra(getResources().getString(R.string.coming_from),"MasterActivityFragment");
                startActivity(intent);
            }else{

                String Token = context.getSharedPreferences(SharedPre, 0).getString("RefreshToken", "");
                String TokenType = context.getSharedPreferences(SharedPre, 0).getString("TokenType", "");
                String UserId = context.getSharedPreferences(SharedPre, 0).getString("UserId", "");
                callToGetAllActivitiesApi(TokenType + " " + Token, UserId, formattedDate+".json");

            }
        }

        //INSERT CUSTOM CODE HERE
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onGetToken(String Token) {
        Toast.makeText(context, "AuthCode is "+Token, Toast.LENGTH_SHORT).show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            String testResult = data.getStringExtra(EXTRA_KEY_TEST);
            Toast.makeText(context, "AuthCode is "+testResult, Toast.LENGTH_SHORT).show();
            // TODO: Do something with your extra data
        }
    }

    private void CallToFetchWeeklyActivities()
    {
        utils.showProgressbar(context);


        Call<ActivityWeeklyResponse> call = service.GetActivityHistory(5);
        call.enqueue(new Callback<ActivityWeeklyResponse>()
        {
            @Override
            public void onResponse(Call<ActivityWeeklyResponse> call, retrofit2.Response<ActivityWeeklyResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ActivityWeeklyResponse sleepResponse = response.body();

                    if (sleepResponse != null && sleepResponse.getCode() == 1)
                    {
                        if (sleepResponse.getData() != null)
                        {
                            dataSetLast.clear();
                            for(int i =0;i<response.body().getData().size();i++){
                                MasterLastActivityModel model  = new MasterLastActivityModel();
                                model.setDate(response.body().getData().get(i).getDate());
                                model.setCalories(response.body().getData().get(i).getCalorieBurn());
                                dataSetLast.add(model);
                            }
                            masterTrackActivityAdapter.notifyDataSetChanged();


                        }
                    }
                    else
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ActivityWeeklyResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                Log.e("ERROR------>", t.toString());
            }
        });
    }

    //Calling AddUpdateDailyActivity  API
    private void callingForAddUpdateDailyActivity(AddUpdateActivityRequest request)
    {
        utils.showProgressbar(context);
        Call<AddUpdateActivityResponse> call = service.addUpdateDailyActivity(request);
        call.enqueue(new Callback<AddUpdateActivityResponse>()
        {
            @Override
            public void onResponse(Call<AddUpdateActivityResponse> call, Response<AddUpdateActivityResponse> response)
            {
                utils.hideProgressbar();
             if(response.body()!=null){
                 if(response.body().getCode()==1 ){
                     Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_LONG).show();
                 }else{
                     Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_LONG).show();
                 }
             }

            }
            @Override
            public void onFailure(Call<AddUpdateActivityResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                // Log error here since request failed
                String strError = t.getMessage();
                Toast.makeText(context,strError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    //FitBit API's calling

    //Calling Token Refresh API
    private void callingForRefreshToken()
    {
        utils.showProgressbar(context);
        String AuthCode  = context.getSharedPreferences(SharedPre,0).getString("AuthCode","");
        String RefreshToken  = context.getSharedPreferences(SharedPre,0).getString("RefreshToken","");
        Call<TokenResponse> call = fitBitClient.getRefreshToken(txtAuthorization,"refresh_token",RefreshToken);
        call.enqueue(new Callback<TokenResponse>()
        {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response)
            {
                utils.hideProgressbar();
                if(response.isSuccessful()) {
                    //  JsonObject object = new JsonObject()(response.body());
                    editor.putString("AccessToken",response.body().getAccessToken());
                    editor.putString("RefreshToken",response.body().getRefreshToken());
                    editor.putString("TokenType",response.body().getTokenType());
                    editor.putString("UserId",response.body().getUserId());
                    editor.commit();
//                    callToGetAllActivitiesApi(response.body().getTokenType()+" "+response.body().getAccessToken(),response.body().getUserId(),formattedDate+".json");
                }
            }
            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t)
            {
                utils.hideProgressbar();
                // Log error here since request failed
                String strError = t.getMessage();
                Toast.makeText(context,strError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }




    //Calling Activities API
    private void callToGetAllActivitiesApi(String beare, String id, String date)
    {
        utils.showProgressbar(context);
        //response_type
//        Call<ResponseBody> call = fitBitClient.getAllActivities(beare,id,date);

        String finalURL  = fitBitClient.getAllActivities(beare,id,date).request().url().toString();
        String test = finalURL;
//        call.enqueue(new Callback<ResponseBody>()
//        {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
//            {
//                utils.hideProgressbar();
//                //Check Wheater token is Expire if yes the refresh token.
//                if(response.code()==401){
//                    callingForRefreshToken();
//                }
//                else if(response.code()==200){
//                    AddUpdateActivityRequest request = new AddUpdateActivityRequest();
//                    request.setDtInsertedOn(dt_InsertedOn);
//                    request.setIntUserId(String.valueOf(userId));
//                    callingForAddUpdateDailyActivity(request);
//
//                }
//                else{
//                    Toast.makeText(context,response.body().toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t)
//            {
//                utils.hideProgressbar();
//                // Log error here since request failed
//                String strError = t.getMessage();
//                Toast.makeText(context,strError.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
    }


}
