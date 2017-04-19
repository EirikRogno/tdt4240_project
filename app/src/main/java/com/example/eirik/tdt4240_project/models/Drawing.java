package com.example.eirik.tdt4240_project.models;

import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import com.example.eirik.tdt4240_project.drawing.SerializablePath;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class Drawing {

    private SerializablePath path;
    private float x;
    private float y;
    private Map<Path, Paint> strokes; // drawing gets new stroke whenever the user lifts the brush, makes it possible for user to regret

    public Drawing() {
        path = new SerializablePath(); // follows the path of drawing
        strokes = new LinkedHashMap<Path, Paint>();
    }

    public Map<Path, Paint> getDrawing() { // returns finished strokes
        return strokes;
    }

    public Path addStroke(MotionEvent event, Paint paint) { // returns current stroke before it's finished
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
            strokes.put(path, paint);
            path = new SerializablePath();
        }
        return path;
    }



    /*
    * The following functions allows serializing the drawing
    * to a string, and then deserialize it.
    *
    * Fileformat is that the string will be [paint];#;
    *
    *
    * */
    @Override
    public String toString(){


        return "";
    }

    public static Drawing fromString(String serializedDrawing){
        return new Drawing();
    }
}
