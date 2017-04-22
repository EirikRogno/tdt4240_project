package com.example.eirik.tdt4240_project.drawing;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ninagraneggen on 26.03.2017.
 */

public class Eraser implements DrawingTool {

    private Paint paint;

    Eraser() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public Paint getTool() {
        return paint;
    }

    @Override
    public DrawingTool changeColor(int color) {
        return this; // not possible to change color for eraser
    }

    @Override
    public DrawingTool changeSize(int size) {
        return this; // not possible to change size for eraser
    }
}
