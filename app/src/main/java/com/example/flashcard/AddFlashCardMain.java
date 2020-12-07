package com.example.flashcard;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.flashcard.DataBase.FlashCardLocalDataBase;

public class AddFlashCardMain extends BaseActivity implements View.OnClickListener, Add_SubCategoryDialogue.DialogListener {


    TextView noteType;

    ImageView  backArrow,save,addImage,imageViewNotes;

    TextView addcategory;

    EditText addQue,addNotes;

    private final String SUB_CATERGORY_DIALOGUE="Sub_Category_Dialogue";


    private static  final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE=100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_flash_card);

        initialize();
    }


    void initialize(){
        noteType=findViewById(R.id.noteType);

        backArrow=findViewById(R.id.backArrow);
        backArrow.setOnClickListener(this);

        save=findViewById(R.id.save);
        save.setOnClickListener(this);

       /* addImage=findViewById(R.id.addImage);
        addImage.setOnClickListener(this);*/

        imageViewNotes=findViewById(R.id.imageViewNotes);

        addcategory=findViewById(R.id.addcategory);
        addcategory.setOnClickListener(this);

        addNotes=findViewById(R.id.addNotes);

        addQue=findViewById(R.id.addQue);

        getDataFromArguments();
    }


    void getDataFromArguments(){
         Bundle b= getIntent().getBundleExtra(MainActivity.BUNDLE_KEY);
         noteType.setText(b.getString(MainActivity.argumentKey,""));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.backArrow:
                finish();
                setResult(RESULT_OK);
                break;

            case R.id.save:
if(checkInputValues())
    if(setFlashCardDetails())
        clearDataAfterInsert();

    else
                Toast.makeText(this, "Please fill required values", Toast.LENGTH_SHORT).show();


                break;

            case R.id.addcategory:
               getDialogueFragment();
                break;


        }
    }

    void clearDataAfterInsert(){
        addQue.setText("");
        addNotes.setText("");
        addcategory.setText("");
    }

    boolean setFlashCardDetails(){
        FlashCardDetails flashCardDetails=new FlashCardDetails();
        flashCardDetails.setCategory(noteType.getText().toString().trim());
        flashCardDetails.setSunCategory(addcategory.getText().toString().trim());
        flashCardDetails.setQuestion(addQue.getText().toString().trim());
        flashCardDetails.setAnswer(addNotes.getText().toString().trim());

        return  new FlashCardLocalDataBase(getApplicationContext()).insertFlashCardDetails(flashCardDetails);
    }



    boolean checkInputValues(){

        if(addcategory.getText().toString().trim().isEmpty()){
            return  false;
        }

        else if(addQue.getText().toString().trim().isEmpty()){
            return  false;
        }
        else if(addNotes.getText().toString().trim().isEmpty()){
            return  false;
        }

        return true ;
    }



    public  void getDialogueFragment(){

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
    }







}
