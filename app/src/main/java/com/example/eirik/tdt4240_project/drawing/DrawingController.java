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
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.mainmenu.MatchAdapter;
import com.example.eirik.tdt4240_project.models.Drawing;
import com.example.eirik.tdt4240_project.models.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DrawingController extends View {

    private Path currentPath;
    private Drawing drawing;
    private Map<SerializablePath, Paint> strokes;
    private DrawingTool currentTool;
    private DrawingTool eraser;
    private DrawingTool lastBrush; // remembers last brush before changing to eraser
    private boolean drawingEnable;

    AppController appController = AppController.getInstance();

    public DrawingController(Context context, AttributeSet as){
        super(context, as);
        drawing = new Drawing();
        currentPath = new Path();
        currentTool = new Pen(Color.BLACK, 5);
        eraser = new Eraser();
        drawingEnable = true;
    }

    public void setDrawingEnable(boolean drawingEnable){
        this.drawingEnable = drawingEnable;
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

    public Drawing getDrawing() {
        return drawing;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(drawingEnable) {
            currentPath = drawing.addStroke(event, currentTool.getTool());

            //force view to draw
            invalidate();
        }
        return true;
    }

    public void changeColor(int color, TextView currentColorView) { // makes new tool
        //currentColorView.setBackgroundColor(color); // in GUI
        GradientDrawable background = (GradientDrawable) currentColorView.getBackground();
        background.setColor(color);
        currentTool = currentTool.changeColor(color);
    }

    public void changeBrushSize(int size) {
        currentTool = currentTool.changeSize(size);
    }

    public void sendDrawing(DrawingActivity drawingActivity) throws JSONException{
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

        final Match match = appController.getCurrentMatch();

        List<String> states = match.getAllowedStates();

        final String newState = states.indexOf(match.getState()) >= 3 ? states.get(0) : states.get(states.indexOf(match.getState()) + 1 );

        Log.d("newstate", newState);

        String matchUrl = appController.getBaseUrl() + "match/" + match.getId();

        StringRequest stateRequest = new StringRequest(Request.Method.PUT,
                matchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("json_obj_req", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("state", newState);
                return params;
            }};

        appController.addToRequestQueue(stateRequest);

        drawingActivity.goToMainMenu();

    }

    public void getRemoteDrawing(final String matchID){

        String url = appController.getBaseUrl() + "drawing/" + matchID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json_obj_req", response.toString());

                        try {
                            drawing = Drawing.fromJsonString(response.toString());
                            String word = response.getString("word");
                            appController.setCurrentWord(word);
                            invalidate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

    public void activateErasor() {
        lastBrush = currentTool;
        currentTool = eraser;
    }

    public void activatePencil() {
        currentTool = lastBrush;
    }

    public void getRemoteWord(final DrawingActivity drawingActivity) {

        String url = appController.getBaseUrl() + "word/";

        final ArrayList<String> wordList = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("json_obj_req", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                String word = obj.getString("word");

                                wordList.add(word);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Random random = new Random();

                        int randomNumber = random.nextInt(wordList.size());
                        drawingActivity.startDrawingActivity(wordList.get(randomNumber));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
            }
        });

        appController.addToRequestQueue(request);

    }
}
