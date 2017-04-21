package com.example.eirik.tdt4240_project.logIn;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.mainMenu.MainMenuActivity;


public class LogInActivity extends AppCompatActivity {

    EditText usernameInput;
    LogInController controller = new LogInController();

    public void goToMainMenu(){
        startActivity(new Intent(LogInActivity.this, MainMenuActivity.class));
    }

    public void displayMessage(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public void logIn(View v){
        controller.getUser(usernameInput.getText().toString(), this);
    }

    public void newAccount(View v){
        controller.createUser(usernameInput.getText().toString(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameInput = (EditText)findViewById(R.id.txtUsername);

    }
}
