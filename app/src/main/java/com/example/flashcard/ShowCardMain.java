package com.example.flashcard;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.flashcard.DataBase.FlashCardLocalDataBase;

import java.util.ArrayList;

import static com.example.flashcard.MainActivity.argumentKey;
import static com.example.flashcard.MainActivity.bundleKey;
import static com.example.flashcard.MainActivity.headerKey;
import static com.example.flashcard.MainActivity.positionKey;

public class ShowCardMain extends  BaseActivity  implements View.OnClickListener,
        AddNotesDialogue.DialogListener{

    ArrayList<FlashCardDetails> flashCardDetailsArrayList;

    Button prev,known,next;

    ImageView notes,backArrow;

    TextView noteType,totalCards,addNotes;

    int quePosition=0;
    // Animation
    Animation animZoomIn;

    public  static  final String bundleKey="bundleKey";
    public  static  final String queIdKey="bundleKey";
    private final String ADD_NOTES_DIALOGUE="Add_notes_Dialogue";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_card_main);
        flashCardDetailsArrayList=new ArrayList<>();

        getArrayListFromArguments();

        initializer();

        queSetter(quePosition,addNotes);


    }

    void initializer(){
        prev=findViewById(R.id.prev);
        prev.setOnClickListener(this);

        known=findViewById(R.id.known);
        known.setOnClickListener(this);

        next=findViewById(R.id.next);
        next.setOnClickListener(this);

        notes=findViewById(R.id.notes);
        notes.setOnClickListener(this);

        backArrow=findViewById(R.id.backArrow);
        backArrow.setOnClickListener(this);

        addNotes=findViewById(R.id.addNotes);
        addNotes.setMovementMethod(new ScrollingMovementMethod());
        addNotes.setOnClickListener(this);

        noteType=findViewById(R.id.noteType);
        noteType.setText(getIntent().getBundleExtra(argumentKey).getString(headerKey));

        totalCards=findViewById(R.id.totalCards);
        totalCards.setText("Total cards : "+flashCardDetailsArrayList.size());


        // load the animation
        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zoom_in);
    }


    public  void getSubCategoryDialogueFragment(){

        /*
         * Create new instance for dialogue fragment
         * */
        AddNotesDialogue dialogueFragment=new AddNotesDialogue();

        Bundle b = new Bundle();
        b.putString(queIdKey,flashCardDetailsArrayList.get(quePosition).getQuestionID());
        dialogueFragment.setArguments(b);

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

        Fragment fr=getSupportFragmentManager().findFragmentByTag(ADD_NOTES_DIALOGUE);


        // if fragment is not null then remove it from backstack

        if(fr!=null)
            ft.remove(fr);

        ft.addToBackStack(null);


        // show dialogue using fragment manager

        dialogueFragment.show(fm,ADD_NOTES_DIALOGUE);



    }



    void getArrayListFromArguments(){

        Bundle  b=getIntent().getBundleExtra(argumentKey);
        quePosition=b.getInt(positionKey);
        flashCardDetailsArrayList=(ArrayList<FlashCardDetails>) b.getSerializable(bundleKey);


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){


            case R.id.prev:

                checkquePos(prev,quePosition);
                break;

            case R.id.known:
                updateQuestionStatus(quePosition);
                break;

            case R.id.next:
                checkquePos(next,quePosition);
                break;

            case R.id.notes:
                getSubCategoryDialogueFragment();
                break;

            case R.id.backArrow:
                finish();
                break;

            case R.id.addNotes:
                addNotes.startAnimation(animZoomIn);
                showAns(quePosition,addNotes);
                break;




        }
    }

    private void showAns(int pos, TextView tv){
        tv.setText(flashCardDetailsArrayList.get(pos).getAnswer());
    }

    private void checkquePos(Button bn,int pos){
        if((pos==0 && bn.getId()==R.id.prev)){
            Toast.makeText(this, "You viewed all que", Toast.LENGTH_SHORT).show();
        }
        else if(( bn.getId()!=R.id.prev && (pos==flashCardDetailsArrayList.size()-1))){
            Toast.makeText(this, "You viewed all que", Toast.LENGTH_SHORT).show();

        }
        else{

            if(bn.getId()==R.id.prev)
                quePosition--;
            else
                quePosition++;

            queSetter(quePosition,addNotes);

        }
    }

    boolean updateQuestionStatus (int pos)
    {

        flashCardDetailsArrayList.get(pos).setQuestionStatus("Known");

        new FlashCardLocalDataBase(getApplicationContext()).updateFlashCardStatusDetails(flashCardDetailsArrayList.get(pos));
        return true;
    }



    private void queSetter(int pos, TextView tv){
        if(flashCardDetailsArrayList.get(pos).getQuestionStatus().toLowerCase().equals("known"))
            known.setBackgroundResource(R.drawable.button_bag_green);
        else
            known.setBackgroundResource(R.drawable.button_bag);
       updateVisitCount(flashCardDetailsArrayList.get(pos).getQuestionID(),pos);
        tv.setText(flashCardDetailsArrayList.get(pos).getQuestion());
    }


    boolean updateVisitCount(String queId,int pos){

        if(!new FlashCardLocalDataBase(getApplicationContext()).isFlashCardStatusAvailable(queId)){
            // flash card data available so update visit count here

            flashCardDetailsArrayList.get(pos).setTotalVisit("1");
            flashCardDetailsArrayList.get(pos).setQuestionStatus("UnKnown");
            new FlashCardLocalDataBase(getApplicationContext()).insertFlashCardStatus( flashCardDetailsArrayList.get(pos));
            return true;
        }
        else {
            flashCardDetailsArrayList.get(pos).setTotalVisit("" + (1 + Integer.parseInt(flashCardDetailsArrayList.get(pos).getTotalVisit())));

            new FlashCardLocalDataBase(getApplicationContext()).updateFlashCardStatusDetails(flashCardDetailsArrayList.get(pos));
            return true;
        }
    }

    @Override
    public void onFinishSubCategoryEditDialog(String inputText) {

    }
}
