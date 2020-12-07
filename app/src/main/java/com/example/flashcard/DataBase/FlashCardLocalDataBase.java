package com.example.flashcard.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.flashcard.FlashCardDetails;
import com.example.flashcard.SubCategoryDetails;

import java.util.ArrayList;

public class FlashCardLocalDataBase extends  BaseDatabase implements  DB_Properties {

private  static  FlashCardLocalDataBase flashCardLocalDataBase;

private  static  DataBaseHelper dataBaseHelper;


    public static FlashCardLocalDataBase getInstance(Context context) {
        if (flashCardLocalDataBase == null) {
            flashCardLocalDataBase = new FlashCardLocalDataBase(context);
        }
        dataBaseHelper = new DataBaseHelper();
        return flashCardLocalDataBase;
    }


    public FlashCardLocalDataBase(@Nullable Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_FLASH_CARD);

        db.execSQL(CREATE_TABLE_CARD_STATUS);

        db.execSQL(CREATE_TABLE_FLASH_CARD_NOTES);

        db.execSQL(CREATE_TABLE_SUB_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }

    public boolean isSubCategoryAvailable(){
        return  new DataBaseHelper().isSubCategoryAvailable(this);
    }


    public ArrayList<SubCategoryDetails> getSubCategory(){
        return  new DataBaseHelper().getSubCategory(this);
    }

    public boolean insertSubCategoryDetails(SubCategoryDetails fd){
        return  new DataBaseHelper().insertSubCategoryDetails(this,fd);
    }



    public boolean isFlashCardDataAvailable(){
       return  new DataBaseHelper().isDataAvailable(this);
    }


    public ArrayList<FlashCardDetails> getFlashCards(){
        return  new DataBaseHelper().getFlashCards(this);
    }

    public boolean insertFlashCardDetails(FlashCardDetails fd){
        return  new DataBaseHelper().insertFlashCardDetails(this,fd);
    }

    public boolean isFlashCardStatusAvailable(String queID){
        return  new DataBaseHelper().isFlashCardStatusAvailable(this,queID);
    }

    public boolean insertFlashCardStatus(FlashCardDetails fd){
        return  new DataBaseHelper().insertFlashCardStatus(this,fd);
    }


    public boolean updateFlashCardStatusDetails(FlashCardDetails fd){
        return  new DataBaseHelper().updateFlashCardStatusDetails(this,fd);
    }

// notes

    public boolean isNotesAvailable(String queID){
        return  new DataBaseHelper().isNotesAvailable(this,queID);
    }


    public ArrayList<String> getNotes(String queID){
        return  new DataBaseHelper().getNotes(this,queID);
    }

    public boolean insertNotesDetails(String notes,String queID){
        return  new DataBaseHelper().insertNotesDetails(this,notes,queID);
    }


}
