package com.shamrock.reework.activity.FoodModule.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.activity.AddMealActivity;
import com.shamrock.reework.activity.FoodModule.adapter.AddMealIntoListAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.ClsAddFoodQuantityAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.RepeatMealIntoListAdapter;
import com.shamrock.reework.activity.FoodModule.model.FoodUnitMasterData;
import com.shamrock.reework.activity.FoodModule.service.OnGetFoodDailogData;
import com.shamrock.reework.activity.FoodModule.service.onSubmitRepeatMeal;
import com.shamrock.reework.activity.waterhistory.DigitsInputFilter;
import com.shamrock.reework.adapter.DifferentRowAdapter;
import com.shamrock.reework.api.response.FoodListByMealType;
import com.yanzhenjie.wheel.OnWheelChangedListener;
import com.yanzhenjie.wheel.OnWheelClickedListener;
import com.yanzhenjie.wheel.WheelView;
import com.yanzhenjie.wheel.adapters.AbstractWheelAdapter;
import com.yanzhenjie.wheel.adapters.AbstractWheelTextAdapter;
import com.yanzhenjie.wheel.adapters.NumericWheelAdapter;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class AddFoodDialogFragmet extends BottomSheetDialogFragment  {

    private BottomSheetBehavior mBehavior;
    private Context mCtx;
    private boolean isDataLoading = false;
    private BottomSheetDialog bDialog;
    private int pageNumber = 1;
    ImageView img_add_recipe;
    TextView txt_gram_header;

    RecyclerView recyler_quantity;
    ArrayList<FoodUnitMasterData> bundleList;
    public String strUOmID_new="";
    private double str_new_quanity=0.25;
    OnGetFoodDailogData onGetFoodDailogData_listenr;
    TextView btn_data_to_meal;
    double newValueInGram;
    String meal_name="";
    TextView txt_recipe;
    String meal_type="";
    String imgpath;

    TextView txt_carb_value;
    TextView txt_fat_value;
    TextView txt_fibre_value;
    TextView txt_protin_value;
    FoodListByMealType.Datum model;
    EditText edt_food_quantity;
    TextView tvTitle;
    ImageView imgLeft;
    TextView tvItemName;




    public AddFoodDialogFragmet(AddMealActivity addMealActivity) {
//        onSubmitRepeatMeal_listner= (onSubmitRepeatMeal) addMealActivity;
        onGetFoodDailogData_listenr=addMealActivity;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bottom sheet round corners can be obtained but the while background appears to remove that we need to add this.


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //bottom sheet round corners can be obtained but the while background appears to remove that we need to add this.

        bDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        final View view = View.inflate(getContext(), R.layout.fragment_bottom_shit_dialog_add_meal, null);

        mCtx = view.getContext();

        imgLeft=view.findViewById(R.id.imgLeft);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvTitle=view.findViewById(R.id.tvTitle);
        tvTitle.setText("Add Food");
        txt_gram_header=view.findViewById(R.id.txt_gram_header);
        recyler_quantity=view.findViewById(R.id.recyler_quantity);
        img_add_recipe=view.findViewById(R.id.img_add_recipe);
        btn_data_to_meal=view.findViewById(R.id.btn_data_to_meal);
        edt_food_quantity=view.findViewById(R.id.edt_food_quantity);
        edt_food_quantity.setFilters(new InputFilter[]{new DigitsInputFilter(4, 2, 3000)});

        edt_food_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence!=null&&charSequence.length()>0){

                    double perProtinvalue=Double.parseDouble(model.getProtin())/100;
                    double totalgramProtine=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
                    double finalprotinevalue=perProtinvalue*totalgramProtine;
                    DecimalFormat decimalFormat = new DecimalFormat("0.00");
                    String protin = decimalFormat.format(finalprotinevalue);
                    txt_protin_value.setText(String.valueOf(protin));

                    double perfibrevalue=Double.parseDouble(model.getFibre())/100;
                    double totalgramFibre=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
                    double finalfibrevalue=perfibrevalue*totalgramFibre;
                    String fibre = decimalFormat.format(finalfibrevalue);
                    txt_fibre_value.setText(String.valueOf(fibre));

                    double perfatvalue=Double.parseDouble(model.getFat())/100;
                    double totalgramFat=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
                    double finalfatvalue=perfatvalue*totalgramFat;
                    String fat = decimalFormat.format(finalfatvalue);
                    txt_fat_value.setText(String.valueOf(fat));

                    double perCarbvalue=Double.parseDouble(model.getFat())/100;
                    double totalgramCarb=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
                    double finalCarbvalue=perCarbvalue*totalgramCarb;
                    String Carb = decimalFormat.format(finalCarbvalue);
                    txt_carb_value.setText(String.valueOf(Carb));

                    DecimalFormat decimalFormat2 = new DecimalFormat("0.0");
                    double totalgramProtine2=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
                    String gram = decimalFormat2.format(totalgramProtine2);


                    txt_gram_header.setText("Nutrition Information per "+gram+" gm");


                }else {
                    txt_protin_value.setText("0.0");
                    txt_fibre_value.setText("0.0");
                    txt_fat_value.setText("0.0");
                    txt_carb_value.setText("0.0");



                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        txt_protin_value=view.findViewById(R.id.txt_protin_value);
        txt_fibre_value=view.findViewById(R.id.txt_fibre_value);
        txt_fat_value=view.findViewById(R.id.txt_fat_value);
        txt_carb_value=view.findViewById(R.id.txt_carb_value);

        ImageView img_close_food_dailog=view.findViewById(R.id.img_close_food_dailog);
        img_close_food_dailog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_data_to_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_food_quantity.getText().toString().isEmpty()){
                    Toast.makeText(mCtx, "Please enter the quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                dismiss();

                onGetFoodDailogData_listenr.getaddMealData(strUOmID_new,newValueInGram, Double.parseDouble(edt_food_quantity.getText().toString()));
            }
        });
        Bundle bundle=getArguments();
        if (bundle!=null){
            try {
                imgpath = bundle.getString("imagepath");
                meal_name = bundle.getString("meal_name");
                meal_type = bundle.getString("meal_type");
                String text="Add to " + meal_type;
//                btn_data_to_meal.setText(text.toUpperCase());
                tvItemName = view.findViewById(R.id.tvItemName);
                TextView txt_cal=view.findViewById(R.id.txt_cal);
                tvItemName.setText(meal_name);
                bundleList = (ArrayList<FoodUnitMasterData>) getArguments().getSerializable("COUNTRY_LIST");

                if (getArguments().getSerializable("nutrionData")!=null){


                    model= (FoodListByMealType.Datum) getArguments().getSerializable("nutrionData");
                    txt_cal.setText(model.getCalories()+" kcal");


                    txt_protin_value.setText(model.getProtin());
                    txt_carb_value.setText(model.getCarb());
                    txt_fibre_value.setText(model.getFibre());
                    txt_fat_value.setText(model.getFat());

                }

                if (bundleList != null) {
                    strUOmID_new = bundleList.get(0).getId();
                    newValueInGram=bundleList.get(0).getValueInGram();


                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        try {


            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.deafault_recipe)
                    .error(R.drawable.deafault_recipe)
                    .diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                    .priority(Priority.HIGH);
            if (imgpath!=null){
                Glide.with(mCtx)
                        .load(imgpath)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                        Target<Drawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                           DataSource dataSource, boolean isFirstResource) {
                                return false;
                            }
                        })
                        .apply(options)
                        .into(img_add_recipe);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        setQuantityAdapter(view);
        setUOMAdapter(view,bundleList);



        bDialog.setContentView(view);
//        bDialog.setCancelable(false);
        try {
            Field behaviorField = bDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bDialog);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING){
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        setupFullHeight(bDialog);
//        mBehavior = BottomSheetBehavior.from((View) view.getParent());
////        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
//
//        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
//                    dismiss();
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });

        /*
         * Call TimeLineArticle API
         */





        return bDialog;
    }
    public class TestAdapter extends AbstractWheelAdapter {
        ArrayList<FoodUnitMasterData> str_qnty;

        public TestAdapter(ArrayList<FoodUnitMasterData> str_qnty) {
            this.str_qnty=str_qnty;
        }

        @Override
        public int getItemsCount() {
            return str_qnty.size();
        }

        @Override
        public View getItem(int index, View convertView, ViewGroup parent) {
            View view=LayoutInflater.from(getContext()).inflate(R.layout.row_test,parent,false);
            TextView txt_unit_name=view.findViewById(R.id.txt_unit_name);
            txt_unit_name.setText(str_qnty.get(index).getMeasurement().toString());
            ImageView img_uom=view.findViewById(R.id.img_uom);

            Glide.with(getContext())
                    .load(str_qnty.get(index).getImagePath())
                    .apply(
                            RequestOptions.circleCropTransform()
                                    .placeholder(R.drawable.defaultmedicine)
                                    .error(R.drawable.defaultmedicine)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    )
                    .into(img_uom);


            return view;
        }
    }

    private void setUOMAdapter(View view, final ArrayList<FoodUnitMasterData> bundleList) {
        final ArrayList<String> str_qnty=new ArrayList<>();

        for (int i = 0; i <bundleList.size() ; i++) {
            str_qnty.add(bundleList.get(i).getMeasurement());
        }
        WheelView wheal_list_uom=view.findViewById(R.id.wheal_list_uom);
        wheal_list_uom.setAdapter(new TestAdapter(bundleList));
        Drawable centerFilter = mCtx.getResources().getDrawable(R.drawable.quantity_selector);
        wheal_list_uom.setCenterFilter(centerFilter);
        wheal_list_uom.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                for (int i = 0; i <bundleList.size() ; i++) {

                    if (bundleList.get(newValue).getMeasurement().toString().equalsIgnoreCase(bundleList.get(i).getMeasurement())){
                        strUOmID_new=bundleList.get(i).getId();
                        newValueInGram=bundleList.get(i).getValueInGram();
                        updateData();
                        break;
                    }
                }

            }
        });

    }

    private void updateData() {

        if (edt_food_quantity!=null&&edt_food_quantity.length()>0){
            String charSequence=edt_food_quantity.getText().toString();

            double perProtinvalue=Double.parseDouble(model.getProtin())/100;
            double totalgramProtine=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
            double finalprotinevalue=perProtinvalue*totalgramProtine;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String protin = decimalFormat.format(finalprotinevalue);
            txt_protin_value.setText(String.valueOf(protin));

            double perfibrevalue=Double.parseDouble(model.getFibre())/100;
            double totalgramFibre=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
            double finalfibrevalue=perfibrevalue*totalgramFibre;
            String fibre = decimalFormat.format(finalfibrevalue);
            txt_fibre_value.setText(String.valueOf(fibre));

            double perfatvalue=Double.parseDouble(model.getFat())/100;
            double totalgramFat=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
            double finalfatvalue=perfatvalue*totalgramFat;
            String fat = decimalFormat.format(finalfatvalue);
            txt_fat_value.setText(String.valueOf(fat));

            double perCarbvalue=Double.parseDouble(model.getFat())/100;
            double totalgramCarb=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
            double finalCarbvalue=perCarbvalue*totalgramCarb;
            String Carb = decimalFormat.format(finalCarbvalue);
            txt_carb_value.setText(String.valueOf(Carb));

            DecimalFormat decimalFormat2 = new DecimalFormat("0.0");
            double totalgramProtine2=Double.parseDouble(String.valueOf(charSequence))*Double.parseDouble(String.valueOf(newValueInGram));
            String gram = decimalFormat2.format(totalgramProtine2);


            txt_gram_header.setText("Nutrition Information per "+gram+" gm");


        }else {
            txt_protin_value.setText("0.0");
            txt_fibre_value.setText("0.0");
            txt_fat_value.setText("0.0");
            txt_carb_value.setText("0.0");


        }
    }

    private void setQuantityAdapter(View view) {
        final   double basicvalue=0.25;

        final ArrayList<String> str_qnty=new ArrayList<>();
        for (double i = 1; i < 201; i++) {

            double value=basicvalue*i;

            String strqty= String.valueOf(value);
            int startindex=strqty.length()-2;
            int endindex=strqty.length()-1;
            String[] strNewQty=strqty.split("\\.");
            boolean isFound=false;


            if (strNewQty[1].equals("0")){
                isFound=true;

            }

            if (isFound){
                isFound=false;
                str_qnty.add(strNewQty[0].toString());


            }else {
                str_qnty.add(strqty.toString());

            }

        }
//

        AbstractWheelTextAdapter abstractWheelTextAdapter=new AbstractWheelTextAdapter(mCtx) {
            @Override
            protected CharSequence getItemText(int index) {
                for (int i = 0; i <str_qnty.size() ; i++) {
                    return str_qnty.get(index);

                }
                return "";            }

            @Override
            public int getItemsCount() {
                return str_qnty.size();
            }
        };

        WheelView wheal_list=view.findViewById(R.id.wheal_list);
        abstractWheelTextAdapter.setItemResource(R.layout.layout_item);
        abstractWheelTextAdapter.setItemTextResource(R.id.txt_wheel_quantity);
        wheal_list.setAdapter(abstractWheelTextAdapter);

        int[] colors = {Color.WHITE, Color.TRANSPARENT};

        Drawable centerFilter = mCtx.getResources().getDrawable(R.drawable.quantity_selector );
        wheal_list.setCenterFilter(centerFilter);

        GradientDrawable topShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        wheal_list.setTopShadow(topShadow);

        GradientDrawable bottomShadow = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors);
        wheal_list.setBottomShadow(bottomShadow);


        wheal_list.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                str_new_quanity= Double.parseDouble(str_qnty.get(newValue).toString());



            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,0);
    }
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setHideable(false);
        behavior.isHideable();
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        bottomSheet.setClickable(false);
//        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mCtx).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }



}

