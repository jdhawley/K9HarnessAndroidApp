package com.example.android.k9harnessandroidapp;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.k9harnessandroidapp.Service.AccountService;
import com.example.android.k9harnessandroidapp.domain.ManagedUserVM;
import com.example.android.k9harnessandroidapp.domain.User;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class AccountRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_register);



        Button saveChangesButton = (Button) findViewById(R.id.button_submit_account_settings_register);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String accountUrl = "https://k9backend.herokuapp.com/api/register";
                //remove keyboard on button click
                try {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                catch (Exception e) {

                }

                EditText email = (EditText) findViewById(R.id.email);
                EditText firstName = (EditText) findViewById(R.id.first_name);
                EditText lastName = (EditText) findViewById(R.id.last_name);
                EditText password = (EditText) findViewById(R.id.password_field);
                EditText dogName = (EditText) findViewById(R.id.dog_name);

                String em = email.getText().toString();
                String fn = firstName.getText().toString();
                String ln = lastName.getText().toString();
                String pw = password.getText().toString();
                String doge = dogName.getText().toString();

                SharedPreferences prefs = getSharedPreferences("AccountSettings", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("dogName", doge);
                editor.apply();

                try{
                    User u = new ManagedUserVM();
                    u.setEmail(em);
                    u.setPassword(pw);
//                    u.setFirstName(fn);
//                    u.setLastName(ln);
                    u.setLogin(em);
                    u.setLastName("en");
                    new AccountTask(AccountRegister.this).execute(u);
                } catch (Exception e){
                    Log.e("EXCEPTION:", e.toString());
                }
            }
        });
    }
    private class AccountTask extends AsyncTask<User, Void, Boolean> {
        private String username;
        private String password;
        private String email;
        private SharedPreferences sharedPreferences;
        private User u;
        private SQLiteHelper helper;

        public AccountTask(Context c) {
            sharedPreferences = c.getSharedPreferences("AccountSettings", MODE_PRIVATE);
            this.helper = new SQLiteHelper(c);
        }
        @Override
        protected Boolean doInBackground(User... users) {
            final String url = "https://192.168.1.5:9000/api/users";
            try {
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>(headers);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<User> dogSubmission  = restTemplate.exchange(url, HttpMethod.POST, entity, User.class, users);

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
