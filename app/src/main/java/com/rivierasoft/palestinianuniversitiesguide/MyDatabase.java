package com.rivierasoft.palestinianuniversitiesguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.rivierasoft.palestinianuniversitiesguide.Models.SavedProgram;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "pug_db";
    public static final int DB_VERSION = 1;

    public static final String SAVED_PROGRAM_TB_NAME = "saved_program";
    public static final String SAVED_PROGRAM_CLN_ID = "id";
    public static final String SAVED_PROGRAM_CLN_DOCUMENT_ID = "document_id";


    public MyDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+SAVED_PROGRAM_TB_NAME+" ("+SAVED_PROGRAM_CLN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                SAVED_PROGRAM_CLN_DOCUMENT_ID +" TEXT NOT NULL" +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ SAVED_PROGRAM_TB_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean saveProgram(SavedProgram program) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SAVED_PROGRAM_CLN_DOCUMENT_ID, program.getDocumentID());
        long result= db.insert(SAVED_PROGRAM_TB_NAME, null, contentValues);
        return result != -1;
    }

    public boolean unsaveProgram(SavedProgram program) {
        SQLiteDatabase db = getReadableDatabase();
        String args [] = {program.getDocumentID()};
        int result= db.delete(SAVED_PROGRAM_TB_NAME, "document_id=?", args);
        return result > 0; // != 0
    }

    public ArrayList<SavedProgram> getSavedPrograms() {
        ArrayList<SavedProgram> savedPrograms = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+SAVED_PROGRAM_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(SAVED_PROGRAM_CLN_ID));
                String documentID = cursor.getString(cursor.getColumnIndex(SAVED_PROGRAM_CLN_DOCUMENT_ID));
                SavedProgram savedProgram = new SavedProgram(id, documentID);
                savedPrograms.add(savedProgram);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return savedPrograms;
    }
}
