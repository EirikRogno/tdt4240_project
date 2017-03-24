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

import java.util.Map;

public class DrawingController extends View {

    private Paint paint;
    private Path currentPath;
    private Drawing drawing;
    private Map<Path, Paint> strokes;

    public DrawingController(Context context, AttributeSet as){
        super(context, as);
        drawing = new Drawing();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        currentPath = new Path();
    }

    @Override
    public void onDraw(Canvas canvas) {
        // draw current stroke
        canvas.drawPath(currentPath,paint);

        // draw finished strokes
        strokes = drawing.getDrawing();
        for (Path p : strokes.keySet()) {
            canvas.drawPath(p, strokes.get(p));
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentPath = drawing.addStroke(event, paint);

        //force view to draw
        invalidate();

        return true;
    }
}
