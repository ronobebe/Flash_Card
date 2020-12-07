package com.example.flashcard.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public abstract class BaseDatabase extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    public BaseDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public synchronized void openWritabledatabase() {
        if (database == null) {
            database = this.getWritableDatabase();
        }
    }

    public synchronized void closedatabase() {
        // use this to close base database
        if(database!=null)
            database.close();

    }

    public synchronized SQLiteDatabase getdatabase() {

        if (database == null || !database.isOpen()) {
            openWritabledatabase();
        }
        return database;
    }
}
