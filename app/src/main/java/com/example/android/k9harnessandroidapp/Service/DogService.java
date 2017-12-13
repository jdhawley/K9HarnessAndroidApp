package com.example.android.k9harnessandroidapp.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.k9harnessandroidapp.domain.Dog;
import com.example.android.k9harnessandroidapp.domain.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by George on 12/13/2017.
 */

public class DogService implements Runnable {
    private Context mContext;
    private Dog mDog;

    public DogService(Context c, Dog d){
        this.mContext = c;
        this.mDog = d;
    }

    @Override
    public void run() {
        new DogSyncTask(this.mContext).execute(this.mDog);
    }



    private class DogSyncTask extends AsyncTask<Dog, Dog, Boolean> {

        private String mEmail;
        private String id_token;
        private Context mContext;
        private int dogFlag = 0;
        SharedPreferences sharedPreferences;

        DogSyncTask(Context c) {
            mContext = c;
        }

        @Override
        protected void onPreExecute(){
            sharedPreferences = mContext.getSharedPreferences("AccountSettings", MODE_PRIVATE);
            mEmail = sharedPreferences.getString("currentUsername", null);
            id_token = sharedPreferences.getString("id_token", null);
        }

        @Override
        protected Boolean doInBackground(Dog... dogs) {
            final String url = "https://k9backend.herokuapp.com/api/dogs";
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + id_token);
                HttpEntity<String> entity = new HttpEntity<String>(headers);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<Dog> dogSubmission  = restTemplate.exchange(url, HttpMethod.POST, entity, Dog.class, dogs);

                //If the dog exists we need to create a new dog
                if(dogSubmission.getStatusCode().value() == 400) {
                    dogSubmission  = restTemplate.exchange(url, HttpMethod.PUT, entity, Dog.class, dogs);
                    if(dogSubmission.getStatusCode().value() == 400) {
                        return false;
                    }
                    return true;
                }

            } catch(Exception e) {
                Log.e("DOGSERVICE: ", e.getMessage());
                return false;
            }
            return false;
        }
    }
}
