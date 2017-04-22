package com.example.eirik.tdt4240_project.guessing;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.drawing.DrawingController;
import com.example.eirik.tdt4240_project.mainmenu.MainMenuActivity;

public class GuessingActivity extends AppCompatActivity {

    private EditText guessInput;
    private Button button;
    private GuessingController controller = new GuessingController();
    private DrawingController drawingController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guessing);
        guessInput = (EditText)findViewById(R.id.txtWordGuess);
        button = (Button) findViewById(R.id.guessButton);
        drawingController =  (DrawingController)findViewById(R.id.drawing);
        drawingController.setDrawingEnable(false);
        drawingController.getRemoteDrawing(AppController.getInstance().getCurrentMatch().getId());
    }

    public void makeGuess(View view){
        controller.correctGuess(guessInput.getText().toString(), this);
    }

    public void goToMainMenu(){

        startActivity(new Intent(GuessingActivity.this, MainMenuActivity.class));
    }

    public void displayMessage(final String title, String message, String buttonText){
        AlertDialog alertDialog = new AlertDialog.Builder(GuessingActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, buttonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(title.equals("Correct!")){
                            goToMainMenu();
                        }
                    }
                });
        alertDialog.show();
    }
}