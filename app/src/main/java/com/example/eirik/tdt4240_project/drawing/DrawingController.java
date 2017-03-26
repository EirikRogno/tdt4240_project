package com.example.eirik.tdt4240_project.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.eirik.tdt4240_project.models.Drawing;

import java.util.Map;

public class DrawingController extends View {

    private Paint paint;
    private Path currentPath;
    private Drawing drawing;
    private Map<Path, Paint> strokes;
    int currentColor;
    int currentWidth;

    public DrawingController(Context context, AttributeSet as){
        super(context, as);
        drawing = new Drawing();
        currentColor = Color.BLACK;
        currentWidth = 5;
        paint = makeNewPaint();
        currentPath = new Path();
    }

    @Override
    public void onDraw(Canvas canvas) {

        // draw finished strokes
        strokes = drawing.getDrawing();
        for (Path p : strokes.keySet()) {
            canvas.drawPath(p, strokes.get(p));
        }

        // draw current stroke
        canvas.drawPath(currentPath,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentPath = drawing.addStroke(event, paint);

        //force view to draw
        invalidate();

        return true;
    }

    public void changeColor(int color, TextView currentColor) {
        this.currentColor = color;
        currentColor.setBackgroundColor(color);
        paint = makeNewPaint();
    }

    public void changeStrokeWidth(int width) {
        currentWidth = width;
        paint = makeNewPaint();
    }

    private Paint makeNewPaint() {
        Paint paint = new Paint();
        paint.setColor(currentColor);
        paint.setStrokeWidth(currentWidth);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }
}
