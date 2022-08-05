package com.shamrock.reework.activity.FoodModule.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.shamrock.BuildConfig;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.adapter.FoodRecipeDescriptionAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.FoodRecipeIngridentAdapter;
import com.shamrock.reework.activity.exoplayerview.ExoActivity;
import com.shamrock.reework.activity.newrecipe.ClsAllRecipeData;
import com.shamrock.reework.activity.newrecipe.ClsAllRecipeMainClass;
import com.shamrock.reework.activity.newrecipe.ClsEditRecipeMain;
import com.shamrock.reework.activity.newrecipe.NewRecipeEditDialouge;
import com.shamrock.reework.activity.recipe.adapter.RecipeMethodListAdapter;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.api.request.FoodTripFavoriteUpdateRequest;
import com.shamrock.reework.api.request.GetRecipeRequest;
import com.shamrock.reework.api.response.FoodTripFavoriteUpdateResponse;
import com.shamrock.reework.api.response.RecipeResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.activity.FoodModule.dialog.RecipeEditDialouge;
import com.shamrock.reework.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodRecipeActivity extends AppCompatActivity implements View.OnClickListener,RecipeEditDialouge.RecipeEditListener
{
    private  File file;
    Context context;
    Toolbar toolbar;
    Utils utils;
    RecyclerView recyler_recipemethod_new;
    FoodService foodService;
    SessionManager sessionManager;
    ImageView ivFood, ivfavorite, ivEdit;
    TextView tvRecipeName, tvCalorie, tvPrepTime, tvCookTime, tvTotalTime, tvIngridents, tvHowtomake,etIngri;
    TextView tvunit;
    TextView txt_calories_reciepe;
    LinearLayout ll_video;

    RecyclerView rvIngridents, rvHowtocook;
    RecipeEditDialouge editDialog;
    NewRecipeEditDialouge editDialog1;
    ImageView ivplay;

    ArrayList<RecipeResponse.Recipee> mRecipeList;
    FoodRecipeIngridentAdapter foodRecipeIngridentAdapter;
    FoodRecipeDescriptionAdapter foodRecipeDescriptionAdapter;
    private int recipeId, userId,editId;
    TextView txt_ingradcount_preparationtime;
    TextView etDesc;
    int colorBlue, colorBlack;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 108;
    boolean isFavorite;
    TextView tvservingqurantity;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer youTubePlayers;
    LinearLayout ll_recipe_header;
    TextView txt_carb_value;
    TextView txt_fat_value;
    TextView txt_fibre_value;
    TextView txt_protin_value;
    private ClsAllRecipeData allRecipeData;
    File files;
    private File imagePath;
    TextView txt_recipemethod,txt_ingradient,txt_nutrition;
    ViewFlipper vp_main_recipe;

    RecyclerView rvIngridents_new;
    public interface RecipeEditResponceListener
    {
        void onEditResponceRecipe(boolean res);
    }
    public  void store(Bitmap bm, String fileName){
        final  String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        files = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (youTubePlayerView!=null){
            youTubePlayerView.release();

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_recipe);
        ivplay = findViewById(R.id.ivplay);
        txt_nutrition = findViewById(R.id.txt_nutrition);
        txt_ingradient = findViewById(R.id.txt_ingradient);
        txt_recipemethod = findViewById(R.id.txt_recipemethod);
        recyler_recipemethod_new = findViewById(R.id.recyler_recipemethod_new);
        vp_main_recipe = findViewById(R.id.vp_main_recipe);
        vp_main_recipe.setDisplayedChild(0);
        txt_nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main_recipe.setDisplayedChild(0);
                txt_nutrition.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner_green));
                txt_ingradient.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner));
                txt_recipemethod.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner));

                txt_nutrition.setTextColor(getResources().getColor(R.color.white));
                txt_ingradient.setTextColor(getResources().getColor(R.color.title_gray));
                txt_recipemethod.setTextColor(getResources().getColor(R.color.title_gray));
                txt_nutrition.setPadding(20,20,20,20);
                txt_ingradient.setPadding(20,20,20,20);
                txt_recipemethod.setPadding(20,20,20,20);

            }
        });
        txt_ingradient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main_recipe.setDisplayedChild(1);
                txt_nutrition.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner));
                txt_ingradient.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner_green));
                txt_recipemethod.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner));

                txt_nutrition.setTextColor(getResources().getColor(R.color.title_gray));
                txt_ingradient.setTextColor(getResources().getColor(R.color.white));
                txt_recipemethod.setTextColor(getResources().getColor(R.color.title_gray));
                txt_nutrition.setPadding(20,20,20,20);
                txt_ingradient.setPadding(20,20,20,20);
                txt_recipemethod.setPadding(20,20,20,20);

            }
        });

        txt_recipemethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main_recipe.setDisplayedChild(2);
                txt_nutrition.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner));
                txt_ingradient.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner));
                txt_recipemethod.setBackground(getResources().getDrawable(R.drawable.bg_rounded_corner_green));
                txt_nutrition.setTextColor(getResources().getColor(R.color.title_gray));
                txt_ingradient.setTextColor(getResources().getColor(R.color.title_gray));
                txt_recipemethod.setTextColor(getResources().getColor(R.color.white));
                txt_nutrition.setPadding(20,20,20,20);
                txt_ingradient.setPadding(20,20,20,20);
                txt_recipemethod.setPadding(20,20,20,20);


            }
        });



        youTubePlayerView = findViewById(R.id.youtube_player_view);

        context = FoodRecipeActivity.this;
        ll_recipe_header=findViewById(R.id.ll_recipe_header);
        txt_protin_value=findViewById(R.id.txt_protin_value);
        txt_fibre_value=findViewById(R.id.txt_fibre_value);
        txt_fat_value=findViewById(R.id.txt_fat_value);
        txt_carb_value=findViewById(R.id.txt_carb_value);
        ll_video=findViewById(R.id.ll_video);
        init();
        setToolBar();
        findViews();

        getAllRecipeMaster();
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        ImageView ivShare=findViewById(R.id.ivShare);
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    txt_nutrition.performClick();
                    toolbar.setVisibility(View.GONE);
                    screenShot();
//                    Bitmap bitmap = takeScreenshot();
//                    saveBitmap(bitmap);
//                    shareImage(bitmap);
//                    shareIt();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
    private void screenShot() {
        LinearLayout llrecipescreenshot=findViewById(R.id.llrecipescreenshot);
        LinearLayout ll_nutritionheader=findViewById(R.id.ll_nutritionheader);
        llrecipescreenshot.setVisibility(View.VISIBLE);
        ll_nutritionheader.setVisibility(View.GONE);
//        txt_recipemethod.setVisibility(View.INVISIBLE);
//        txt_ingradient.setVisibility(View.INVISIBLE);

        NestedScrollView scroll=findViewById(R.id.scroll);



        View v1 = scroll.getRootView();
        v1.setDrawingCacheEnabled(true);

        Bitmap bm=Bitmap.createBitmap(scroll.getChildAt(0).getWidth(),scroll.getChildAt(0).getHeight(),Bitmap.Config.ARGB_4444);
        v1.setDrawingCacheEnabled(false);

        Canvas canvas=new Canvas(bm);
//        canvas.scale(5.0f, 5.0f);
        scroll.getChildAt(0).draw(canvas);




//        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bm, "title", null);
//        Uri bitmapUri = Uri.parse(bitmapPath);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bm,"IMG_" + Calendar.getInstance().getTime(),null);
        Uri bitmapUri = Uri.parse(path);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(String.valueOf(bitmapUri)));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT, "To enjoy more of such healthy recipes and many more features,please visit our website 'reework.in'. ");

        intent.setType("image/png");
        startActivity(intent);
        llrecipescreenshot.setVisibility(View.GONE);
        txt_recipemethod.setVisibility(View.VISIBLE);
        txt_ingradient.setVisibility(View.VISIBLE);
        ll_nutritionheader.setVisibility(View.VISIBLE);




    }

    public void shareImage(Bitmap bitmap) {
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        } else {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }

        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        ContentValues newImageDetails = new ContentValues();
        newImageDetails.put(MediaStore.Images.Media.DISPLAY_NAME, "filename");
        Uri imageContentUri = contentResolver.insert(contentUri, newImageDetails);

        try (ParcelFileDescriptor fileDescriptor =
                     contentResolver.openFileDescriptor(imageContentUri, "w", null)) {
            FileDescriptor fd = fileDescriptor.getFileDescriptor();
            OutputStream outputStream = new FileOutputStream(fd);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_STREAM, imageContentUri);
//        sendIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", imageContentUri));
            sendIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(FoodRecipeActivity.this, BuildConfig.APPLICATION_ID + ".provider",new File(imageContentUri.getPath())));

            sendIntent.putExtra(Intent.EXTRA_TEXT, "This app give me complete information of how to create recipe.You can also download Reework App from playstore and enjoy all services.");
            sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            sendIntent.setType("image/*");
            Intent shareIntent = Intent.createChooser(sendIntent, "Share with");
            startActivity(shareIntent);
        } catch (IOException e) {
        }


    }
    private void shareIt() {
        try{
            Uri uri = Uri.fromFile(imagePath);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("image/*");
            String shareBody = "This app give me complete information of how to create recipe.You can also download Reework App from playstore and enjoy all services.";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Recipe");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(FoodRecipeActivity.this, BuildConfig.APPLICATION_ID + ".provider",imagePath));
            toolbar.setVisibility(View.VISIBLE);

            startActivity(Intent.createChooser(sharingIntent, "Share via"));




        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public Bitmap takeScreenshot() {
        NestedScrollView scroll=findViewById(R.id.scroll);
        Bitmap bitmap=Bitmap.createBitmap(scroll.getChildAt(0).getWidth(),scroll.getChildAt(0).getHeight(),Bitmap.Config.ARGB_4444);
        Canvas canvas=new Canvas(bitmap);
        scroll.getChildAt(0).draw(canvas);
        scroll.setDrawingCacheEnabled(true);
        return bitmap;
    }

    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public void showYoutube(final String videoIDStr){
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                if (videoIDStr.isEmpty()){




                }else {
//                    String videoId = "J-EEotdw5YU";
                    youTubePlayers=youTubePlayer;
                    youTubePlayer.loadVideo(videoIDStr, 0);
                    youTubePlayer.pause();

                }


            }

            @Override
            public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float second) {
                super.onCurrentSecond(youTubePlayer, second);

                if (second>1){
                    youTubePlayerView.getPlayerUiController().showFullscreenButton(true);


                }
            }
        });

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onYouTubePlayerEnterFullScreen() {

                ll_recipe_header.setVisibility(View.GONE);
                youTubePlayers.play();

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


            }

            @SuppressLint("SourceLockedOrientationActivity")
            @Override
            public void onYouTubePlayerExitFullScreen() {
                ll_recipe_header.setVisibility(View.VISIBLE);

                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }


        });
    }

    private void init()
    {
        foodService = Client.getClient().create(FoodService.class);
        sessionManager = new SessionManager(context);
        utils = new Utils();
        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);


        try
        {
            recipeId = getIntent().getExtras().getInt("RECEIPE_ID");
            editId = getIntent().getExtras().getInt("EDIT_ID");
        }
        catch (Exception e){e.printStackTrace();}

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    private void setToolBar()
    {
        toolbar=findViewById(R.id.toolbar);
        ImageView imgLeft = findViewById(R.id.imgLeft);
        setSupportActionBar(toolbar);
        imgLeft.setImageResource(R.drawable.back_arrow);
        imgLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }


    public String extractYTId(String url) {


        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";
        final String[] videoIdRegex = { "\\?vi?=([^&]*)","watch\\?.*v=([^&]*)", "(?:embed|vi?)/([^/?]*)", "^([A-Za-z0-9\\-]*)"};

        String youTubeLinkWithoutProtocolAndDomain = youTubeLinkWithoutProtocolAndDomain(url);
        for(String regex : videoIdRegex) {
            Pattern compiledPattern = Pattern.compile(regex);
            Matcher matcher = compiledPattern.matcher(youTubeLinkWithoutProtocolAndDomain);
            if(matcher.find()){
                return matcher.group(1);
            }
        }
        return null;

    }
    public String youTubeLinkWithoutProtocolAndDomain(String url) {
        final String youTubeUrlRegEx = "^(https?)?(://)?(www.)?(m.)?((youtube.com)|(youtu.be))/";

        Pattern compiledPattern = Pattern.compile(youTubeUrlRegEx);
        Matcher matcher = compiledPattern.matcher(url);

        if(matcher.find()){
            return url.replace(matcher.group(), "");
        }
        return url;
    }
    private void callToUpdateFavoriteStatus()
    {
        utils.showProgressbar(FoodRecipeActivity.this);


        FoodTripFavoriteUpdateRequest request = new FoodTripFavoriteUpdateRequest();
        request.setUserId(userId);


        if(isFavorite){
            request.setIsfavourite(0);
        }else{
            request.setIsfavourite(1);
        }

        request.setEditId(editId);
        request.setRecipeId(recipeId);
        Gson gson = new Gson();
        String json = gson.toJson(request);
        String test = json;

        Call<FoodTripFavoriteUpdateResponse> call = foodService.setIsFavoriteStatus(request);
        call.enqueue(new Callback<FoodTripFavoriteUpdateResponse>()
        {
            @Override
            public void onResponse(Call<FoodTripFavoriteUpdateResponse> call, Response<FoodTripFavoriteUpdateResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodTripFavoriteUpdateResponse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {

                        if (listResponse.getMessage().equalsIgnoreCase("Successfully Updated as a Favorite Recipe")){
                            ivfavorite.setSelected(true);
                            isFavorite=true;

                        }else  if (listResponse.getMessage().equalsIgnoreCase("Successfully added as a Favorite Recipe")){
                            ivfavorite.setSelected(true);
                            isFavorite=true;

                        }

                        else {
                            isFavorite=false;
                            ivfavorite.setSelected(false);

                        }









                    }
                    else
                        Toast.makeText(FoodRecipeActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodTripFavoriteUpdateResponse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }
    private void findViews()
    {
        ivFood = findViewById(R.id.ivFood);
        txt_calories_reciepe = findViewById(R.id.txt_calories_reciepe);
        tvservingqurantity = findViewById(R.id.tvservingqurantity);
        tvRecipeName = findViewById(R.id.tvItemName);
        tvRecipeName.setSelected(true);

        txt_ingradcount_preparationtime = findViewById(R.id.txt_ingradcount_preparationtime);
        tvCalorie = findViewById(R.id.tvCalorie);
        ivfavorite = findViewById(R.id.ivFavrorite);
        ivEdit = findViewById(R.id.ivEdit);
        tvPrepTime = findViewById(R.id.tvPrepTime);
        tvPrepTime = findViewById(R.id.tvPrepTime);
        tvCookTime = findViewById(R.id.tvCookTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        tvIngridents = findViewById(R.id.tvIngridents);
        tvHowtomake = findViewById(R.id.tvHowtomake);
        rvIngridents = findViewById(R.id.rvIngridents);
        rvIngridents_new = findViewById(R.id.rvIngridents_new);
        rvHowtocook = findViewById(R.id.rvHowtocook);
        tvunit = findViewById(R.id.tvunit);
        etDesc = findViewById(R.id.etDesc);
        etIngri = findViewById(R.id.etIngri);
        // ivfavorite.setVisibility(View.GONE);

        ivfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToUpdateFavoriteStatus();


            }
        });

        ivEdit.setOnClickListener(this);

        mRecipeList = new ArrayList<>();

        colorBlue = ContextCompat.getColor(context, R.color.colorRobinEggBlue);
        colorBlack = ContextCompat.getColor(context, R.color.colorPremiumBlack);


        if (Utils.isNetworkAvailable(context))
        {
            if (!((Activity) context).isFinishing())
            {
                utils.showProgressbar(context);
            }

            if (getIntent().getBooleanExtra("FROMPLAN",false)){

                if (getIntent().getSerializableExtra("PLAN_RECEIPE")!=null){
                    utils.hideProgressbar();
                    Intent intent=getIntent();
                    ArrayList<RecipeResponse.Recipee> recipeeArrayList= (ArrayList<RecipeResponse.Recipee>) intent.getSerializableExtra("PLAN_RECEIPE");

                    setData(recipeeArrayList) ;
                }

//                setData(getIntent());


            }else {
                callToGetRecipe();

            }
        }
        else
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                    , Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ivEdit:

                if (mRecipeList != null)
                {

                    callRecipeEditApi();




//                    FragmentManager fm = getSupportFragmentManager();
//                    editDialog = new RecipeEditDialouge();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("LIST", mRecipeList);
//                    editDialog.setArguments(bundle);
//                    editDialog.show(fm, "editPlan");
                }


                break;
        }
    }

    private void callRecipeEditApi() {

        utils.showProgressbar(FoodRecipeActivity.this);




        Call<ClsEditRecipeMain> call = foodService.GetEditRecipe(recipeId);
        call.enqueue(new Callback<ClsEditRecipeMain>()
        {
            @Override
            public void onResponse(Call<ClsEditRecipeMain> call, Response<ClsEditRecipeMain> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsEditRecipeMain listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {


                        if (listResponse.getData().getIsMadeByChef().equalsIgnoreCase("true")){
                            Toast.makeText(FoodRecipeActivity.this, "You are not allow to edit this recipe.", Toast.LENGTH_SHORT).show();

                        }else {

                            FragmentManager fm = getSupportFragmentManager();
                            editDialog1 = new NewRecipeEditDialouge();

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("LIST", listResponse.getData());
                            bundle.putSerializable("allRecipeData",allRecipeData);
                            editDialog1.setArguments(bundle);
                            editDialog1.show(fm, "editPlan");


                        }








                    }
                    else
                        Toast.makeText(FoodRecipeActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClsEditRecipeMain> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });

    }

    @Override
    public void onEditRecipe(boolean res)
    {
        if (res)
        {
            if (Utils.isNetworkAvailable(context)) {
                //   callToGetRecipe();
           /*     Intent intent = new Intent();
                intent.putExtra("result",res);
                setResult(MasterFoodFragmentFoodTrip.SECOND_ACTIVITY_REQUEST_CODE, intent);*/
            /*    Intent intent = new Intent(context, MasterDetailsActivity.class);
                intent.putExtra("FRAGMENT_POSITION", 0);
                intent.putExtra("MEAL_CAL_MAX","");
                intent.putExtra("COMING_FROM","FoodRecipeActivity");
                startActivity(intent);*/
                finish();

            } else
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.internet_connection_unavailable)
                        , Snackbar.LENGTH_SHORT).show();
        }
    }

    private void callToGetRecipe()
    {



        GetRecipeRequest request = new GetRecipeRequest();
        request.setEditId(editId);
        request.setRecipeId(recipeId);

        Call<RecipeResponse> call = foodService.GetRecipe(request);
        call.enqueue(new Callback<RecipeResponse>()
        {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    RecipeResponse recipeResponse = response.body();

                    if (recipeResponse != null && recipeResponse.getCode() == 1)
                    {
                        ArrayList<RecipeResponse.Recipee> tempList = recipeResponse.getData();

                        if (tempList != null && tempList.size() > 0)
                        {
                            try {
                                setData(tempList);

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    else
                    {
                        Snackbar.make(findViewById(android.R.id.content), recipeResponse.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t)
            {
                Log.d("error",t.getMessage());
                utils.hideProgressbar();
            }
        });
    }

    public void setData(final ArrayList<RecipeResponse.Recipee> list) {
        if (list != null) {

//            Toast.makeText(context, "sunit mst "+list.get(0).getRecipeImagePath(), Toast.LENGTH_SHORT).show();


            if (list.get(0).getProtein()!=null){

                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                double showschedulestr = Double.parseDouble(list.get(0).getProtein());
                String protin = decimalFormat.format(showschedulestr);

                txt_protin_value.setText(protin);

            }
            if (list.get(0).getCarbs()!=null){

                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                double showschedulestr = Double.parseDouble(list.get(0).getCarbs());
                String protin = decimalFormat.format(showschedulestr);

                txt_carb_value.setText(protin);

//                txt_carb_value.setText(list.get(0).getCarbs());

            }
            if (list.get(0).getFibre()!=null){

                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                double showschedulestr = Double.parseDouble(list.get(0).getFibre());
                String protin = decimalFormat.format(showschedulestr);

                txt_fibre_value.setText(protin);
//                txt_fibre_value.setText(list.get(0).getFibre());

            }
            if (list.get(0).getFat()!=null){

                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                double showschedulestr = Double.parseDouble(list.get(0).getFat());
                String protin = decimalFormat.format(showschedulestr);

//                txt_fibre_value.setText(protin);
                txt_fat_value.setText(protin);

            }




            ivplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String idss=extractYTId(list.get(0).getVideoLink());

                        Intent intent=new Intent(context, ExoActivity.class);
                        intent.putExtra("VideoID",idss);
                        intent.putExtra("youtubelist_Name",list.get(0).getRecipeName());
                        intent.putExtra("title",list.get(0).getRecipeName());
                        intent.putExtra("description","");
                        context.startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            if (list.get(0).getVideoLink()!=null){
                if (!list.get(0).getVideoLink().isEmpty()){
                    ivplay.setVisibility(View.VISIBLE);

                    ll_video.setVisibility(View.VISIBLE);
                    youTubePlayerView.setVisibility(View.VISIBLE);


//                    if (idss!=null&&!idss.isEmpty()){
////                        showYoutube(idss);
//
//
//
//                    }

                }else {
                    ivplay.setVisibility(View.GONE);

                }
            }else {
                ivplay.setVisibility(View.GONE);
            }


            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.defaultmedicine)
                    .error(R.drawable.defaultmedicine)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.HIGH);


            if (getIntent().getStringExtra("RECEIPE_Image")!=null){
                if (!getIntent().getStringExtra("RECEIPE_Image").isEmpty()){
                    if (context!=null){
                        Glide.with(context)
                                .load(getIntent().getStringExtra("RECEIPE_Image"))
                                .apply(options)
                                .into(ivFood);
                    }

                }else {
                    ivFood.setImageDrawable(getResources().getDrawable(R.drawable.deafault_recipe));

                }
            }else {
                ivFood.setImageDrawable(getResources().getDrawable(R.drawable.deafault_recipe));
            }

            if (getIntent().getBooleanExtra("FROMPLAN",false)){
                if(list.get(0).getRecipeImagePath()!=null&&!list.get(0).getRecipeImagePath().isEmpty()){
                    Log.d("APPpath",list.get(0).getRecipeImagePath());
                    if (context!=null){
                        Glide.with(context)
                                .load(list.get(0).getRecipeImagePath())
                                .apply(options)
                                .into(ivFood);
                    }

                }
            }




            if (list.get(0).getServingQuantity()!=null){
                try {
                    if (list.get(0).getUnit()!=null){
                        String unit=list.get(0).getUnit().toString();

                        if (!unit.isEmpty()){
                            tvservingqurantity.setText( Math.round(Float.parseFloat(String.valueOf(list.get(0).getServingQuantity())))+"(" + list.get(0).getUnit() + ")");

                        }else {
                            tvservingqurantity.setText( ""+Math.round(Float.parseFloat(String.valueOf(list.get(0).getServingQuantity()))));

                        }



                    }else {
                        tvservingqurantity.setText(""+Math.round(Float.parseFloat(String.valueOf( list.get(0).getServingQuantity()))));


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (list.get(0).getUnit()!=null){
                tvunit.setText("Unit :"+list.get(0).getUnit());
            }

            txt_calories_reciepe.setText(""+new DecimalFormat("##.#").format(Double.parseDouble(String.valueOf(list.get(0).getTotalCalories()))));


            String recipeName = "";

            if (list.get(0).getEditId() > 0) {
                recipeName = list.get(0).getModifiedRecipeName();

            } else {
                recipeName = list.get(0).getRecipeName();
            }

            double calories = 0;
            if (list.get(0).getEditId() > 0)
            {
                calories = list.get(0).getCalories();


            }


            isFavorite = list.get(0).getIsfavourite();


            String prepTime ="";
            String cookTime ="";
            String prepareDescription="";
            String modiIngidrient = "";

            //check wheather it is modified recipe or not if greather than 0 then it is modified
            if (list.get(0).getEditId() > 0) {

                prepTime = String.valueOf(list.get(0).getModifiedPrepTime());
                cookTime = String.valueOf(list.get(0).getModifiedCookingTime()); //;
                prepareDescription = list.get(0).getModifiedRecipeMethod();
                modiIngidrient = list.get(0).getModifiedIngredients();
                etIngri.setText(modiIngidrient);
                etIngri.setVisibility(View.VISIBLE);
                rvIngridents.setVisibility(View.GONE);

            }else {
                prepTime = String.valueOf(list.get(0).getPrepTime());
                cookTime = String.valueOf(list.get(0).getCookTime()); //;
                prepareDescription = list.get(0).getRecipeDescription();
                etIngri.setVisibility(View.GONE);
                rvIngridents.setVisibility(View.VISIBLE);
            }


            if (!TextUtils.isEmpty(recipeName))
                tvRecipeName.setText(recipeName);

            if (calories > 0) {
                String strCookTime = String.valueOf(calories) + " " + "Calories";
                int len = String.valueOf(calories).length();
                Spannable wordtoSpan = new SpannableString(strCookTime);
                wordtoSpan.setSpan(new ForegroundColorSpan(colorBlue), 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordtoSpan.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                        0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvCalorie.setText(wordtoSpan);
            }


            if (isFavorite)
                ivfavorite.setSelected(true);
            else
                ivfavorite.setSelected(false);

            if (!TextUtils.isEmpty(prepTime))
                tvPrepTime.setText(prepTime);

            if (!TextUtils.isEmpty(cookTime))
                tvCookTime.setText(cookTime);
            /*Food Calories*/


            /*min only in green rest all in black color*/
            //for cooktime
            String strCookTime = String.valueOf(cookTime) + " " + "min";
            int len = cookTime.length();
            Spannable wordtoSpan = new SpannableString(strCookTime);
            wordtoSpan.setSpan(new ForegroundColorSpan(colorBlue), 0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                    0, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvCookTime.setText(wordtoSpan);

            //for prepare time
            String strPreTime = String.valueOf(prepTime) + " " + "min";
            int len1 = cookTime.length();
            Spannable wordtoSpan1 = new SpannableString(strPreTime);
            wordtoSpan1.setSpan(new ForegroundColorSpan(colorBlue), 0, len1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            wordtoSpan1.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                    0, len1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPrepTime.setText(wordtoSpan1);


            try {
                String startTime = "00:00";
                int minutes = Integer.parseInt(prepTime) + Integer.parseInt(cookTime);
                int h = minutes / 60 + Integer.parseInt(startTime.substring(0, 1));
                int m = minutes % 60 + Integer.parseInt(startTime.substring(3, 4));

                if (h == 0) {
                    String strTotalTime = String.valueOf(m) + " " + "min";
                    int len2 = String.valueOf(m).length();
                    Spannable wordtoSpan2 = new SpannableString(strTotalTime);
                    wordtoSpan2.setSpan(new ForegroundColorSpan(colorBlue), 0, len2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpan2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                            0, len2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvTotalTime.setText(wordtoSpan2);
                } else {
                    //for min green  color n rest in black color
                    String strTotalTime = String.valueOf(h + ":" + m) + " " + "hrs";
                    int len2 = String.valueOf(h + ":" + m).length();
                    Spannable wordtoSpan2 = new SpannableString(strTotalTime);
                    wordtoSpan2.setSpan(new ForegroundColorSpan(colorBlue), 0, len2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    wordtoSpan2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                            0, len2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvTotalTime.setText(wordtoSpan2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }





            if (!TextUtils.isEmpty(prepareDescription)){
                etDesc.setText(prepareDescription);


                String lines[] = prepareDescription.split("\\r?\\n");
                RecyclerView recyler_recipemethod=findViewById(R.id.recyler_recipemethod);

                recyler_recipemethod.setAdapter(new RecipeMethodListAdapter(FoodRecipeActivity.this,lines));
                recyler_recipemethod_new.setAdapter(new RecipeMethodListAdapter(FoodRecipeActivity.this,lines));

            }



            mRecipeList.clear();


            mRecipeList.addAll(list);


            int count=0;
            for (int i = 0; i <mRecipeList.size() ; i++) {

                for (int j = 0; j <mRecipeList.get(i).getRecipeIngrdients().size() ; j++) {

                    count++;

                }

            }





            foodRecipeIngridentAdapter = new FoodRecipeIngridentAdapter(context, mRecipeList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvIngridents.setLayoutManager(layoutManager);
            rvIngridents.setItemAnimator(new DefaultItemAnimator());
            rvIngridents.setAdapter(foodRecipeIngridentAdapter);

          try {
              RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this);

              rvIngridents_new.setLayoutManager(layoutManager1);
              rvIngridents_new.setItemAnimator(new DefaultItemAnimator());
              rvIngridents_new.setAdapter(foodRecipeIngridentAdapter);
          }catch (Exception e){
              e.printStackTrace();
          }
            txt_ingradcount_preparationtime.setText(count+" Ingredients | "+prepTime+" min");

          /*  foodRecipeDescriptionAdapter = new FoodRecipeDescriptionAdapter(context, mRecipeList);
            RecyclerView.LayoutManager  layoutManager1= new LinearLayoutManager(this);
            rvHowtocook.setLayoutManager(layoutManager1);
            rvHowtocook.setItemAnimator(new DefaultItemAnimator());
            rvHowtocook.setAdapter(foodRecipeDescriptionAdapter);*/

        }
    }

    private void getAllRecipeMaster()
    {
        utils.showProgressbar(FoodRecipeActivity.this);
        Call<ClsAllRecipeMainClass> call = foodService.getRecipemaster();
        call.enqueue(new Callback<ClsAllRecipeMainClass>()
        {
            @Override
            public void onResponse(Call<ClsAllRecipeMainClass> call, Response<ClsAllRecipeMainClass> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsAllRecipeMainClass listResponse = response.body();
                    if (listResponse != null && listResponse.getCode() == 1)
                    {
                        allRecipeData=listResponse.getData();

                    }
                    else
                        Toast.makeText(FoodRecipeActivity.this, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClsAllRecipeMainClass> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
            }
        });
    }




}
