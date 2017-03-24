package com.example.eirik.tdt4240_project.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.eirik.tdt4240_project.models.Drawing;

public class DrawingController extends View {

    private Paint paint;
    private Path path;
    private Drawing drawing;

    public DrawingController(Context context, AttributeSet as){
        super(context, as);
        drawing = new Drawing();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        path = drawing.addStroke(event);

        //force view to draw
        invalidate();

        return true;
    }
}
