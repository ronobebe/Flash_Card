package com.example.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcard.DataBase.FlashCardLocalDataBase;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener,
        CategoryDialogueFragment.DialogListener , Add_SubCategoryDialogue.DialogListener, MainListRecycler.RecyclerOnClick{

private final String CATERGORY_DIALOGUE="Category_Dialogue";

public  static  final String argumentKey="Header";

    public  static  final String headerKey="HeaderKey";
    public  static  final String positionKey="PositionKey";

public  static  final String bundleKey="bundleKey";

public  static  final int showCardRequestCode=100;

    public  static  final int addCardRequestCode=200;

private ImageView add_notes;

TextView all,syntax,general,algorithm,flashCardTotal;

MainListRecycler mainListRecycler;

RecyclerView categoryRecycler;

ArrayList<FlashCardDetails> flashCardDetailsArrayList=new ArrayList<>();

    ArrayList<FlashCardDetails> flashCardDetailsArrayListFiltered=new ArrayList<>();

    private final String SUB_CATERGORY_DIALOGUE="Sub_Category_Dialogue";

     private TextView addcategory;


     String title="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize view for the activity
        categoryRecycler=findViewById(R.id.categoryRecycler);


        if(checkDataIsPresent())
            getFlashCardDetails();
        else
            categoryRecycler.setVisibility(View.GONE);

         initalizeView();


    }





    public void setTitle(String title) {
        this.title = title;
    }


    private void initalizeView(){

        add_notes=findViewById(R.id.add_notes);
        add_notes.setOnClickListener(this);


        all=findViewById(R.id.all);
        all.setOnClickListener(this);

        syntax=findViewById(R.id.syntax);
        syntax.setOnClickListener(this);

        general=findViewById(R.id.general);
        general.setOnClickListener(this);

        algorithm=findViewById(R.id.algorithm);
        algorithm.setOnClickListener(this);



        addcategory=findViewById(R.id.addcategory);
        addcategory.setOnClickListener(this);

        flashCardTotal=findViewById(R.id.flashCardTotal);

        initializeRecyclerView(flashCardDetailsArrayList);

    }

    boolean checkDataIsPresent(){

        return  new FlashCardLocalDataBase(getApplicationContext()).isFlashCardDataAvailable();
    }


    void getFlashCardDetails(){


         flashCardDetailsArrayList=new FlashCardLocalDataBase(getApplicationContext()).getFlashCards();
         flashCardDetailsArrayListFiltered.addAll(flashCardDetailsArrayList);

    }

    private void changeListTitle(View view){
        TextView[] views={all,syntax,general,algorithm};


        for(int i=0; i<views.length; i++) {

            if (view.getId() == views[i].getId()) {

                views[i].setBackground(getResources().getDrawable(R.drawable.select_category_outline));
                views[i].setTextColor(Color.parseColor("#232323"));

            } else {

                views[i].setBackground(getResources().getDrawable(R.drawable.non_select_category_outline));
                views[i].setTextColor(Color.parseColor("#474545"));


            }

        }

    }

    void initializeRecyclerView(ArrayList<FlashCardDetails> flashCardDetailsArrayList1){

        flashCardTotal.setText("Total cards : "+flashCardDetailsArrayList1.size());

        mainListRecycler=new MainListRecycler(flashCardDetailsArrayList1,this);

        categoryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        categoryRecycler.setAdapter(mainListRecycler);
    }


    public  void getDialogueFragment(){

        /*
        * Create new instance for dialogue fragment
        * */
        CategoryDialogueFragment dialogueFragment=new CategoryDialogueFragment();

        /*
         * Create new instance for fragment Manager
         *
         * getSupportFragmentManager() give a Singleton instance
         *
         * */

        FragmentManager fm= getSupportFragmentManager();

        /*
         * Create instance for fragment transaction using fragment manager
         *
         * */

        FragmentTransaction ft=fm.beginTransaction();

       // check wheather fragment is available or not

        Fragment fr=getSupportFragmentManager().findFragmentByTag(CATERGORY_DIALOGUE);


        // if fragment is not null then remove it from backstack

        if(fr!=null)
            ft.remove(fr);

        ft.addToBackStack(null);


        // show dialogue using fragment manager

        dialogueFragment.show(fm,CATERGORY_DIALOGUE);



    }


    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.add_notes:

                // call category dialogue fragment
                getDialogueFragment();



                break;


            case R.id.all:
                setTitle("");
                changeListTitle(all);
                filterCondition(addcategory.getText().toString().trim(),"");
                break;

            case R.id.syntax:

                setTitle("syntax");
                changeListTitle(syntax);

                filterCondition(addcategory.getText().toString().trim(),syntax.getText().toString().trim());
                break;

            case R.id.general:

                setTitle("general");

                changeListTitle(general);
                filterCondition(addcategory.getText().toString().trim(),general.getText().toString().trim());

                break;

            case R.id.algorithm:

                setTitle("algorithm");

                changeListTitle(algorithm);
                filterCondition(addcategory.getText().toString().trim(),/*algorithm.getText().toString().trim()*/"algorithm");

                break;

            case R.id.addcategory:
                getSubCategoryDialogueFragment();

                break;
        }
    }

    @Override
    public void onFinishEditDialog(String inputText) {

        System.out.println("Input is : " + inputText);

        if(!inputText.isEmpty()) {

            Bundle b = new Bundle();
            b.putString(argumentKey, inputText);
            Intent i = new Intent(MainActivity.this, AddFlashCardMain.class);
            i.putExtra(BUNDLE_KEY, b);
            startActivityForResult(i,addCardRequestCode);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if(requestCode==addCardRequestCode){*/
            getFlashCardDetails();
            filterCondition(addcategory.getText().toString().trim(),title);


    }

/*
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission was granted. Now you can call your method to open camera, fetch contact or whatever

                } else {
                    // Permission was denied.......
                    // You can again ask for permission from here
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }
*/

    public  void getSubCategoryDialogueFragment(){

        /*
         * Create new instance for dialogue fragment
         * */
        Add_SubCategoryDialogue dialogueFragment=new Add_SubCategoryDialogue();

        /*
         * Create new instance for fragment Manager
         *
         * getSupportFragmentManager() give a Singleton instance
         *
         * */

        FragmentManager fm= getSupportFragmentManager();

        /*
         * Create instance for fragment transaction using fragment manager
         *
         * */

        FragmentTransaction ft=fm.beginTransaction();

        // check wheather fragment is available or not

        Fragment fr=getSupportFragmentManager().findFragmentByTag(SUB_CATERGORY_DIALOGUE);


        // if fragment is not null then remove it from backstack

        if(fr!=null)
            ft.remove(fr);

        ft.addToBackStack(null);


        // show dialogue using fragment manager

        dialogueFragment.show(fm,SUB_CATERGORY_DIALOGUE);



    }


    @Override
    public void onFinishSubCategoryEditDialog(String inputText) {

        addcategory.setText(inputText);
        filterCondition(inputText, title);

    }



    public void filterCondition(String subCategory, String category) {
        flashCardDetailsArrayListFiltered = new ArrayList<>();

        if (flashCardDetailsArrayList.size() != 0) {


            if (!subCategory.equals("Show all") && category.equals("")) {
                for (FlashCardDetails fd : flashCardDetailsArrayList) {
                    // filter based on category
                    if (fd.getSunCategory().toLowerCase().equals(subCategory.toLowerCase()))
                        flashCardDetailsArrayListFiltered.add(fd);
                }
            } else if (subCategory.equals("Show all") && !category.equals("")) {

                for (FlashCardDetails fd : flashCardDetailsArrayList) {
                    // filter based on sub category
                    if (fd.getCategory().toLowerCase().equals(category.toLowerCase()))
                        flashCardDetailsArrayListFiltered.add(fd);

                }
            } else if (subCategory.equals("Show all") && category.equals("")) {


                flashCardDetailsArrayListFiltered.addAll(flashCardDetailsArrayList);


            } else {
                for (FlashCardDetails fd : flashCardDetailsArrayList) {
                    // filter based on category and sub category
                    if ((fd.getCategory().toLowerCase().equals(category.toLowerCase()) && fd.getSunCategory().toLowerCase().equals(subCategory.toLowerCase())))
                        flashCardDetailsArrayListFiltered.add(fd);

                }
            }

            flashCardTotal.setText("Total cards : "+flashCardDetailsArrayListFiltered.size());

            if (flashCardDetailsArrayListFiltered.size() > 0) {
                categoryRecycler.setVisibility(View.VISIBLE);
                initializeRecyclerView(flashCardDetailsArrayListFiltered);
            } else
                categoryRecycler.setVisibility(View.GONE);


        }
    }

    @Override
    public void onClickRecycler(int pos, FlashCardDetails flashCardDetails) {

        Intent i=new Intent(this,ShowCardMain.class);
        Bundle b=new Bundle();
        String headKey=title==""?"All":title;
        b.putSerializable(bundleKey,flashCardDetailsArrayListFiltered);
        b.putString(headerKey,(headKey+" ("+addcategory.getText().toString().trim()+")"));
        b.putInt(positionKey,pos);
        i.putExtra(argumentKey,b);
        startActivityForResult(i,showCardRequestCode);

    }
}
