package com.example.eirik.tdt4240_project.drawing;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.eirik.tdt4240_project.R;

public class DrawingActivity extends Activity {

    private DrawingController drawingController;
    private TextView word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        this.drawingController = (DrawingController)findViewById(R.id.draw);
        this.word = (TextView)findViewById(R.id.word);
        startDrawingActivity();
    }

    private void startDrawingActivity() {
        // get a word from server?
        word.setText("Banan");
    }
}
