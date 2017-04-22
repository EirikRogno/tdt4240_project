package com.example.eirik.tdt4240_project.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.models.Drawing;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class DrawingController extends View {

    private Path currentPath;
    private Drawing drawing;
    private Map<SerializablePath, Paint> strokes;
    private DrawingTool currentTool;

    AppController appController = AppController.getInstance();

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
        //currentColorView.setBackgroundColor(color); // in GUI
        GradientDrawable background = (GradientDrawable) currentColorView.getBackground();
        background.setColor(color);
        currentTool = currentTool.changeColor(color);
    }

    public void changeStrokeWidth(int width) {
        currentTool = currentTool.changeSize(width);
    }

    public void sendDrawing() throws JSONException{
        JSONObject jsonDrawing = drawing.toJson();

        String url = appController.getBaseUrl() + "drawing";
        Log.d("", jsonDrawing.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url, jsonDrawing,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json_obj_req", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
            }
        });

        appController.addToRequestQueue(request);

    }

    public void undoLastStroke() {
        strokes = drawing.undoLastStroke();

        invalidate();
    }
}
