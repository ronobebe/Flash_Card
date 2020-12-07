package com.example.flashcard.DataBase;

public interface DataBaseStatments {

    String INSERT_INTO = "INSERT INTO ";
    String SELECT_FROM = "SELECT * FROM ";
    String DELETE_FROM = "DELETE FROM ";
    String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS '";

    String INTEGER_PRIMARY_KEY_AUTOINCREMENT = "' INTEGER PRIMARY KEY AUTOINCREMENT, " + "'";

    String TEXT = "' TEXT, " + "'";
    String TEXT_NULL = "' TEXT NULL, " + "'";
    String TEXT_NOT_NULL = "' TEXT NOT NULL, " + "'";

    String INTEGER = "' INTEGER, " + "'";
    String INTEGER_NOT_NULL = "' INTEGER NOT NULL, " + "'";

    String NUMBER = "' NUMBER, " + "'";
    String NUMBER_NOT_NULL = "' NUMBER NOT NULL, '";

    String DATE_NOT_NULL = "' DATE NOT NULL, " + "'";


    String TEXT_DEFAULT_ZERO_POINT = "' TEXT DEFAULT '0.0000', " + "'";
    String INTEGER_DEFAULT_ZERO = "' INTEGER DEFAULT 0, " + "'";
    String TEXT_DEFAULT = "' TEXT DEFAULT '', '";
    String TEXT_NOT_NULL_DEFAULT_N = "' TEXT  NOT NULL DEFAULT 'N', '";
}
