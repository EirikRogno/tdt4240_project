package com.example.eirik.tdt4240_project.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.mainmenu.MainMenuActivity;


public class LogInActivity extends AppCompatActivity {

    /*
    public void inputButtonOnClick(View v){
        if(v.getId() == R.id.logInButton){
            Intent i = new Intent(LogInActivity.this, MainMenuActivity.class);
            startActivity(i);
        }
    }
    */

    Button logInButton;
    Button newAccountButton;
    EditText usernameInput;


    public void inputButtonOnClick(View v){
        if(v.getId() == R.id.btnLogIn){
            logInButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    startActivity(new Intent(LogInActivity.this, MainMenuActivity.class));
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInButton = (Button)findViewById(R.id.btnLogIn);
        newAccountButton = (Button)findViewById(R.id.btnNewAcc);

        usernameInput = (EditText)findViewById(R.id.txtUsername);

        logInButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        Log.v("Username", usernameInput.getText().toString());
                    }
                });
    }
}
