package com.example.eirik.tdt4240_project.newGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.mainmenu.MainMenuActivity;

public class NewGameActivity extends AppCompatActivity {

    NewGameController controller = new NewGameController();
    EditText usernameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        usernameInput = (EditText)findViewById(R.id.txtUsername);
    }

    public void createNewGame(View v){
        controller.createNewGame(usernameInput.getText().toString(), this);
    }

    public void returnToMainMenu(){
        startActivity(new Intent(NewGameActivity.this, MainMenuActivity.class));
    }

    public void displayMessage(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(NewGameActivity.this).create();
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
}
