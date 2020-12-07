package com.example.flashcard.DataBase;

public interface DataBaseQuery extends DataBaseStatments, DataBaseColumn {

    String CREATE_TABLE_FLASH_CARD=

             CREATE_TABLE_IF_NOT_EXISTS +
                     FlashCardTable + "' ('" +
                     QuestionID + INTEGER_PRIMARY_KEY_AUTOINCREMENT +
                     CreatedDate + TEXT_NOT_NULL +
                     Question + TEXT_NOT_NULL +
                     Answer + TEXT_NOT_NULL +
                     Category + TEXT_NOT_NULL +
                     SunCategory + "' TEXT)";

    String CREATE_TABLE_CARD_STATUS=

            CREATE_TABLE_IF_NOT_EXISTS +
                    StatusTable + "' ('" +
                    QuestionID + INTEGER +
                    LastVisitDate + TEXT_NOT_NULL +
                    TotalVisit + TEXT_NOT_NULL +
                    QuestionStatus + "' TEXT)";


    String CREATE_TABLE_FLASH_CARD_NOTES=

            CREATE_TABLE_IF_NOT_EXISTS +
                    NotesTable + "' ('" +
                    NotesId + INTEGER_PRIMARY_KEY_AUTOINCREMENT +
                    NotesText + TEXT_NOT_NULL +
                    QuestionID + TEXT_NOT_NULL +
                    UpdateDate + "' TEXT)";

    String CREATE_TABLE_SUB_CATEGORY=

            CREATE_TABLE_IF_NOT_EXISTS +
                    SubCategoryTable + "' ('" +
                    SubCategoryId + INTEGER_PRIMARY_KEY_AUTOINCREMENT +
                    SunCategory + "' TEXT)";



}
