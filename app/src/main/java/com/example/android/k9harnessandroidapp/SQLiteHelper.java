package com.example.android.k9harnessandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Jon on 11/20/17 for the K9 Dog Collar Project.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private Context context;
    private int sessionID = -1;

    public static final String DATABASE_NAME = "DogHarness.db";
    public static final int VERSION = 1;

    public SQLiteHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION);
        context = ctx;

        //TODO: Remove this upgrade statement once the database schema is final.
        onUpgrade(getWritableDatabase(), 1, 1);

        insertTestValues();

        beginSession(0, 0);
        endSession();
        beginSession(0, 0);
        endSession();
        beginSession(0, 0);
        endSession();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Dog(" +
                "DogID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Handler(" +
                "HandlerID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FirstName Text NOT NULL," +
                "LastName Text NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Session(" +
                "SessionID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DogID INTEGER NOT NULL," +
                "HandlerID INTEGER NOT NULL," +
                "FOREIGN KEY (DogID) REFERENCES Dog(DogID)," +
                "FOREIGN KEY (HandlerID) REFERENCES Handler(HandlerID))");

        db.execSQL("CREATE TABLE IF NOT EXISTS SessionTick(" +
                "SessionTickID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SessionID INTEGER NOT NULL," +
                "SessionTick INTEGER NOT NULL," +
                "HeartRate INTEGER NOT NULL," +
                "RespiratoryRate INTEGER NOT NULL," +
                "CoreTemperature INTEGER NOT NULL," +
                "AbdominalTemperature INTEGER NOT NULL," +
                "FOREIGN KEY (SessionID) REFERENCES Session(SessionID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SessionTick");
        db.execSQL("DROP TABLE IF EXISTS Session");
        db.execSQL("DROP TABLE IF EXISTS Handler");
        db.execSQL("DROP TABLE IF EXISTS Dog");

        onCreate(db);
    }

    private void insertTestValues(){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("Name", "Sub-woofer");
        long success = db.insert("Dog", null, content);
        if(success == -1){
            Toast.makeText(context, "Error adding test dog", Toast.LENGTH_SHORT).show();
        }

        content.clear();
        content.put("FirstName", "Jack");
        content.put("LastName", "Sparrow");
        success = db.insert("Handler", null, content);
        if(success == -1){
            Toast.makeText(context, "Error adding test handler", Toast.LENGTH_SHORT).show();
        }
    }

    private int getMostRecentSessionID(){
        SQLiteDatabase db = getWritableDatabase();


        //TODO: Convert this from raw query to a more structured query
        Cursor results = db.rawQuery("SELECT * FROM Session ORDER BY SessionID DESC LIMIT 1", null);
        results.moveToFirst();
        return results.getInt(0);
    }

    public void beginSession(int dogID, int handlerID){
        SQLiteDatabase db = getWritableDatabase();

        if(duringSession()){
            return;
        }

        ContentValues content = new ContentValues();
        content.put("DogID", dogID);
        content.put("HandlerID", handlerID);
        db.insert("Session", null, content);

        sessionID = getMostRecentSessionID();
    }

    public void endSession(){
        if(!duringSession()){
            String string = "Please start a session before trying to close.";
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
            return;
        }

        sessionID = -1;
    }

    public boolean duringSession(){
        return sessionID != -1;
    }

    //TODO: Create all methods for retrieving and inserting data
}
