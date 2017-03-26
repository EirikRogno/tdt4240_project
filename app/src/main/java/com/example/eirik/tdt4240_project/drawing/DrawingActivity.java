package com.example.eirik.tdt4240_project.drawing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eirik.tdt4240_project.R;

public class DrawingActivity extends Activity {

    private DrawingController drawingController;
    private TextView wordField;
    private Button sendButton;
    private Button colorButton;
    private ColorPopup colors;
    private TextView currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        this.drawingController = (DrawingController)findViewById(R.id.draw);
        this.wordField = (TextView)findViewById(R.id.word);

        this.sendButton = (Button)findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopDrawingActivity();
            }
        });

        this.colorButton = (Button)findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openColorPopup();
            }
        });

        this.currentColor = (TextView)findViewById(R.id.currentColor);

        startDrawingActivity();
    }

    private void openColorPopup() {
        colors = new ColorPopup(this);
        colors.showDialog(this);
    }

    public void changeColor(int color) { // user wants to change color from colorPopup-window, notify controller
        drawingController.changeColor(color, currentColor);
    }

    private void startDrawingActivity() {
        // get a word from server
        wordField.setText("Banan");
    }

    private void stopDrawingActivity() {
        // send drawing to server
    }

}
