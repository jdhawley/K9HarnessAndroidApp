package com.example.android.k9harnessandroidapp.dto;

import com.example.android.k9harnessandroidapp.domain.User;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by grc on 12/3/17.
 */

public class UserDTO {
    private Long id;

    private String login;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activated = false;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }


    public UserDTO(Long id, String login, String firstName, String lastName,
                   String email) {

        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


    public boolean isActivated() {
        return activated;
    }



    @Override
    public String toString() {
        return "UserDTO{" +
                "login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                "}";
    }
}
