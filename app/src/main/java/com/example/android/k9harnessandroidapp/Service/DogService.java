package com.example.android.k9harnessandroidapp.Service;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.android.k9harnessandroidapp.domain.Dog;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * Created by George on 12/13/2017.
 */

public class DogService implements Runnable {


    @Override
    public void run() {
        new DogSyncTask().execute();
    }

    private class DogSyncTask extends AsyncTask<URL, Void, Boolean> {

        @Override
        protected Boolean doInBackground(URL... urls) {
            try {
                final String url = "https://10.122.162.72:9000/api/dogs";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setRequestFactory();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Dog d = new Dog("George", 100, 100, 100, 100, 100, 100,
                    100, 100);
                Dog returned = restTemplate.postForObject(url, d, Dog.class);

                Log.e("DogServ", returned.toString());
                if(returned != null){
                    return true;
                }
                return false;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                return false;
            }
        }

//        @Override
//        protected void onPostExecute(Dog greeting) {
//            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
//            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
//            greetingIdText.setText(greeting.getId());
//            greetingContentText.setText(greeting.getContent());
//        }

    }
}
