package com.example.eirik.tdt4240_project.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingController extends View {

    private Paint paint;
    private Path path;
    private float x;
    private float y;

    public DrawingController(Context context, AttributeSet as){
        super(context, as);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path(); // follows the path of drawing
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // touch coordinates
        x = event.getX();
        y = event.getY();

        // update path
        if (event.getAction() == 0) { // ACTION_DOWN
            path.moveTo(x,y);
        } else if (event.getAction() == 2) {// ACTION_MOVE
            path.lineTo(x,y);
        } else if (event.getAction() == 1) {// ACTION_UP
            path.lineTo(x,y);
        }

        //force view to draw
        invalidate();

        return true;
    }
}
