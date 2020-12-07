package com.example.flashcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.DataBase.FlashCardLocalDataBase;

import java.util.ArrayList;

public class Add_SubCategoryDialogue extends DialogFragment
        implements View.OnClickListener, SubCategoryAdapter.RecyclerOnClick {


    private  final String activityName="com.example.flashcard.MainActivity";

    DialogListener dialogListener;

    RecyclerView subCategoryRecycler;

    EditText category;

    TextView ok;

    ArrayList<SubCategoryDetails> stringArrayList;

    @Override
    public void onResume() {
        super.onResume();
      /*  Window window = getDialog().getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = params.WRAP_CONTENT;
        params.height = params.WRAP_CONTENT;

        window.setAttributes(params);*/
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stringArrayList= new ArrayList<>();

getCategoryDataFromDB();



    }


    void getCategoryDataFromDB(){
        stringArrayList.clear();
        if(new FlashCardLocalDataBase(getActivity()).isSubCategoryAvailable()) {

            // i main activty add show all default value in list

            String name = getActivity().getClass().getName();
            if (activityName.equals(name)) {

                SubCategoryDetails scd = new SubCategoryDetails();
                scd.setSubCategoryID("0");
                scd.setSubCategory("Show all");
                stringArrayList.add(scd);

            }

            stringArrayList .addAll( new FlashCardLocalDataBase(getActivity()).getSubCategory());
        }
        else {
            String name = getActivity().getClass().getName();
            if (activityName.equals(name)) {

                SubCategoryDetails scd = new SubCategoryDetails();
                scd.setSubCategoryID("0");
                scd.setSubCategory("Show all");
                stringArrayList.add(scd);

            }
            else
            Toast.makeText(getActivity(), "Please add category", Toast.LENGTH_SHORT).show();

        }
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.sub_categorydialogue, null, false);
        assert layout != null;

        initalizeView(layout);
        //build the alert dialog child of this fragment
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        //restore the background_color and layout_gravity that Android strips
        b.getContext().getTheme().applyStyle(R.style.MyAlertDialog, true);
        b.setView(layout);
        return b.create();
    }


    void initalizeView(View view){

        subCategoryRecycler=view.findViewById(R.id.subCategoryRecycler);
        category=view.findViewById(R.id.category);

        ok=view.findViewById(R.id.ok);
        ok.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);

        View view=inflater.inflate(R.layout.sub_categorydialogue,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialogListener=(Add_SubCategoryDialogue.DialogListener)getActivity();

        initializeRecyclerView(stringArrayList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ok:

               if(setSubCategoryDataFromDB()) {
                   category.setText("");
                   getCategoryDataFromDB();
                   initializeRecyclerView(stringArrayList);
               }

                break;
        }
    }


    boolean setSubCategoryDataFromDB(){


        SubCategoryDetails subCategoryDetails= new SubCategoryDetails();
        subCategoryDetails.setSubCategory(category.getText().toString().trim());
        return new FlashCardLocalDataBase(getActivity()).insertSubCategoryDetails(subCategoryDetails);
    }


    void initializeRecyclerView(ArrayList<SubCategoryDetails> subCategoryDetailsArrayList){

                subCategoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                subCategoryRecycler.setAdapter(new SubCategoryAdapter(subCategoryDetailsArrayList,this));


    }

    @Override
    public void onClickRecycler(int pos, SubCategoryDetails o) {

        dialogListener.onFinishSubCategoryEditDialog(o.getSubCategory());
        dismiss();
    }

    public interface DialogListener {
        void onFinishSubCategoryEditDialog(String inputText);
    }

}
