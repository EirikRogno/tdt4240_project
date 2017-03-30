package com.example.eirik.tdt4240_project.login;

import android.widget.EditText;

import java.util.ArrayList;

public class LogInController {

    //burde kanskje bruke liste med brukernavn fra server?
    private ArrayList<EditText> usernameList;

    EditText txtUsername = (EditText)findViewById(android.support.design.R.id.username);

    public Boolean isValidUsername(EditText text){
        String username = text.getText().toString();
        if(username.length() > 0){ //check if container for username is not empty
            if(usernameList.contains(username){
                return true;
            }
        }
        return false;
    }

    //need to make a new xml for new acc view
    public Boolean isNewAccValid(EditText newaccName){

    }
}
