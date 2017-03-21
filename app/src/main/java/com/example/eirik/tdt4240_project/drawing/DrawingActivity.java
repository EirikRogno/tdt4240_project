package com.example.eirik.tdt4240_project.drawing;

import android.app.Activity;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.eirik.tdt4240_project.R;

public class DrawingActivity extends Activity {

    private DrawingController drawingController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        this.drawingController = (DrawingController)findViewById(R.id.draw);
    }
}
