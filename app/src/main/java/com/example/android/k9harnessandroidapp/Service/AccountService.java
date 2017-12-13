package com.example.android.k9harnessandroidapp.Service;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.k9harnessandroidapp.domain.Dog;
import com.example.android.k9harnessandroidapp.domain.ManagedUserVM;
import com.example.android.k9harnessandroidapp.domain.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by grc on 12/13/17.
 */

public class AccountService {
    private String username;
    private String password;
    private String email;
    private SharedPreferences sharedPreferences;
    private User u;

    public AccountService(Context c, String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        sharedPreferences = c.getSharedPreferences("AccountSettings", MODE_PRIVATE);

        u = new ManagedUserVM();
        u.setEmail(this.email);
    }


    private class AccountTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            final String url = "https://k9backend.herokuapp.com/api/register";
            try {
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>(headers);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<ManagedUserVM> dogSubmission  = restTemplate.exchange(url, HttpMethod.POST, entity, ManagedUserVM.class, users);

                //If the dog exists we need to create a new dog
                if(dogSubmission.getStatusCode().value() == 400) {
//                    dogSubmission  = restTemplate.exchange(url, HttpMethod.PUT, entity, Dog.class, users);
                    if(dogSubmission.getStatusCode().value() == 400) {
                        return false;
                    }
                    return true;
                }
            } catch(Exception e) {
                Log.e("ACCOUNTSERVICE: ", e.getMessage());
                return false;
            }
            return false;
        }
    }
}
