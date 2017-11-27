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
    private boolean TEST_DATABASE_MESSAGES = true;

    private Context context;
    private int sessionID = -1;
    private int sessionTick;

    private static final String DATABASE_NAME = "DogHarness.db";
    private static final int VERSION = 1;

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
                "SessionID INTEGER NOT NULL," +
                "SessionTick INTEGER NOT NULL," +
                "HeartRate INTEGER NOT NULL," +
                "RespiratoryRate INTEGER NOT NULL," +
                "CoreTemperature INTEGER NOT NULL," +
                "AbdominalTemperature INTEGER NOT NULL," +
                "PRIMARY KEY (SessionID, SessionTick)," +
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

    private void insertTestValues() {
        addDog("TestDog");

        addHandler("Test", "Handler");
    }

    public void beginSession(int dogID, int handlerID) {
        SQLiteDatabase db = getWritableDatabase();

        if (duringSession()) {
            return;
        }

        ContentValues content = new ContentValues();
        content.put("DogID", dogID);
        content.put("HandlerID", handlerID);
        db.insert("Session", null, content);

        sessionID = getMostRecentSessionID();
        sessionTick = 0;
    }

    public void endSession() {
        if (!duringSession()) {
            String string = "Please start a session before trying to close.";
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
            return;
        }

        sessionID = -1;
        sessionTick = -1;
    }

    public boolean duringSession() {
        return sessionID != -1;
    }

    private int getMostRecentSessionID() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor results = db.rawQuery("SELECT * FROM Session ORDER BY SessionID DESC LIMIT 1", null);
        results.moveToFirst();
        int mostRecentSessionID = results.getInt(0);
        results.close();
        return mostRecentSessionID;
    }

    //TODO: Check to make sure a session is open for the relevant functions

    public void addDog(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("Name", name);
        long success = db.insert("Dog", null, content);
        if (success == -1) {
            Toast.makeText(context, "Error adding dog", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TEST_DATABASE_MESSAGES){
            Toast.makeText(context, "Dog added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void addHandler(String firstName, String lastName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("FirstName", firstName);
        content.put("LastName", lastName);
        long success = db.insert("Dog", null, content);
        if (success == -1) {
            Toast.makeText(context, "Error adding handler", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TEST_DATABASE_MESSAGES){
            Toast.makeText(context, "Handler added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllDogs(){
        SQLiteDatabase db = getReadableDatabase();

        //TODO: Convert to more secure query
        return db.rawQuery("SELECT * FROM Dog ORDER BY DogID ASC", null);
    }

    public Cursor getAllHandlers(){
        SQLiteDatabase db = getReadableDatabase();

        //TODO: Convert to more secure query
        return db.rawQuery("SELECT * FROM Handler ORDER BY HandlerID ASC", null);
    }

    public void removeDog(int dogID){
        SQLiteDatabase db = getWritableDatabase();

        String table = "Dog";
        String whereClause = "DogID=?";
        String[] whereArgs = new String[] { Integer.toString(dogID) };
        int rowsAffected = db.delete(table, whereClause, whereArgs);

        if(TEST_DATABASE_MESSAGES){
            Toast.makeText(context, Integer.toString(rowsAffected) + " rows deleted from the dog table", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeHandler(int handlerID){
        SQLiteDatabase db = getWritableDatabase();

        String table = "Handler";
        String whereClause = "HandlerID=?";
        String[] whereArgs = new String[] { Integer.toString(handlerID) };
        int rowsAffected = db.delete(table, whereClause, whereArgs);

        if(TEST_DATABASE_MESSAGES){
            Toast.makeText(context, Integer.toString(rowsAffected) + " rows deleted from the handler table", Toast.LENGTH_SHORT).show();
        }
    }

    public void addDataTick(int heartRate, int respiratoryRate, int coreTemp, int abdTemp) {
        if(sessionID == -1){
            Toast.makeText(context, "Can't add a data tick without opening a session first.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("SessionID", sessionID);
        content.put("SessionTick", sessionTick);
        content.put("HeartRate", heartRate);
        content.put("RespiratoryRate", respiratoryRate);
        content.put("CoreTemperature", coreTemp);
        content.put("AbdominalTemperature", abdTemp);
        long success = db.insert("SessionTick", null, content);

        if (success == -1) {
            Toast.makeText(context, "Error adding data point", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TEST_DATABASE_MESSAGES){
            Toast.makeText(context, "Data tick added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
