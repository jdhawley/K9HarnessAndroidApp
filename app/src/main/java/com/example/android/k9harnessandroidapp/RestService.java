package com.example.android.k9harnessandroidapp;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by George on 11/22/2017.
 */

public class RestService {
    private static final String dogHostURL = "localhost:8080/dog?name=%s";

    private static JSONObject getJSON(Context context, String dogName){
        try {

            //This fills in the dogs name using %s like c printf into the URL
            URL url = new URL(String.format(dogHostURL, dogName));

            //open URL connection with the URL we want to hit
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

            //Make a reader for the input stream we'll get from the server
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            //Parse all the lines of JSON adding it to the
            StringBuffer json = new StringBuffer(1024);
            String temp = "";
            while((temp = reader.readLine()) != null){
                json.append(temp).append("\n");
            }
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // success value of the get request
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        } catch (Exception e) {
            System.out.print("Houston we have a problem");
            return null;
        }
    }


}
