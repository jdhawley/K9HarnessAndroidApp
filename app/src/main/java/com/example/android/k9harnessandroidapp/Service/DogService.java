package com.example.android.k9harnessandroidapp.Service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.android.k9harnessandroidapp.domain.Dog;
import com.example.android.k9harnessandroidapp.domain.LoginVM;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * Created by George on 12/13/2017.
 */

public class DogService implements Runnable {


    @Override
    public void run() {

    }



//    private class DogSyncTask extends AsyncTask<URL, Void, Boolean> {
//
//        @Override
//        @JsonIgnoreProperties
//        protected Boolean doInBackground(URL... urls) {
//            try {
//
//            } catch (Exception e) {
//                Log.e("MainActivity", e.getMessage(), e);
//                return false;
//            }
//        }
//
////        @Override
////        protected void onPostExecute(Dog greeting) {
////            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
////            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
////            greetingIdText.setText(greeting.getId());
////            greetingContentText.setText(greeting.getContent());
////        }
//
//    }
}
