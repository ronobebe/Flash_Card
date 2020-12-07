package com.example.flashcard.DataBase;

public interface DataBaseColumn {

    String QuestionID="question_id";
    String CreatedDate="created_date";
    String Question="question_text";
    String Answer="answer";
    String Category="category";
    String SunCategory="sub_category";

    // last visit Update
    String LastVisitDate="last_visit_date";
    String TotalVisit="total_visit";
    String QuestionStatus="question_status";

    //notes
    String NotesId="notes_id";
    String NotesText="notes_text";
    String UpdateDate="updated_date";

    // table name
    String FlashCardTable="flash_card_table";
    String StatusTable="status_table";
    String NotesTable="notes_table";
    String SubCategoryTable="sub_category_table";


    // category table
    String SubCategoryId="sub_category_id";




}
