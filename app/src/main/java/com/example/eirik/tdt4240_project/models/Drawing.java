package com.example.eirik.tdt4240_project.models;

import android.graphics.Path;
import android.view.MotionEvent;

public class Drawing {

    private Path path;
    private float x;
    private float y;

    public Drawing() {
        path = new Path(); // follows the path of drawing
    }

    public Path getDrawing() {
        return path;
    }

    public Path addStroke(MotionEvent event) {
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
        return path;
    }
}
