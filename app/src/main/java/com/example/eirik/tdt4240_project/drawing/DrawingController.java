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

    private Path currentPath;
    private Drawing drawing;
    private Map<Path, Paint> strokes;
    private DrawingTool currentTool;

    public DrawingController(Context context, AttributeSet as){
        super(context, as);
        drawing = new Drawing();
        currentPath = new Path();
        currentTool = new Pen(Color.BLACK, 5);
    }

    @Override
    public void onDraw(Canvas canvas) {

        // draw finished strokes
        strokes = drawing.getDrawing();
        for (Path p : strokes.keySet()) {
            canvas.drawPath(p, strokes.get(p));
        }

        // draw current stroke
        canvas.drawPath(currentPath,currentTool.getTool());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentPath = drawing.addStroke(event, currentTool.getTool());

        //force view to draw
        invalidate();

        return true;
    }

    public void changeColor(int color, TextView currentColorView) { // makes new tool
        currentColorView.setBackgroundColor(color); // in GUI
        currentTool = currentTool.changeColor(color);
    }

    public void changeStrokeWidth(int width) {
        currentTool = currentTool.changeSize(width);
    }

    public void undoLastStroke() {
        strokes = drawing.undoLastStroke();

        invalidate();
    }
}
