package com.example.eirik.tdt4240_project.guessing;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eirik.tdt4240_project.R;

public class GuessingActivity extends AppCompatActivity {

    private EditText guessInput;
    private Button button;
    GuessingController controller = new GuessingController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessing);
        guessInput = (EditText)findViewById(R.id.txtWordGuess);
        button = (Button) findViewById(R.id.guessButton);
    }

    public void goToNextState(){
        // TODO: move the game to next state;
    }

    public void makeGuess(View view){
        controller.correctGuess(guessInput.getText().toString(), this);
    }

    public void displayMessage(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(GuessingActivity.this).create();
        alertDialog.setTitle("Wrong word");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Try again!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}