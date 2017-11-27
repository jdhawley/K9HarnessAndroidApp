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
    private boolean TEST_DATABASE_MESSAGES = false;

    private Context context;
    private int sessionID = -1;
    private int sessionTick;

    private static final String DATABASE_NAME = "DogHarness.db";
    private static final int VERSION = 1;

    public SQLiteHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION);
        context = ctx;

        // Uncomment the line below to update the database in the event of a schema change.
        // onUpgrade(getWritableDatabase(), 1, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Handler(" +
                "HandlerID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FirstName Text NOT NULL," +
                "LastName Text NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS Dog(" +
                "DogID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HandlerID INTEGER NOT NULL," +
                "Name TEXT NOT NULL," +
                "FOREIGN KEY (HandlerID) REFERENCES Handler(HandlerID))");

        db.execSQL("CREATE TABLE IF NOT EXISTS Session(" +
                "SessionID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DogID INTEGER NOT NULL," +
                "HandlerID INTEGER NOT NULL," +
                "FOREIGN KEY (DogID) REFERENCES Dog(DogID))");

        db.execSQL("CREATE TABLE IF NOT EXISTS SessionTick(" +
                "SessionID INTEGER NOT NULL," +
                "SessionTick INTEGER NOT NULL," +
                "HeartRate INTEGER NOT NULL," +
                "RespiratoryRate INTEGER NOT NULL," +
                "CoreTemperature INTEGER NOT NULL," +
                "AmbientTemperature INTEGER NOT NULL," +
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
        insertTestValues();
    }

    private void insertTestValues() {
        addHandler("Test", "Handler");

        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery("SELECT HandlerID FROM Handler ORDER BY HandlerID ASC", null);
        result.moveToFirst();

        addDog("TestDog", result.getInt(0));
        result.close();

        beginSession(1);
        addDataTick(87, 18,103, 103, 103);
        endSession();
        beginSession(1);
        addDataTick(86, 27,103, 103,  101);
        endSession();
        beginSession(1);
        addDataTick(74, 10,101, 102,  101);
        endSession();
    }

    public void beginSession(int dogID) {
        if (duringSession()) {
            Toast.makeText(context, "Please close the current session before starting a new one.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT HandlerID FROM Dog WHERE DogID=? ORDER BY DogID ASC";
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(dogID)});

        result.moveToFirst();
        int handlerID = result.getInt(0);
        result.close();

        ContentValues content = new ContentValues();
        content.put("DogID", dogID);
        content.put("HandlerID", handlerID);
        db.insert("Session", null, content);

        sessionID = getMostRecentSessionID();
        sessionTick = 1;

        if(TEST_DATABASE_MESSAGES){
            Toast.makeText(context, "Session " + sessionID + " has started with dog " + Integer.toString(dogID), Toast.LENGTH_SHORT).show();
        }
    }

    public void endSession() {
        if (!duringSession()) {
            String string = "Please start a session before trying to close.";
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
            return;
        }

        sessionID = -1;
        sessionTick = -1;

        if(TEST_DATABASE_MESSAGES){
            Toast.makeText(context, "The current session has been closed.", Toast.LENGTH_SHORT).show();
        }
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

    public void addDog(String name, int handlerID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("Name", name);
        content.put("HandlerID", handlerID);
        long success = db.insert("Dog", null, content);
        if (success == -1) {
            Toast.makeText(context, "Error adding dog", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TEST_DATABASE_MESSAGES){
            Cursor result = db.rawQuery("SELECT DogID FROM Dog ORDER BY DogID DESC LIMIT 1", null);
            result.moveToFirst();
            int dogID = result.getInt(0);
            result.close();
            Toast.makeText(context, "Dog " + Integer.toString(dogID) + " added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void addHandler(String firstName, String lastName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("FirstName", firstName);
        content.put("LastName", lastName);
        long success = db.insert("Handler", null, content);
        if (success == -1) {
            Toast.makeText(context, "Error adding handler", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TEST_DATABASE_MESSAGES){
            Cursor result = db.rawQuery("SELECT HandlerID FROM Handler ORDER BY HandlerID DESC LIMIT 1", null);
            result.moveToFirst();
            int handlerID = result.getInt(0);
            result.close();
            Toast.makeText(context, "Handler " + Integer.toString(handlerID) + " added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllDogs(){
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery("SELECT * FROM Dog ORDER BY DogID ASC", null);
    }

    public Cursor getAllHandlers(){
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery("SELECT * FROM Handler ORDER BY HandlerID ASC", null);
    }

    public void removeDog(int dogID){
        SQLiteDatabase db = getWritableDatabase();

        String table = "Dog";
        String whereClause = "DogID=?";
        String[] whereArgs = new String[] { Integer.toString(dogID) };
        int rowsAffected = db.delete(table, whereClause, whereArgs);

        if(TEST_DATABASE_MESSAGES){
            if(rowsAffected == 1){
                Toast.makeText(context, "Dog " + Integer.toString(dogID) + " removed from the database", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "A dog with ID: " + Integer.toString(dogID) + " was not found in the database.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void removeHandler(int handlerID){
        SQLiteDatabase db = getWritableDatabase();

        String table = "Handler";
        String whereClause = "HandlerID=?";
        String[] whereArgs = new String[] { Integer.toString(handlerID) };
        int rowsAffected = db.delete(table, whereClause, whereArgs);

        if(TEST_DATABASE_MESSAGES){
            if(rowsAffected == 1){
                Toast.makeText(context, "Handler " + Integer.toString(handlerID) + " removed from the database", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "A handler with ID: " + Integer.toString(handlerID) + " was not found in the database.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addDataTick(int hr, int rr, int ct, int amt, int abt) {
        if(sessionID == -1){
            Toast.makeText(context, "Can't add a data tick without opening a session first.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put("SessionID", sessionID);
        content.put("SessionTick", sessionTick);
        content.put("HeartRate", hr);
        content.put("RespiratoryRate", rr);
        content.put("CoreTemperature", ct);
        content.put("AmbientTemperature", amt);
        content.put("AbdominalTemperature", abt);
        long success = db.insert("SessionTick", null, content);

        if (success == -1) {
            String data = hr + ":" + rr + ":" + ct + ":" + amt + ":" + abt + "#";
            Toast.makeText(context, "Error adding " + data, Toast.LENGTH_SHORT).show();
            return;
        }

        sessionTick += 1;

        if(TEST_DATABASE_MESSAGES){
            String data = hr + ":" + rr + ":" + ct + ":" + amt + ":" + abt + "#";
            Toast.makeText(context, data + " inserted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllAmbientTemps(){
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT AmbientTemperature FROM SessionTick WHERE SessionID=?";
        return db.rawQuery(query, new String[]{ Integer.toString(sessionID) });
    }

    public Cursor getAllSessionData(){
        SQLiteDatabase db = getWritableDatabase();

        return db.rawQuery("SELECT HeartRate, RespiratoryRate, CoreTemperature, AmbientTemperature, AbdominalTemperature " +
                "FROM SessionTick " +
                "WHERE SessionID=?", new String[]{ Integer.toString(sessionID) });
    }
}
