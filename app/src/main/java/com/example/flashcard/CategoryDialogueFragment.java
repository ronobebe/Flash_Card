package com.example.flashcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CategoryDialogueFragment extends DialogFragment implements
        View.OnClickListener, RadioGroup.OnCheckedChangeListener {

private TextView ok, cancel;

private RadioGroup radioGroup;

String categoryValue="";

DialogListener dialogListener;
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


    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.add_notes_dialogue, null, false);
        assert layout != null;

        initalizeView(layout);
        //build the alert dialog child of this fragment
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        //restore the background_color and layout_gravity that Android strips
        b.getContext().getTheme().applyStyle(R.style.MyAlertDialog, true);
        b.setView(layout);
        return b.create();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);

        View view=inflater.inflate(R.layout.add_notes_dialogue,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // initailize dialogue Listener

        dialogListener=(DialogListener)getActivity();



    }


    private void initalizeView(View v){

        // view for click OK button
        ok=v.findViewById(R.id.ok);
        ok.setOnClickListener(this);


        // view for click Cancel button
        cancel=v.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);


        // view for click Radio group
        radioGroup=v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ok:

                dialogListener.onFinishEditDialog(getCategoryValue());

                getDialog().dismiss();

                break;

            case R.id.cancel:
                dialogListener.onFinishEditDialog("");

                getDialog().dismiss();

                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){


            case R.id.algorithm:

               getCategoryHeader(CategoryData.Algorithm);

                break;

            case R.id.syntax:

                getCategoryHeader(CategoryData.Syntax);

                break;

            case R.id.general:

                getCategoryHeader(CategoryData.General);

                break;
        }


    }


    void getCategoryHeader(CategoryData cd){

        setCategoryValue(cd.getData());

    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }


    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }
}
