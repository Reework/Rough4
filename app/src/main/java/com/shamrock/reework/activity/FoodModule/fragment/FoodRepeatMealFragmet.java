package com.shamrock.reework.activity.FoodModule.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shamrock.R;
import com.shamrock.reework.activity.FoodModule.OnRepeatMealSelect;
import com.shamrock.reework.activity.FoodModule.activity.AddMealActivity;
import com.shamrock.reework.activity.FoodModule.adapter.AddMealIntoListAdapter;
import com.shamrock.reework.activity.FoodModule.adapter.RepeatMealIntoListAdapter;
import com.shamrock.reework.activity.FoodModule.service.onSubmitRepeatMeal;
import com.shamrock.reework.api.response.FoodListByMealType;

import java.util.ArrayList;
import java.util.List;


public class FoodRepeatMealFragmet extends BottomSheetDialogFragment  {

    private BottomSheetBehavior mBehavior;
    private Context mCtx;
    private boolean isDataLoading = false;
    private BottomSheetDialog bDialog;
    private int pageNumber = 1;
    private String storyId;
    RecyclerView recycler_repeat_meal;
    private RepeatMealIntoListAdapter adapter;
    ArrayList<FoodListByMealType.Datum> commonAddmealList;
    onSubmitRepeatMeal onSubmitRepeatMeal_listner;
    TextView txt_add_repeat_food;
    private List<FoodListByMealType.Datum> filtermList;
    ImageView close_repeat_dialog;
    String time="";

    private ArrayList<String> arylst_meal_list;

    public FoodRepeatMealFragmet(AddMealActivity addMealActivity) {
        onSubmitRepeatMeal_listner= (onSubmitRepeatMeal) addMealActivity;

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
        final View view = View.inflate(getContext(), R.layout.fragment_bottom_shit_dialog, null);

        mCtx = view.getContext();

        txt_add_repeat_food=view.findViewById(R.id.txt_add_repeat_food);
        close_repeat_dialog=view.findViewById(R.id.close_repeat_dialog);
        close_repeat_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        txt_add_repeat_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                filtermList=new ArrayList<>();
                for (int i = 0; i <commonAddmealList.size() ; i++) {
                    if (commonAddmealList.get(i).isSelect()){
                        filtermList.add(commonAddmealList.get(i));
                    }
                }
                if (filtermList.isEmpty()){
                    Toast.makeText(mCtx, "Please select meal", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
                onSubmitRepeatMeal_listner.getSelectedRepeatMealFood(filtermList);
            }
        });



        bDialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                    dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        /*
         * Call TimeLineArticle API
         */


        Bundle bundle=getArguments();
        if (commonAddmealList!=null){
            if (commonAddmealList.size()>1){
                commonAddmealList.clear();
            }
        }
        commonAddmealList= (ArrayList<FoodListByMealType.Datum>) bundle.getSerializable("KEY_RepeatMeal");
        arylst_meal_list=bundle.getStringArrayList("KEY_MEAL_TYPE");
        time=bundle.getString("time");
        recycler_repeat_meal=view.findViewById(R.id.recycler_repeat_meal);
//        adapter = new RepeatMealIntoListAdapter(mCtx, commonAddmealList,FoodRepeatMealFragmet.this,arylst_meal_list,time);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mCtx);
//        recycler_repeat_meal.setLayoutManager(layoutManager);
//        recycler_repeat_meal.setItemAnimator(new DefaultItemAnimator());
//        recycler_repeat_meal.setAdapter(adapter);

        return bDialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME,0);
    }



}

