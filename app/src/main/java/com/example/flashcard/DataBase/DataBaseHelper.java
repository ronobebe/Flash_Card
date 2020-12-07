package com.example.flashcard.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.flashcard.BuildConfig;
import com.example.flashcard.FlashCardDetails;
import com.example.flashcard.SubCategoryDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.flashcard.DataBase.DataBaseColumn.FlashCardTable;
import static com.example.flashcard.DataBase.DataBaseStatments.INSERT_INTO;
import static com.example.flashcard.DataBase.DataBaseStatments.SELECT_FROM;

public class DataBaseHelper implements  DataBaseColumn {

    private SQLiteDatabase db;


    public static String checkNull(String value) {
        if (value == null) {
            return "";
        } else if (value.equals("null")) {
            return "";
        }
        return value;
    }

    // check weather detail is available or not

    public boolean isSubCategoryAvailable(BaseDatabase baseDatabase){

        db = baseDatabase.getdatabase();
        String query = SELECT_FROM + SubCategoryTable ;
        Cursor c = db.rawQuery(query, null);
        try {
            if (c != null && c.getCount() > 0) {
                c.close();
                return true;
            }
        } finally {
            baseDatabase.closedatabase();
        }
        return false;
    }



    // get flash card details
    public ArrayList<SubCategoryDetails> getSubCategory(BaseDatabase baseDatabase){

        db = baseDatabase.getdatabase();
        String query = SELECT_FROM + SubCategoryTable ;

        Cursor c = db.rawQuery(query, null);


        ArrayList<SubCategoryDetails> subCategoryDetailsArrayList=new ArrayList<>();
        try {
            if (c != null && c.getCount() > 0) {



                while (c.moveToNext()){
                    SubCategoryDetails subCategoryDetails=new SubCategoryDetails();

                    subCategoryDetails.setSubCategoryID(c.getString(c.getColumnIndex(SubCategoryId)));
                    subCategoryDetails.setSubCategory(c.getString(c.getColumnIndex(SunCategory)));
                    subCategoryDetailsArrayList.add(subCategoryDetails);
                }

            }

            return subCategoryDetailsArrayList;
        }

        finally {
            baseDatabase.closedatabase();
        }



    }



    // inset flash card table

    public  boolean insertSubCategoryDetails(BaseDatabase baseDatabase, SubCategoryDetails subCategoryDetails){

        db = baseDatabase.getdatabase();

        String sql = INSERT_INTO + SubCategoryTable + " VALUES (NULL,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        try {
            statement.clearBindings();

            statement.bindString(1, checkNull(subCategoryDetails.getSubCategory()));




            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w("DUPLICATE_TAG", e);
            return false;
        }

        return  true;
    }










    // check weather detail is available or not

    public boolean isDataAvailable(BaseDatabase baseDatabase){

        db = baseDatabase.getdatabase();
        String query = SELECT_FROM + FlashCardTable ;
        Cursor c = db.rawQuery(query, null);
        try {
            if (c != null && c.getCount() > 0) {
                c.close();
                return true;
            }
        } finally {
            baseDatabase.closedatabase();
        }
        return false;
    }



    // get flash card details
    public ArrayList<FlashCardDetails> getFlashCards(BaseDatabase baseDatabase){

        db = baseDatabase.getdatabase();


        String query="SELECT "+FlashCardTable+"."+QuestionID+", "+FlashCardTable+"."+CreatedDate+", "+FlashCardTable+"."+Question+", "
                +FlashCardTable+"."+Answer+", "+FlashCardTable+"."+Category+", "+FlashCardTable+"."+SunCategory+", "
                +StatusTable+"."+LastVisitDate+", "+StatusTable+"."+TotalVisit+", "+StatusTable+"."+QuestionStatus
                +" FROM "+FlashCardTable +" LEFT JOIN " +StatusTable+
                " ON "+FlashCardTable+"."+QuestionID+" = "  +StatusTable+"."+QuestionID;

       /* String query = SELECT_FROM + FlashCardTable +" LEFT JOIN " +StatusTable+
                " ON "+FlashCardTable+"."+QuestionID+" = "  +StatusTable+"."+QuestionID;*/

        Cursor c = db.rawQuery(query, null);


        ArrayList<FlashCardDetails> flashCardDetailsArrayList=new ArrayList<>();
        try {
            if (c != null && c.getCount() > 0) {



                while (c.moveToNext()){
                    FlashCardDetails flashCardDetails=new FlashCardDetails();
                    flashCardDetails.setQuestionID(checkNull(c.getString(c.getColumnIndex(QuestionID))));
                    flashCardDetails.setCreatedDate(checkNull(c.getString(c.getColumnIndex(CreatedDate))));
                    flashCardDetails.setQuestion(checkNull(c.getString(c.getColumnIndex(Question))));
                    flashCardDetails.setAnswer(checkNull(c.getString(c.getColumnIndex(Answer))));
                    flashCardDetails.setCategory(checkNull(c.getString(c.getColumnIndex(Category))));
                    flashCardDetails.setSunCategory(checkNull(c.getString(c.getColumnIndex(SunCategory))));
                    flashCardDetails.setLastVisitDate(checkNull(c.getString(c.getColumnIndex(LastVisitDate))));
                    flashCardDetails.setTotalVisit(checkNull(c.getString(c.getColumnIndex(TotalVisit))));
                    flashCardDetails.setQuestionStatus(checkNull(c.getString(c.getColumnIndex(QuestionStatus))));

                    flashCardDetailsArrayList.add(flashCardDetails);
                }

            }

            return flashCardDetailsArrayList;
        }

        finally {
            baseDatabase.closedatabase();
        }



    }



    // inset flash card table

    public  boolean insertFlashCardDetails(BaseDatabase baseDatabase, FlashCardDetails flashCardDetails){

        db = baseDatabase.getdatabase();

        String sql = INSERT_INTO + FlashCardTable + " VALUES (NULL,?,?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        try {
            statement.clearBindings();



            //statement.bindString(1, checkNull(flashCardDetails.getQuestionID()));
            statement.bindString(1, checkNull(""+new SimpleDateFormat("MMM dd, yy").format(new Date())));
            statement.bindString(2, checkNull(flashCardDetails.getQuestion()));
            statement.bindString(3, checkNull(flashCardDetails.getAnswer()));
            statement.bindString(4, checkNull(flashCardDetails.getCategory()));
            statement.bindString(5, checkNull(flashCardDetails.getSunCategory()));



            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w("DUPLICATE_TAG", e);
            return false;
        }

        return  true;
    }




    public boolean isFlashCardStatusAvailable(BaseDatabase baseDatabase,String queID){

        db = baseDatabase.getdatabase();
        String query = SELECT_FROM + StatusTable + " WHERE "+QuestionID+" = "+queID ;
        Cursor c = db.rawQuery(query, null);
        try {
            if (c != null && c.getCount() > 0) {
                c.close();
                return true;
            }
        } finally {
            baseDatabase.closedatabase();
        }
        return false;
    }





    public  boolean updateFlashCardStatusDetails( BaseDatabase baseDatabase, FlashCardDetails flashCardDetails){

        db = baseDatabase.getdatabase();


        ContentValues cv =new ContentValues();
        try {
            cv.put("total_visit",flashCardDetails.getTotalVisit());
            cv.put("question_status",flashCardDetails.getQuestionStatus());
            cv.put("last_visit_date",""+new SimpleDateFormat("MMM dd, yy").format(new Date()));

       int i=     db.update(StatusTable, cv, "question_id = ?", new String[]{flashCardDetails.getQuestionID()});


        }
        catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w("DUPLICATE_TAG", e);
            return false;
        }

        return  true;
    }




    public  boolean insertFlashCardStatus(BaseDatabase baseDatabase, FlashCardDetails flashCardDetails){

        db = baseDatabase.getdatabase();
        String sql = INSERT_INTO + StatusTable + " VALUES (?,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        try {
            statement.clearBindings();


            statement.bindString(1, checkNull(flashCardDetails.getQuestionID()));
            statement.bindString(2, checkNull(""+new SimpleDateFormat("MMM dd, yy").format(new Date())));
            statement.bindString(3, checkNull(flashCardDetails.getTotalVisit()));
            statement.bindString(4, checkNull(flashCardDetails.getQuestionStatus()));




            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w("DUPLICATE_TAG", e);
            return false;
        }

        return  true;
    }




    // update flash card status





    // insert flash card notes

    public  boolean insertNotesDetails( BaseDatabase baseDatabase,String notes,String queID){
        db = baseDatabase.getdatabase();
        String sql = INSERT_INTO + NotesTable + " VALUES (NULL,?,?,?);";

        SQLiteStatement statement = db.compileStatement(sql);
        try {
            statement.clearBindings();



            statement.bindString(1, checkNull(notes));
            statement.bindString(2, checkNull(queID));
            statement.bindString(3, checkNull(""+new SimpleDateFormat("MMM dd, yy").format(new Date())));


            statement.execute();
        } catch (SQLiteException e) {
            if (BuildConfig.DEBUG)
                Log.w("DUPLICATE_TAG", e);
            return false;
        }

        return  true;
    }



    public boolean isNotesAvailable(BaseDatabase baseDatabase,String queID){

        db = baseDatabase.getdatabase();
        String query = SELECT_FROM + NotesTable + " WHERE "+QuestionID+" = "+queID ;
        Cursor c = db.rawQuery(query, null);
        try {
            if (c != null && c.getCount() > 0) {
                c.close();
                return true;
            }
        } finally {
            baseDatabase.closedatabase();
        }
        return false;
    }


    public ArrayList<String> getNotes(BaseDatabase baseDatabase, String queID){

        db = baseDatabase.getdatabase();
        String query = SELECT_FROM + NotesTable+ " WHERE "+QuestionID+" = "+queID ;

        Cursor c = db.rawQuery(query, null);


        ArrayList<String> subCategoryDetailsArrayList=new ArrayList<>();
        try {
            if (c != null && c.getCount() > 0) {



                while (c.moveToNext()){

                    subCategoryDetailsArrayList.add(c.getString(c.getColumnIndex(NotesText)));
                }

            }

            return subCategoryDetailsArrayList;
        }

        finally {
            baseDatabase.closedatabase();
        }



    }


}
