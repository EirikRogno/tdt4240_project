package com.example.eirik.tdt4240_project.drawing;

import android.graphics.Paint;

/**
 * Created by ninagraneggen on 26.03.2017.
 */

public interface DrawingTool {
    Paint getTool();
    DrawingTool changeColor(int color); // returns NEW drawing tool with another color
    DrawingTool changeSize(int size); // returns NEW drawing tool with another size
}