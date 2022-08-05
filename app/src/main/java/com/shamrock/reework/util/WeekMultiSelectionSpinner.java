package com.shamrock.reework.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WeekMultiSelectionSpinner extends AppCompatSpinner implements
        OnMultiChoiceClickListener {

    public interface OnMultipleItemsSelectedListener {
        void selectedIndices(List<Integer> indices);

        void selectedStrings(List<String> strings);
    }

    private OnMultipleItemsSelectedListener listener;

    String[] _items = null;
    boolean[] mSelection = null;
    boolean[] mSelectionAtStart = null;
    String _itemsAtStart = null;
    boolean selectAll = false;
    int listsize ;

    int totalItems;
    ArrayAdapter<String> simple_adapter;

    public WeekMultiSelectionSpinner(Context context) {
        super(context);

        simple_adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public WeekMultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        simple_adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item);
        super.setAdapter(simple_adapter);
    }

    public void setListener(OnMultipleItemsSelectedListener listener) {
        this.listener = listener;
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {


            if (which==0){

                if (isChecked) {
                    for (int i = 0; i < 1; i++) {
                        mSelection[i] = true;
                        listsize = totalItems-1;
                        ((AlertDialog) dialog).getListView().setItemChecked(i, true);
                    }

                } else {

                    for (int i = 0; i < 1; i++) {
                        mSelection[i] = false;
                        listsize = 0;
                        ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                    }
                }


                simple_adapter.clear();
                simple_adapter.add(buildSelectedItemString());

//                if (mSelection != null) {
//
//
//
//
//
//
//                    for (int i = 0; i < _items.length; i++) {
//                        mSelection[i] = true;
//                        ((AlertDialog) dialog).getListView().setItemChecked(i, true);
//
//                    }
//                    simple_adapter.clear();
//                    simple_adapter.add(buildSelectedItemString());
//
//
//                } else {
//                    throw new IllegalArgumentException("mSelection is null");
//                }
            }else {

                mSelection[which] = isChecked;


                simple_adapter.clear();
                simple_adapter.add(buildSelectedItemString());
            }



            if (listsize == totalItems-1) {
                mSelection[0] = true;
                ((AlertDialog) dialog).getListView().setItemChecked(0, true);
            } else {
                mSelection[0] = false;
                ((AlertDialog) dialog).getListView().setItemChecked(0, false);


            }
        }
    }

//    protected void selectAll(boolean isSelectAll, AlertDialog.Builder dialog) {
//        if (mSelection != null) {
//            for (int i = 0; i < _items.length; i++) {
//                mSelection[i] = isSelectAll;
//                dialog.getListView().setItemChecked(i, isSelectAll);
//
//            }
//
//            simple_adapter.clear();
//            simple_adapter.add(buildSelectedItemString());
//        } else {
//            throw new IllegalArgumentException("mSelection is null");
//        }
//    }


    @Override
    public boolean performClick() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMultiChoiceItems(_items, mSelection, this);
            builder.setTitle("Select Days");

            builder.setMultiChoiceItems(_items, mSelection, this);
//        _itemsAtStart = getSelectedItemsAsString();
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

//            builder.setNeutralButton("Select All", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//
//                }
//            });


            final AlertDialog dialog = builder.create();
            dialog.show();


//            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(
//                    new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Boolean wantToCloseDialog = false;
//                            selectAll(selectAll, dialog);
//
//                            if (wantToCloseDialog)
//                                dialog.dismiss();
//                        }
//                    });
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            try {

                                if (simple_adapter!=null){
                                    simple_adapter.clear();

                                }
                                dialog.dismiss();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                    new OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);

                            if (getSelectedIndices().isEmpty()){
                                Toast.makeText(getContext(), "Please select week days", Toast.LENGTH_SHORT).show();
                                return;
                            }
//                            if (getSelectedIndices().size()>2){
//                                Toast.makeText(getContext(), "Please select only two week days", Toast.LENGTH_SHORT).show();
//                                return;
//                            }


                            listener.selectedIndices(getSelectedIndices());
                            listener.selectedStrings(getSelectedStrings());
                            dialog.dismiss();

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    protected void selectAll(boolean isSelectAll, AlertDialog dialog) {
        if (mSelection != null) {




            if (!isSelectAll){

                isSelectAll=true;
            }else {
                isSelectAll=false;
            }


            for (int i = 0; i < _items.length; i++) {
                mSelection[i] = isSelectAll;
                ((AlertDialog) dialog).getListView().setItemChecked(i, isSelectAll);

            }
            simple_adapter.clear();
            simple_adapter.add(buildSelectedItemString());


        } else {
            throw new IllegalArgumentException("mSelection is null");
        }
    }


//    @Override
//    public boolean performClick() {
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
////        builder.setView(customLayout);
//
//
//
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//
//        //        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
////        builder.setView(customLayout);
//        builder.setTitle("Select Days");
//        builder.setMultiChoiceItems(_items, mSelection, this);
//        _itemsAtStart = getSelectedItemsAsString();
//
//
//        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);
//                listener.selectedIndices(getSelectedIndices());
//                listener.selectedStrings(getSelectedStrings());
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                simple_adapter.clear();
//                simple_adapter.add(_itemsAtStart);
//                System.arraycopy(mSelectionAtStart, 0, mSelection, 0, mSelectionAtStart.length);
//            }
//        });
//
//
//        builder.setNeutralButton("Select All", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//
//                selectAll(false,builder);
//            }
//        });
//        builder.show();
//
//        return true;
//    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(String[] items) {
        _items = items;
        mSelection = new boolean[_items.length];
        mSelectionAtStart = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
        mSelectionAtStart[0] = true;
        listsize=0;
        totalItems = items.length;
        mSelection = new boolean[items.length];
    }

    public void setItems(List<String> items) {
        _items = items.toArray(new String[items.size()]);
        mSelection = new boolean[_items.length];
        mSelectionAtStart = new boolean[_items.length];
        simple_adapter.clear();
        simple_adapter.add(_items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
    }

    public void setSelection(String[] selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (String cell : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(cell)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(List<String> selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int index) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
            mSelectionAtStart[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index
                    + " is out of bounds.");
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public void setSelection(int[] selectedIndices) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (int index : selectedIndices) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
                mSelectionAtStart[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index
                        + " is out of bounds.");
            }
        }
        simple_adapter.clear();
        simple_adapter.add(buildSelectedItemString());
    }

    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                selection.add(_items[i]);
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndices() {
        List<Integer> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {

            if (_items.length==8){
                if (i>0){
                    if (mSelection[i]) {
                        selection.add(i);
                    }
                }

            }else {
                if (mSelection[i]) {
                    selection.add(i);
                }
            }

        }
        return selection;
    }

    private String buildSelectedItemString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;

                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }

    public String getSelectedItemsAsString() {
        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < _items.length; ++i) {
            if (mSelection[i]) {
                if (foundOne) {
                    sb.append(", ");
                }
                foundOne = true;
                sb.append(_items[i]);
            }
        }
        return sb.toString();
    }
}