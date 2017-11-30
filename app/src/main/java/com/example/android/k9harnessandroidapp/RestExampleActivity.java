package com.example.android.k9harnessandroidapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class RestExampleActivity extends AppCompatActivity {
    private final static String API_URI_STRING = "https://10.0.2.2:8080/api/authenticate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rest_example);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_rest_example, container, false);
            return rootView;
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Dog> {
        @Override
        protected Dog doInBackground(Void... params) {
            try {
                //To send data we package it as a linkedmultivaluemap
                RestTemplate restTemplate = new RestTemplate();

                MultiValueMap<String, String> login = new LinkedMultiValueMap<String, String>();
                login.add("username", "admin");
                login.add("password", "admin");
                URI targetUrl = UriComponentsBuilder.fromUriString("https://10.0.2.2:8080/api/authenticate").build().encode().toUri();

                final ResponseEntity<String> authResponse = restTemplate.postForEntity(targetUrl, login, String.class);
                JSONObject jsonObject = new JSONObject(authResponse.getBody());
                JSONObject attributes = jsonObject.getJSONObject("@attributes");
                String status = attributes.getString("stat");

                final String url = "https://10.0.2.2:8080/api/dogs/1001";
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Dog dog = restTemplate.getForObject(url, Dog.class);
                return dog;
            } catch (Exception e) {
                Log.e("RESTExample", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Dog dog) {
            TextView dogIdText = (TextView) findViewById(R.id.dog_value);
            dogIdText.setText(dog.getName());
        }

    }

}
