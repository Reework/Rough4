package com.shamrock.reework.activity.Pathologists;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.shamrock.R;
import com.shamrock.reework.activity.AppoinmentModule.activity.AppoinmentScheduleActivity;
import com.shamrock.reework.activity.AppoinmentModule.adapter.MyAppointmentsAdapter;
import com.shamrock.reework.activity.AppoinmentModule.dialog.MyAppoinmentEditDialog;
import com.shamrock.reework.activity.AppoinmentModule.service.AppoinmentService;
import com.shamrock.reework.activity.BloodTestModule.activity.SnipeetActivity;
import com.shamrock.reework.activity.HomeModule.activity.HomeActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.ChangeReecoachActivity;
import com.shamrock.reework.activity.MyRecoachModule.activity.MyReecoachProfileActivity;
import com.shamrock.reework.activity.MyRecoachModule.adapters.AdditionalQnAdapter;
import com.shamrock.reework.activity.MyRecoachModule.service.ReecoachService;
import com.shamrock.reework.activity.mybcaplan.ClsMyBCAPlanActivity;
import com.shamrock.reework.activity.mypathoplan.ClsMyPathoPlanActivity;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.api.request.AppoinmentEditRequest;
import com.shamrock.reework.api.request.GetAllAppointmentReq;
import com.shamrock.reework.api.request.ReecoachDetailsRequest;
import com.shamrock.reework.api.response.AppoinmentResponse;
import com.shamrock.reework.api.response.GetAllAppointmentResponse;
import com.shamrock.reework.api.response.ReecoachDetailsResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shamrock.reework.util.Utils.isValidContextForGlide;

public class PathologistMainActivity extends AppCompatActivity implements   View.OnClickListener, MyAppointmentsAdapter.MyAppointmentListener,
        MyAppoinmentEditDialog.ApponmentEditInterface{

    private static final String TAG = "MyReecoachProfile";
    Context context;
    Toolbar toolbar;
    Typeface font;
    RadioButton rd_button_reecoach_appointment;
    ImageView imgReecoachBg, imgReecoach;
    TextView textView_Name, textView_MobNo, textView_Email, textView_RegAddress, textView_NewAddress;
    SessionManager sessionManager;
    ReecoachService reecoachService;
    Utils utils;
    String email;
    int userId;ImageView imgViewNext_roacoach;
    private ProgressBar progressBar;
    Button btn_change_reecoach;
    RecyclerView recyler_reecoach_add_info;
    RadioButton rd_button__reecoach_profile;

    //appointmnt
    LinearLayout btnScheduleAppointment;
    Button buttonSleep_ViewMore;
    RecyclerView recyclerView;
    ArrayList<GetAllAppointmentResponse.AppointmentData> appointmentDataArrayList;
    MyAppointmentsAdapter adapter;
    MyAppoinmentEditDialog editDialog;
    AppoinmentService appoinmentService;
    public static final int REQ_FOR_APPOINMENT = 1000;
    LinearLayout mainLayout;
    RelativeLayout noInternetLayout;
    Button btnRefresh;
    TextView txtNoData;
    private String dummydate_from;
    private String dummydate_to;
    private String submitFromDate;
    private String submitToDate;
    LinearLayout ll_weight_header;
    private TextView  txt_no_weight;
    ViewFlipper vp_reecoach;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathologist_main);
        context=PathologistMainActivity.this;
        recyler_reecoach_add_info=findViewById(R.id.recyler_reecoach_add_info);
        init();
        setToolBar();
        findViews();
        initAppointment();
        findViewsAppoimtent();
        TextView txt_path_plam_my=findViewById(R.id.txt_path_plam_my);
        txt_path_plam_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PathologistMainActivity.this, ClsMyPathoPlanActivity.class) );

            }
        });
        TextView rd_button_patho_plan=findViewById(R.id.rd_button_patho_plan);
        rd_button_patho_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PathologistMainActivity.this, ClsMyPathoPlanActivity.class) );

            }
        });
    }
    private void init()
    {
        sessionManager = new SessionManager(context);
        reecoachService = Client.getClient().create(ReecoachService.class);
        utils = new Utils();
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
        tvTitle.setText("Pathologist");
//        tvTitle.setTypeface(font);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vp_reecoach.getDisplayedChild()==1){
                     rd_button__reecoach_profile.performClick();
                     return;
                }
                startActivity(new Intent(PathologistMainActivity.this, HomeActivity.class) );
                finish();
                // exitDialog("Sure, you want to leave the page? On clicking 'OK' the data will not be saved. ");
            }
        });
    }

    private void findViews()
    {
        imgReecoachBg = findViewById(R.id.imageView_ReecoachProfile_Photo_bg);
        btn_change_reecoach = findViewById(R.id.btn_change_reecoach);
        btn_change_reecoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(PathologistMainActivity.this, ChangePathoActivity.class);
                startActivityForResult(intent,205);

            }
        });


        imgViewNext_roacoach = findViewById(R.id.imgViewNext_roacoach);
        imgReecoach = findViewById(R.id.imageView_ReecoachProfile_Photo);
        textView_Name = findViewById(R.id.text_ReecoachProfile_Name);
        textView_MobNo = findViewById(R.id.text_ReecoachProfile_Mobile);
        textView_Email = findViewById(R.id.text_ReecoachProfile_Email);
        textView_RegAddress = findViewById(R.id.text_ReecoachProfile_RegAddress);
        textView_NewAddress = findViewById(R.id.text_ReecoachProfile_NewAddress);
        progressBar = findViewById(R.id.progress);
        rd_button_reecoach_appointment = findViewById(R.id.rd_button_reecoach_appointment);
        vp_reecoach=findViewById(R.id.vp_reecoach);
        vp_reecoach.setDisplayedChild(0);
        email = sessionManager.getStringValue(SessionManager.KEY_USER_EMAIL);
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
        if (Utils.isNetworkAvailable(context))
        {
            callProfileDetailsAPI(email, userId);
        }

    }

    private void callProfileDetailsAPI(String email, int userid) {

        ReecoachDetailsRequest request = new ReecoachDetailsRequest();
//        request.setEmail(email);
        request.setUserid(userid);

        Call<ReecoachDetailsResponse> call = reecoachService.getPathologiesProfileDetails(request);
        call.enqueue(new Callback<ReecoachDetailsResponse>()
        {
            @Override
            public void onResponse(Call<ReecoachDetailsResponse> call, Response<ReecoachDetailsResponse> response)
            {
//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ReecoachDetailsResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        ReecoachDetailsResponse.DataResponse dataResponse = listResponse.getData();

                        if (dataResponse != null)
                        {
                            btn_change_reecoach.setText("Change Pathologist");

                            String reecoachName = dataResponse.getFirstName() + " " + dataResponse.getLastName();
                            sessionManager.setStringValue("pathologistname",reecoachName);
                            String reecoachMobile = dataResponse.getMobileNo();
                            String reecoachEmail = dataResponse.getEmail();
                            String reecoachAddress = dataResponse.getAddress();
                            String imgUrl = dataResponse.getImageUrl();


                            if (!TextUtils.isEmpty(reecoachName))
                                if (dataResponse.getReecoachType()!=null&&!dataResponse.getReecoachType().trim().isEmpty()){




                                    String type=dataResponse.getReecoachType().substring(0,dataResponse.getReecoachType().length()-2);

                                    String strReecoachType="("+type+")";

                                    textView_Name.setText(reecoachName+strReecoachType);
                                }else {
                                    textView_Name.setText(reecoachName);
                                }


                            if (!TextUtils.isEmpty(reecoachMobile))
                                textView_MobNo.setText(reecoachMobile);

                            if (!TextUtils.isEmpty(reecoachEmail))
                                textView_Email.setText(reecoachEmail);

                            if (!TextUtils.isEmpty(reecoachAddress))
                                textView_RegAddress.setText(reecoachAddress);

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_profile_pic_bg)
                                    .error(R.drawable.ic_profile_pic_bg)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .priority(Priority.HIGH);



                            if (listResponse.getData().getAdditionalDetails()!=null&&!listResponse.getData().getAdditionalDetails().isEmpty()){

                                recyler_reecoach_add_info.setAdapter(new AdditionalQnAdapter(PathologistMainActivity.this,listResponse.getData().getAdditionalDetails()));

                            }


                            if (isValidContextForGlide(context))
                            {
                                // for background Image
                                Glide.with(context)
                                        .load(imgUrl)
                                        .apply(options)
                                        .into(imgReecoachBg);

                                // for profile Image
                                Glide.with(context)
                                        .load(imgUrl)
                                        .apply(options)
                                        .apply(RequestOptions.circleCropTransform())
                                        .listener(new RequestListener<Drawable>()
                                        {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                                        Target<Drawable> target, boolean isFirstResource)
                                            {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                                           DataSource dataSource, boolean isFirstResource)
                                            {
                                                progressBar.setVisibility(View.GONE);
                                                return false;
                                            }
                                        })
                                        .into(imgReecoach);
                            }
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "" + listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    progressBar.setVisibility(View.GONE);
                    btn_change_reecoach.setText("Select Pathologist");
                    Toast.makeText(context, "You have not selected any pathologist ,Please select Pathologist first", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ReecoachDetailsResponse> call, Throwable t)
            {
                // Log error here since request failed
                progressBar.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void initAppointment()
    {
        appoinmentService = Client.getClient().create(AppoinmentService.class);
        utils = new Utils();
        sessionManager = new SessionManager(context);
    }


    private void findViewsAppoimtent()
    {
        recyclerView = findViewById(R.id.rvAppoinments);
        btnScheduleAppointment = findViewById(R.id.buttonScheduleAppointment);
        buttonSleep_ViewMore = findViewById(R.id.buttonSleep_ViewMore);
        txtNoData = findViewById(R.id.txtNoData);

        appointmentDataArrayList = new ArrayList<>();
        adapter = new MyAppointmentsAdapter(context, appointmentDataArrayList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        btnScheduleAppointment.setOnClickListener(this);

        mainLayout = findViewById(R.id.mainLayout);
        noInternetLayout = findViewById(R.id.no_internet);
        btnRefresh= findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(this);


        final ViewFlipper vp_reecoach=findViewById(R.id.vp_reecoach);
        vp_reecoach.setDisplayedChild(0);
         rd_button__reecoach_profile=findViewById(R.id.rd_button__reecoach_profile);
        rd_button__reecoach_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_change_reecoach.setVisibility(View.VISIBLE);
                vp_reecoach.setDisplayedChild(0);
                if (Utils.isNetworkAvailable(context))
                {
                    callProfileDetailsAPI(email, userId);
                }

            }
        });

        RadioButton rd_button_chat=findViewById(R.id.rd_button_chat);
        rd_button_chat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btn_change_reecoach.setVisibility(View.GONE);

                vp_reecoach.setDisplayedChild(2);

            }
        });


        rd_button_reecoach_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_change_reecoach.setVisibility(View.GONE);
                vp_reecoach.setDisplayedChild(1);

            }
        });


        if (Utils.isNetworkAvailable(context))
            callToGetAllAppoinments(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager.getStringValue("Back").equalsIgnoreCase("true")){
            callToGetAllAppoinments(false);
            sessionManager.setStringValue("Back","");

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_FOR_APPOINMENT && resultCode == RESULT_OK)
        {
            if (data != null && data.hasExtra("RESULT"))
            {
                if (data.getStringExtra("RESULT").equals("ok"))
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        callToGetAllAppoinments(false);
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == 205 && resultCode == RESULT_OK)
        {
            if (data != null && data.hasExtra("Reeacoach"))
            {
                if (data.getStringExtra("Reeacoach").equals("yes"))
                {
                    if (Utils.isNetworkAvailable(context))
                    {
                        callProfileDetailsAPI(email, userId);

                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                                , Snackbar.LENGTH_SHORT).show();
                }
            }
        }


    }

    /* Get clicked position here */
    @Override
    public void GetClickedAppointment(String type, int position, GetAllAppointmentResponse.AppointmentData model)
    {
        if (!TextUtils.isEmpty(type))
        {
            if (type.equals("edit"))
            {
                FragmentManager fm = getSupportFragmentManager();
                editDialog = new MyAppoinmentEditDialog();

                Bundle bundle = new Bundle();
                bundle.putSerializable("MODEL", model);
                editDialog.setArguments(bundle);
                editDialog.show(fm, "edit_fragment");
            }
            else
            {
                deleteAppoinment(position, model);
            }
        }
    }

    @Override
    public void onEdit(GetAllAppointmentResponse.AppointmentData model)
    {
        if (Utils.isNetworkAvailable(context))
            callToGetAllAppoinments(false);
        else
            Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show();
    }

    public void deleteAppoinment(final int pos , final GetAllAppointmentResponse.AppointmentData model)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Delete appointment!")
                .setMessage("Do you really want to delete this appointment?")
                .setCancelable(false)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
//                        Toast.makeText(context, "" + myMedicine.getMedName(), Toast.LENGTH_SHORT).show();
                        if (Utils.isNetworkAvailable(context))
                        {
                            utils.showProgressbar(context);

                            callDeleteAppoinment(pos, model);
                        }
                        else
                        {
                            Utils.shortToast(context, getString(R.string.internet_connection_unavailable));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void callDeleteAppoinment(final int pos , GetAllAppointmentResponse.AppointmentData model)
    {
        AppoinmentEditRequest request = new AppoinmentEditRequest();
        request.setUserID(model.getUserID());
        request.setApptID(model.getApptID());

        Call<AppoinmentResponse> call = appoinmentService.deleteAppoinment(request);
        call.enqueue(new Callback<AppoinmentResponse>()
        {
            @Override
            public void onResponse(Call<AppoinmentResponse> call, Response<AppoinmentResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    AppoinmentResponse listResponse = response.body();

                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        // call List API to reload list
                        appointmentDataArrayList.remove(pos);
                        adapter.notifyDataSetChanged();
                        callToGetAllAppoinments(false);
                    }
                    else
                    {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(context, "Server down: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppoinmentResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("ACTV::", t.toString());
                utils.hideProgressbar();
            }
        });
    }
    private void callToGetAllAppoinments(final boolean isSwipeToRefresh)
    {
        if (!((Activity) context).isFinishing())
        {
            if(!isSwipeToRefresh)
                utils.showProgressbar(context);
        }

        int userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);

        GetAllAppointmentReq request = new GetAllAppointmentReq();
        request.setRoleId(4);
        request.setUserID(userId);


        Call<GetAllAppointmentResponse> call = appoinmentService.getAllAppoinments(request);
        call.enqueue(new Callback<GetAllAppointmentResponse>()
        {
            @Override
            public void onResponse(Call<GetAllAppointmentResponse> call, Response<GetAllAppointmentResponse> response)
            {
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    GetAllAppointmentResponse appointmentResponse = response.body();

                    if (appointmentResponse != null && appointmentResponse.getCode() == 1)
                    {
                        ArrayList<GetAllAppointmentResponse.AppointmentData>  tempList = appointmentResponse.getData();

                        if (tempList != null && tempList.size() > 0)
                        {
                            appointmentDataArrayList.clear();
                            appointmentDataArrayList.addAll(tempList);
                            adapter.notifyDataSetChanged();
                            txtNoData.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }else{
                            txtNoData.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                    else

if (vp_reecoach.getDisplayedChild()==1){

    txtNoData.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.GONE);
}
                }

            }

            @Override
            public void onFailure(Call<GetAllAppointmentResponse> call, Throwable t)
            {
                Log.e("ERROR---->", t.toString());
                if (!isSwipeToRefresh)
                    utils.hideProgressbar();

//                if (swipeRefreshLayout.isRefreshing())
//                    swipeRefreshLayout.setRefreshing(false);
//                ShowRetryBar("" );
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {



            case R.id.buttonScheduleAppointment:

                Intent intent=new Intent(context, AppoinmentScheduleActivity.class);
                intent.putExtra("IsFromPatho",true);

                startActivityForResult(intent, REQ_FOR_APPOINMENT);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;




            case R.id.buttonSleep_ViewMore:

                break;
        }

    }
}
