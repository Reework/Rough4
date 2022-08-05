package com.shamrock.reework.activity.newrecipe;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.activity.MedicineModule.service.MedicineService;
import com.shamrock.reework.activity.MedicineModule.service.MyMedicine;
import com.shamrock.reework.activity.waterhistory.ClsEditWaterResonse;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class NewRecipeEditDialouge extends DialogFragment implements View.OnClickListener,OnRecipeEditListener
{
    Context context;
    Utils utils;
    SessionManager sessionManager;
    FoodService foodService;

    Button btnSave;
    ImageView ivClose;
    EditText etDesc;
    EditText txtModifiedRecipe,txtModifiedPrepTime,txtModifiedCookingTime,etMethod;
  EditRecipeData recipeeArrayList;
  RecyclerView recler_ingradient;
    EditRecipeIngradientListAdapter editRecipeIngradientListAdapter;
    Button btnSaveEditRecipe;
    File fileuploadimage=new File("");
    private boolean isEditData;
    private MyMedicine myMedicine;
    private int myMedicineID;
    ImageView img_edit_recipe;
    private static final int EXTERNAL_READ_PERMISSION_GRANT = 125;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100,
            FILE_SELECT_REQUEST_CODE = 300;
    private Uri mCapturedImageURI;
    ClsAllRecipeData clsAllRecipeData;
    private int userId;
    private boolean isCamera;
    private Button btn_update_recipe;
    private MedicineService medService;
    TextView txt_serving_unit;
    TextView txt_edit_serving_qty;



    RadioGroup rg_recipe;
    RadioButton rb_veg;
    RadioButton rb_non_veg;
    boolean isVeg=true;


    //
    RadioGroup rg_heathy_recipe;
    RadioButton rb_healthy_no,rb_healthy_yes;
    private boolean isHealthyRecipe=true;
    private String str_cusion="";
    boolean isEditRecipe=false;


    @Override
    public void onEditIngradient(final Ingredients ingredients, final int adapterPosition) {

        final Dialog dialog=new Dialog(context,R.style.CustomDialog);
        dialog.setContentView(R.layout.lay_dailog_edit_ingrad);
        final Spinner spinner_ingradeint=dialog.findViewById(R.id.spinner_ingradeint);
        final Spinner spinner_ingradeint_unit=dialog.findViewById(R.id.spinner_ingradeint_unit);
        btn_update_recipe=dialog.findViewById(R.id.btn_update_recipe);
        final ArrayList<String> arylst_string_ingradeintlist_name=new ArrayList<>();
        final ArrayList<String> arylst_string_ingradeintlist_id=new ArrayList<>();
        for (int i = 0; i <clsAllRecipeData.getIngredientList().size(); i++) {
            arylst_string_ingradeintlist_name.add(clsAllRecipeData.getIngredientList().get(i).getIngredientName());
            arylst_string_ingradeintlist_id.add(clsAllRecipeData.getIngredientList().get(i).getId());
        }
        ArrayAdapter<String> adapter_charge = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_string_ingradeintlist_name);
        spinner_ingradeint.setAdapter(adapter_charge);

        final EditText edt_ingrad_qty=dialog.findViewById(R.id.edt_ingrad_qty);
        edt_ingrad_qty.setText(ingredients.getQuantity());
        for (int i = 0; i <arylst_string_ingradeintlist_name.size() ; i++) {

            if (ingredients.getIngredientName().equalsIgnoreCase(arylst_string_ingradeintlist_name.get(i).toString())){
                spinner_ingradeint.setSelection(i);
                break;
            }


        }

        final ArrayList<String> arylst_ingradunit_name=new ArrayList<>();
        final ArrayList<String> arylst_ingradunit_id=new ArrayList<>();

        for (int i = 0; i <clsAllRecipeData.getIngredientUnitList().size() ; i++) {
            arylst_ingradunit_name.add(clsAllRecipeData.getIngredientUnitList().get(i).getMeasurement());
            arylst_ingradunit_id.add(clsAllRecipeData.getIngredientUnitList().get(i).getId());


        }

        ArrayAdapter<String> adapter_charge_new = new ArrayAdapter<String>(context,R.layout.custom_simple_spinner_item,R.id.txt_when, arylst_ingradunit_name);
        spinner_ingradeint_unit.setAdapter(adapter_charge_new);

        for (int i = 0; i <clsAllRecipeData.getIngredientUnitList().size() ; i++) {

            if (ingredients.getUomId().equalsIgnoreCase(clsAllRecipeData.getIngredientUnitList().get(i).getId())){
                spinner_ingradeint_unit.setSelection(i);
                break;

            }

        }


        btn_update_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recipeeArrayList.getIngredients().get(adapterPosition).setIngredientName(spinner_ingradeint.getSelectedItem().toString());
                recipeeArrayList.getIngredients().get(adapterPosition).setIngredientId(arylst_string_ingradeintlist_id.get(spinner_ingradeint.getSelectedItemPosition()).toString());
                recipeeArrayList.getIngredients().get(adapterPosition).setQuantity(edt_ingrad_qty.getText().toString());


                recipeeArrayList.getIngredients().get(adapterPosition).setUomId(arylst_ingradunit_id.get(spinner_ingradeint_unit.getSelectedItemPosition()).toString());
                recipeeArrayList.getIngredients().get(adapterPosition).setUomName(arylst_ingradunit_name.get(spinner_ingradeint_unit.getSelectedItemPosition()).toString());
                recipeeArrayList.getIngredients().get(adapterPosition).setQuantity(edt_ingrad_qty.getText().toString());



                editRecipeIngradientListAdapter.notifyDataSetChanged();



                dialog.dismiss();
            }
        });


        dialog.show();


    }

    public interface RecipeEditListener
    {
        void onEditRecipe(boolean res);
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;

        try
        {
            sessionManager = new SessionManager(context);
            foodService = Client.getClient().create(FoodService.class);
        }catch (Exception e){e.printStackTrace();}

        utils = new Utils();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        Bundle bundle = getArguments();
        if (bundle != null)
        {
            recipeeArrayList = (EditRecipeData) bundle.getSerializable("LIST");
            clsAllRecipeData = (ClsAllRecipeData) bundle.getSerializable("allRecipeData");
        }

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_fragment_edit_recipe_new, container, false);

        //
        rb_veg=view.findViewById(R.id.rb_veg);
        rb_non_veg=view.findViewById(R.id.rb_non_veg);
        rg_recipe=view.findViewById(R.id.rg_recipe);

        if (recipeeArrayList.getIsVeg().equalsIgnoreCase("true")){
            rb_veg.setChecked(true);

        }else {
            rb_non_veg.setChecked(true);
        }
        rg_recipe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_veg:
                        isVeg = true;
                        break;
                    case R.id.rb_non_veg:
                        isVeg = false;

                        break;

                }
            }
        });


        rb_healthy_no=view.findViewById(R.id.rb_healthy_no);
        rb_healthy_yes=view.findViewById(R.id.rb_healthy_yes);
        rg_heathy_recipe=view.findViewById(R.id.rg_heathy_recipe);
        rb_healthy_yes.setChecked(true);
        if (recipeeArrayList.getIsHealthy().equalsIgnoreCase("true")){
            rb_healthy_yes.setChecked(true);
        }else {
            rb_healthy_no.setChecked(true);
        }

        rg_heathy_recipe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_veg:
                        isHealthyRecipe= true;
                        break;
                    case R.id.rb_non_veg:
                        isHealthyRecipe = false;

                        break;

                }
            }
        });
        //




        txt_edit_serving_qty = view.findViewById(R.id.txt_edit_serving_qty);
        txt_serving_unit = view.findViewById(R.id.txt_serving_unit);
        txt_serving_unit.setText("Serving Quantity : "+recipeeArrayList.getServingQuantitiy()+"("+recipeeArrayList.getServingUnitName()+")");
        img_edit_recipe = view.findViewById(R.id.img_edit_recipe);
        recler_ingradient = view.findViewById(R.id.recler_ingradient);
        btnSaveEditRecipe = view.findViewById(R.id.btnSaveEditRecipe);
        etDesc = view.findViewById(R.id.etDesc);
//        btnSave = view.findViewById(R.id.btnSave);
        ivClose = view.findViewById(R.id.ivClose);
        txtModifiedRecipe = view.findViewById(R.id.txtModifiedRecipe);
        txtModifiedPrepTime = view.findViewById(R.id.txtModifiedPrepTime);
        txtModifiedCookingTime = view.findViewById(R.id.txtModifiedCookingTime);
        etMethod = view.findViewById(R.id.etMethod);

//        btnSave.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        btnSaveEditRecipe.setOnClickListener(this);


        txt_edit_serving_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context,R.style.CustomDialog);
                Button btn_update_serving_qty=dialog.findViewById(R.id.btn_update_serving_qty);
                final EditText edt_serving_qty=dialog.findViewById(R.id.edt_serving_qty);
                edt_serving_qty.setText(recipeeArrayList.getServingQuantitiy());
                btn_update_serving_qty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recipeeArrayList.setServingQuantitiy(edt_serving_qty.getText().toString());
                        txt_serving_unit.setText("Serving Quantity : "+edt_serving_qty.getText().toString()+"("+recipeeArrayList.getServingUnitName()+")");

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });



        if (recipeeArrayList !=  null)
        {

            txtModifiedRecipe.setText(recipeeArrayList.getRecipeName());
            etMethod.setText(recipeeArrayList.getDescription());
            txtModifiedPrepTime.setText(String.valueOf(recipeeArrayList.getPrepTime()));
            txtModifiedCookingTime.setText(String.valueOf(recipeeArrayList.getCookTime()));

            editRecipeIngradientListAdapter=new EditRecipeIngradientListAdapter(NewRecipeEditDialouge.this,recipeeArrayList.getIngredients());
            recler_ingradient.setNestedScrollingEnabled(false);
            recler_ingradient.setAdapter(editRecipeIngradientListAdapter);
        }

        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSave:

                if (Utils.isNetworkAvailable(context))
                {
                    String lsDesc = etDesc.getText().toString();

                    if (!TextUtils.isEmpty(lsDesc))
                    {
//                        callToUpdateRecipe(lsDesc);
                        dismiss();
                    }
                    else
                        Toast.makeText(context, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "No Internet !", Toast.LENGTH_SHORT).show();
                break;

            case R.id.ivClose:
                this.dismiss();
                break;


            case R.id.btnSaveEditRecipe:
                recipeeArrayList.setRecipeName(txtModifiedRecipe.getText().toString());
                recipeeArrayList.setPrepTime(txtModifiedPrepTime.getText().toString());
                recipeeArrayList.setCookTime(txtModifiedCookingTime.getText().toString());
                recipeeArrayList.setDescription(etMethod.getText().toString());
                recipeeArrayList.setIsHealthy(String.valueOf(isHealthyRecipe));
                recipeeArrayList.setIsVeg(String.valueOf(isVeg));

                callEditRecipeApi();
                break;
        }
    }

    private void callEditRecipeApi() {
        utils.showProgressbar(context);

        recipeeArrayList.setCreatedBy(String.valueOf(userId));
        String requestedit=new Gson().toJson(recipeeArrayList);


        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), requestedit);
        MultipartBody.Part photo = null;
        RequestBody photoContent = RequestBody.create(MediaType.parse("multipart/form-data"), "");
        photo = MultipartBody.Part.createFormData("RecipeImage","", photoContent);

        medService = Client.getClientMultiPart().create(MedicineService.class);


        Call<ClsEditWaterResonse> call = medService.saveEditRecipe(body,photo);
        call.enqueue(new Callback<ClsEditWaterResonse>()
        {
            @Override
            public void onResponse(Call<ClsEditWaterResonse> call, Response<ClsEditWaterResonse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    ClsEditWaterResonse listResponse = response.body();
                    if (listResponse != null && listResponse.getCode().equals("1"))
                    {
                        Toast.makeText(context, ""+listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        dismiss();


                    }
                    else
                        Toast.makeText(context, listResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ClsEditWaterResonse> call, Throwable t)
            {
                // Log error here since request failed
                Log.e("MasterFood---->", t.toString());
                utils.hideProgressbar();
                dismiss();

            }
        });

    }

//    private void callToUpdateRecipe(String modifiedReciepe)
//    {
//        if (!((Activity) context).isFinishing())
//        {
//            utils.showProgressbar(context);
//        }
//
//        FoodRecipeEditRequest request = new FoodRecipeEditRequest();
//        request.setEditId(recipeeArrayList.get(0).getEditId());
//        request.setUserId(userId);
//        request.setRecipeId(String.valueOf(recipeeArrayList.get(0).getRecipeId()));
//        request.setModifiedRecipeName(txtModifiedRecipe.getText().toString());
//        request.setModifiedRecipeMethod(etMethod.getText().toString());
//        request.setModifiedIngredients(etDesc.getText().toString());
//        if(txtModifiedPrepTime.getText().toString()!=null && !txtModifiedPrepTime.getText().toString().equalsIgnoreCase("")) {
//            request.setModifiedPrepTime(Integer.parseInt(txtModifiedPrepTime.getText().toString()));
//        }
//
//        if(txtModifiedCookingTime.getText().toString()!=null && !txtModifiedCookingTime.getText().toString().equalsIgnoreCase("")) {
//            request.setModifiedCookingTime(Integer.parseInt(txtModifiedCookingTime.getText().toString()));
//        }
//        request.setIsfavourite(recipeeArrayList.get(0).getIsfavourite());
//
//        Gson gson = new Gson();
//        String json = gson.toJson(request);
//        String test = json;
//
//        Call<FoodRecipeEditResponse> call = foodService.editRecipe(request);
//        call.enqueue(new Callback<FoodRecipeEditResponse>()
//        {
//            @Override
//            public void onResponse(Call<FoodRecipeEditResponse> call, Response<FoodRecipeEditResponse> response)
//            {
//                utils.hideProgressbar();
//
//                if (response.code() == Client.RESPONSE_CODE_OK)
//                {
//                    FoodRecipeEditResponse editResponse = response.body();
//
//                    if (editResponse != null && editResponse.getCode() == 1)
//                    {
//                        Toast.makeText(context, editResponse.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        RecipeEditListener listener = (RecipeEditListener) context;
//                        listener.onEditRecipe(true);
//                        dismiss();
//                    }
//                    else
//                        Toast.makeText(context, editResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FoodRecipeEditResponse> call, Throwable t)
//            {
//                utils.hideProgressbar();
//            }
//        });
//    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_SELECT_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {

                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = context.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                img_edit_recipe.setImageBitmap(null);
                img_edit_recipe.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                fileuploadimage=new File(picturePath);
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile (fileuploadimage.getPath ());
                    bitmap.compress (Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                }
                catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString ());
                    t.printStackTrace ();
                }
//                uploadFile(new File(picturePath), userID);

//                isImage = true;
            }
        }
        else
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                fileuploadimage=new File(getRealPathFromURI(mCapturedImageURI));
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile (fileuploadimage.getPath ());
                    bitmap.compress (Bitmap.CompressFormat.JPEG, 40, new FileOutputStream(fileuploadimage));
                }
                catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file." + t.toString ());
                    t.printStackTrace ();
                }

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mCapturedImageURI);
                    img_edit_recipe.setImageBitmap(null);

                    img_edit_recipe.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
//                uploadFile(new File(getRealPathFromURI(mCapturedImageURI)), userID);
//                isImage = true;
            }
        }
    }
    public String getRealPathFromURI(Uri contentUri)
    {
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        catch (Exception e) {return contentUri.getPath();}
    }

    private void AddMedicineImageDailog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        builder.setMessage("Upload Medicine Image ")
                .setCancelable(false)
                .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        isCamera = true;
                        dialog.dismiss();

                        Dexter.withActivity(getActivity())
                                .withPermission(Manifest.permission.CAMERA)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        CallCameraIntent();
                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                        // check for permanent denial of permission
                                        if (response.isPermanentlyDenied()) {
                                            // navigate user to app settings
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();





                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        dialog.cancel();

                        Dexter.withActivity(getActivity())
                                .withPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .withListener(new PermissionListener() {
                                    @Override
                                    public void onPermissionGranted(PermissionGrantedResponse response) {
                                        // permission is granted, open the camera

                                        dialog.dismiss();
                                        fileChooser();                                    }

                                    @Override
                                    public void onPermissionDenied(PermissionDeniedResponse response) {
                                        // check for permanent denial of permission
                                        if (response.isPermanentlyDenied()) {
                                            // navigate user to app settings
                                            showSettingsDialog();
                                        }
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();


                        if (!checkIfAlreadyHavePermission())
                        {
                            isCamera = false;
                            requestForSpecificPermission();

                        }
                        else
                        {

                        }
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCancelable(true);
        //Setting the title manually
        alert.show();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void CallCameraIntent()
    {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "camera_image.png");
        mCapturedImageURI = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        startActivityForResult(intentPicture, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void fileChooser()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_REQUEST_CODE);
    }

    private boolean checkIfAlreadyHavePermission()
    {
        int res = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return res == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission()
    {
        ActivityCompat.requestPermissions(getActivity(),
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_READ_PERMISSION_GRANT);
    }
}
