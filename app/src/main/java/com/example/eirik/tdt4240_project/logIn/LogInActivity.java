package com.example.eirik.tdt4240_project.logIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.mainMenu.MainMenuActivity;


public class LogInActivity extends AppCompatActivity {

    /*
    public void inputButtonOnClick(View v){
        if(v.getId() == R.id.logInButton){
            Intent i = new Intent(LogInActivity.this, MainMenuActivity.class);
            startActivity(i);
        }
    }
    */

    Button logInButton = (Button) findViewById(R.id.btnLogIn);

    public void inputButtionOnClick(View v){
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
    }
}
