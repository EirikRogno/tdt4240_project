package com.example.eirik.tdt4240_project.drawing;

import android.graphics.Paint;

/**
 * Created by ninagraneggen on 26.03.2017.
 */

public class Pen implements DrawingTool {

    private Paint paint;
    private int color;
    private int width;

    Pen(int color, int width) {
        paint = new Paint();
        this.color = color;
        this.width = width;
        paint.setColor(color);
        paint.setStrokeWidth(width);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public Paint getTool() {
        return paint;
    }

    @Override
    public DrawingTool changeColor(int color) {
        return new Pen(color,width);
    }

    @Override
    public DrawingTool changeSize(int size) {
        return new Pen(color, size);
    }
}
