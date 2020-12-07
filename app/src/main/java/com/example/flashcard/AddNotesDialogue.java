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

import static com.example.flashcard.ShowCardMain.queIdKey;

public class AddNotesDialogue extends DialogFragment
        implements View.OnClickListener {


    private  final String activityName="com.example.flashcard.MainActivity";

    DialogListener dialogListener;

    RecyclerView subCategoryRecycler;

    EditText category;

    TextView ok,flashCardTitle;

    ArrayList<String> stringArrayList;

    String queID;

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

        if(getArguments()!=null)
            queID=getArguments().getString(queIdKey);
            stringArrayList= new ArrayList<>();



        getCategoryDataFromDB(queID);



    }


    void getCategoryDataFromDB(String queID){
        stringArrayList.clear();
        if(new FlashCardLocalDataBase(getActivity()).isNotesAvailable(queID)) {
            if(subCategoryRecycler!=null)
            subCategoryRecycler.setVisibility(View.VISIBLE);

            stringArrayList .addAll( new FlashCardLocalDataBase(getActivity()).getNotes(queID));
        }

            else
                if(subCategoryRecycler!=null)
                subCategoryRecycler.setVisibility(View.INVISIBLE);


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

        if(stringArrayList.size()==0)
            subCategoryRecycler.setVisibility(View.INVISIBLE);

        category=view.findViewById(R.id.category);
        category.setHint("Enter Notes");

        flashCardTitle=view.findViewById(R.id.flashCardTitle);
        flashCardTitle.setText("Add Notes");

        ok=view.findViewById(R.id.ok);
        ok.setText("ADD");
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

        dialogListener=(AddNotesDialogue.DialogListener)getActivity();

        initializeRecyclerView(stringArrayList);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ok:

                if(setSubCategoryDataFromDB(queID)) {
                    category.setText("");
                    getCategoryDataFromDB(queID);
                    initializeRecyclerView(stringArrayList);
                }

                break;
        }
    }


    boolean setSubCategoryDataFromDB(String queID){


        return new FlashCardLocalDataBase(getActivity()).insertNotesDetails(category.getText().toString().trim(),queID);
    }


    void initializeRecyclerView(ArrayList<String> subCategoryDetailsArrayList){

        subCategoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        subCategoryRecycler.setAdapter(new AddNotesAdapter(subCategoryDetailsArrayList));


    }



    public interface DialogListener {
        void onFinishSubCategoryEditDialog(String inputText);
    }

}
