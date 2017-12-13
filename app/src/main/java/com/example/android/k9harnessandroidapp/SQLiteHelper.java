package com.example.android.k9harnessandroidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jon on 11/20/17 for the K9 Dog Collar Project.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static boolean TEST_DATABASE_MESSAGES = false;
    private static boolean isAcceptingData = false;

    private static SQLiteDatabase db;
    //private Context context;
    private static int sessionDogID = -1;
    private static int sessionID = -1;
    private static int sessionTick = -1;

    private static final String DATABASE_NAME = "DogHarness.db";
    private static final int VERSION = 1;

    public SQLiteHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION);
        //context = ctx;

        //TODO: Put this in it's own thread.
        db = getWritableDatabase();

        // Uncomment the line below to update the database in the event of a schema change.
//        onUpgrade(getWritableDatabase(), 1, 1);
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

        Cursor result = db.rawQuery("SELECT HandlerID FROM Handler ORDER BY HandlerID ASC", null);
        result.moveToFirst();

        addDog("TestDog", result.getInt(result.getColumnIndex("HandlerID")));
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

    private int getHandlerIDByDogID(int dogID){
        String query = "SELECT HandlerID FROM Dog WHERE DogID=? ORDER BY DogID ASC";
        Cursor result = db.rawQuery(query, new String[]{Integer.toString(dogID)});

        result.moveToFirst();
        return result.getInt(result.getColumnIndex("HandlerID"));
    }

    public static void beginSession(int dogID) {
        if (isAcceptingData) {
            Log.e("Session", "Attempted to start a new session before closing the old one.");
            return;
        }

        isAcceptingData = true;
        sessionDogID = dogID;
    }

    private void createNewSession(int dogID){
        int handlerID = getHandlerIDByDogID(dogID);

        ContentValues content = new ContentValues();
        content.put("DogID", dogID);
        content.put("HandlerID", handlerID);
        db.insert("Session", null, content);

        isAcceptingData = true;
        sessionID = getMostRecentSessionID();
        sessionTick = 0;

        if(TEST_DATABASE_MESSAGES){
            Log.d("Session", "Session " + sessionID + " has started with dog " + Integer.toString(dogID));
        }
    }

    public void endSession() {
        if (!isAcceptingData) {
            Log.e("Session", "Attempted to close a session while no session was occurring.");
            return;
        }

        isAcceptingData = false;
        sessionID = -1;
        sessionTick = -1;

        if(TEST_DATABASE_MESSAGES){
            Log.d("Session", "Session closed.");
        }
    }

    private int getMostRecentSessionID() {
        Cursor results = db.rawQuery("SELECT * FROM Session ORDER BY SessionID DESC LIMIT 1", null);
        results.moveToFirst();
        int mostRecentSessionID = results.getInt(0);
        results.close();
        return mostRecentSessionID;
    }

    public void addDog(String name, int handlerID) {
        ContentValues content = new ContentValues();

        content.put("Name", name);
        content.put("HandlerID", handlerID);
        long success = db.insert("Dog", null, content);
        if (success == -1) {
            Log.e("Database", "Error adding dog");
            return;
        }
        if(TEST_DATABASE_MESSAGES){
            Cursor result = db.rawQuery("SELECT DogID FROM Dog ORDER BY DogID DESC LIMIT 1", null);
            result.moveToFirst();
            int dogID = result.getInt(0);
            result.close();
            Log.d("Database", "Dog " + Integer.toString(dogID) + " added successfully");
        }
    }

    public void addHandler(String firstName, String lastName) {
        ContentValues content = new ContentValues();

        content.put("FirstName", firstName);
        content.put("LastName", lastName);
        long success = db.insert("Handler", null, content);
        if (success == -1) {
            Log.e("Database", "Error adding handler");
            return;
        }
        if(TEST_DATABASE_MESSAGES){
            Cursor result = db.rawQuery("SELECT HandlerID FROM Handler ORDER BY HandlerID DESC LIMIT 1", null);
            result.moveToFirst();
            int handlerID = result.getInt(0);
            result.close();
            Log.d("Database", "Handler " + Integer.toString(handlerID) + " added successfully");
        }
    }

    public void addDataTick(int hr, int rr, int ct, int amt, int abt) {
        if(!isAcceptingData){
            Log.d("DogOverview", "Attempted to add data tick without open session");
            return;
        }

        if(sessionID == -1){
            //TODO: Update this so it receives a
            createNewSession(sessionDogID);
        }

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
            Log.e("Database", "Error adding " + data);
            return;
        }

        sessionTick += 1;

        if(TEST_DATABASE_MESSAGES){
            String data = hr + ":" + rr + ":" + ct + ":" + amt + ":" + abt + "#";
            Log.d("Database", data + " inserted successfully");
        }
    }

    public Cursor getAllAmbientTemps(){
        String query = "SELECT AmbientTemperature FROM SessionTick WHERE SessionID=?";
        return db.rawQuery(query, new String[]{ Integer.toString(sessionID) });
    }

    public Cursor getAllSessionData(){
        if (!isAcceptingData) {
            Log.e("Database", "Attempted getAllSessionData with closed session");
            return null;
        }

        String query = "SELECT SessionTick, HeartRate, RespiratoryRate, CoreTemperature, " +
                "AbdominalTemperature FROM SessionTick WHERE SessionID=? ORDER BY SessionTick ASC";
        return db.rawQuery(query, new String[]{ Integer.toString(sessionID) });
    }

    public boolean duringSession() {
        return isAcceptingData;
    }

    public Cursor getHistoricalHeartRateData(int dogID) {
        String query = "SELECT SessionID, HeartRate FROM Session S INNER JOIN SessionTick ST" +
                "ON S.SessionID=ST.SessionID WHERE S.DogID=?";
        return db.rawQuery(query, new String[]{Integer.toString(dogID)});
    }
}
