package com.example.eirik.tdt4240_project.drawing;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eirik.tdt4240_project.R;

public class DrawingActivity extends Activity {

    private DrawingController drawingController;
    private TextView wordField;
    private Button sendButton;

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

        startDrawingActivity();
    }

    private void startDrawingActivity() {
        // get a word from server
        wordField.setText("Banan");
    }

    private void stopDrawingActivity() {
        // send drawing to server
    }
}
