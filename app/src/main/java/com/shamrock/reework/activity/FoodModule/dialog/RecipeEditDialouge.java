package com.shamrock.reework.activity.FoodModule.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shamrock.R;
import com.shamrock.reework.api.Client;
import com.shamrock.reework.activity.FoodModule.service.FoodService;
import com.shamrock.reework.api.request.FoodRecipeEditRequest;
import com.shamrock.reework.api.response.FoodRecipeEditResponse;
import com.shamrock.reework.api.response.RecipeResponse;
import com.shamrock.reework.database.SessionManager;
import com.shamrock.reework.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeEditDialouge extends DialogFragment implements View.OnClickListener
{
    Context context;
    Utils utils;
    SessionManager sessionManager;
    FoodService foodService;

    Button btnSave;
    ImageView ivClose;
    EditText etDesc;
    EditText txtModifiedRecipe,txtModifiedPrepTime,txtModifiedCookingTime,etMethod;
    ArrayList<RecipeResponse.Recipee> recipeeArrayList;

    private int userId;

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
        { recipeeArrayList = (ArrayList<RecipeResponse.Recipee>) bundle.getSerializable("LIST");
        }

        userId = sessionManager.getIntValue(SessionManager.KEY_USER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dialog_fragment_edit_recipe, container, false);

        etDesc = view.findViewById(R.id.etDesc);
        btnSave = view.findViewById(R.id.btnSave);
        ivClose = view.findViewById(R.id.ivClose);
        txtModifiedRecipe = view.findViewById(R.id.txtModifiedRecipe);
        txtModifiedPrepTime = view.findViewById(R.id.txtModifiedPrepTime);
        txtModifiedCookingTime = view.findViewById(R.id.txtModifiedCookingTime);
        etMethod = view.findViewById(R.id.etMethod);

        btnSave.setOnClickListener(this);
        ivClose.setOnClickListener(this);


        if (recipeeArrayList !=  null)
        {

            //check wheather it is modified recipe or not if greather than 0 then it is modified
            if (recipeeArrayList.get(0).getEditId() > 0) {
                txtModifiedRecipe.setText(recipeeArrayList.get(0).getModifiedRecipeName());
                etMethod.setText(recipeeArrayList.get(0).getModifiedRecipeMethod());
                txtModifiedPrepTime.setText(String.valueOf(recipeeArrayList.get(0).getModifiedPrepTime()));
                txtModifiedCookingTime.setText(String.valueOf(recipeeArrayList.get(0).getModifiedCookingTime()));
                etDesc.setText(recipeeArrayList.get(0).getModifiedIngredients());


            }else {
                txtModifiedRecipe.setText(recipeeArrayList.get(0).getRecipeName());
                etMethod.setText(recipeeArrayList.get(0).getRecipeDescription());
                txtModifiedPrepTime.setText(String.valueOf(recipeeArrayList.get(0).getPrepTime()));
                txtModifiedCookingTime.setText(String.valueOf(recipeeArrayList.get(0).getCookTime()));
                StringBuilder descriptionIngredients = new StringBuilder();
                for (int i = 0; i < recipeeArrayList.get(0).getRecipeIngrdients().size(); i++) {
                    if (i == 0) {

                        descriptionIngredients.append(recipeeArrayList.get(0).getRecipeIngrdients().get(i).getIngredients().toString());
                    } else {
                        descriptionIngredients.append(recipeeArrayList.get(0).getRecipeIngrdients().get(i).getIngredients().toString());

                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        descriptionIngredients.append(System.lineSeparator());
                    }
                    // description = recipeeArrayList.get(0).getRecipeIngrdients().get(i).toString();
                }

           /* StringBuilder method = new StringBuilder();
            for(int i= 0 ; i < recipeeArrayList.get(0).getRecipeIngrdients().size(); i++){
                if(i == 0 ){

                    method.append(recipeeArrayList.get(0).getRecipeIngrdients().get(i).get().toString());
                }else{
                    method.append(recipeeArrayList.get(0).getRecipeIngrdients().get(i).getIngredients().toString());

                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    method.append(System.lineSeparator());
                }
                // description = recipeeArrayList.get(0).getRecipeIngrdients().get(i).toString();
            }*/


                if (!TextUtils.isEmpty(descriptionIngredients.toString()))
                    etDesc.setText(descriptionIngredients);


            }
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
                        callToUpdateRecipe(lsDesc);
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
        }
    }

    private void callToUpdateRecipe(String modifiedReciepe)
    {
        if (!((Activity) context).isFinishing())
        {
            utils.showProgressbar(context);
        }

        FoodRecipeEditRequest request = new FoodRecipeEditRequest();
        request.setEditId(recipeeArrayList.get(0).getEditId());
        request.setUserId(userId);
        request.setRecipeId(String.valueOf(recipeeArrayList.get(0).getRecipeId()));
        request.setModifiedRecipeName(txtModifiedRecipe.getText().toString());
        request.setModifiedRecipeMethod(etMethod.getText().toString());
        request.setModifiedIngredients(etDesc.getText().toString());
        if(txtModifiedPrepTime.getText().toString()!=null && !txtModifiedPrepTime.getText().toString().equalsIgnoreCase("")) {
            request.setModifiedPrepTime(Integer.parseInt(txtModifiedPrepTime.getText().toString()));
        }

        if(txtModifiedCookingTime.getText().toString()!=null && !txtModifiedCookingTime.getText().toString().equalsIgnoreCase("")) {
            request.setModifiedCookingTime(Integer.parseInt(txtModifiedCookingTime.getText().toString()));
        }
        request.setIsfavourite(recipeeArrayList.get(0).getIsfavourite());

        Gson gson = new Gson();
        String json = gson.toJson(request);
        String test = json;

        Call<FoodRecipeEditResponse> call = foodService.editRecipe(request);
        call.enqueue(new Callback<FoodRecipeEditResponse>()
        {
            @Override
            public void onResponse(Call<FoodRecipeEditResponse> call, Response<FoodRecipeEditResponse> response)
            {
                utils.hideProgressbar();

                if (response.code() == Client.RESPONSE_CODE_OK)
                {
                    FoodRecipeEditResponse editResponse = response.body();

                    if (editResponse != null && editResponse.getCode() == 1)
                    {
                        Toast.makeText(context, editResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        RecipeEditListener listener = (RecipeEditListener) context;
                        listener.onEditRecipe(true);
                        dismiss();
                    }
                    else
                        Toast.makeText(context, editResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodRecipeEditResponse> call, Throwable t)
            {
                utils.hideProgressbar();
            }
        });
    }
}
