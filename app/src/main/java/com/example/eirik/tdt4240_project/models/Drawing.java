package com.example.eirik.tdt4240_project.models;

import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;

import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.drawing.PaintSerializer;
import com.example.eirik.tdt4240_project.drawing.SerializablePath;
import com.example.eirik.tdt4240_project.utils.StringSerializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Drawing {

    private String id;
    private String userId;
    private String word;
    private String matchId;
    private Date timestamp;



    private SerializablePath path;
    private float x;
    private float y;
    private Map<SerializablePath, Paint> strokes; // drawing gets new stroke whenever the user lifts the brush, makes it possible for user to regret

    public Drawing() {
        id = "";
        userId = AppController.getInstance().getUsername();
        matchId = AppController.getInstance().getCurrentMatch().getId();
        timestamp = null;

        path = new SerializablePath(); // follows the path of drawing
        strokes = new LinkedHashMap<SerializablePath, Paint>();
    }

    public Map<SerializablePath, Paint> getDrawing() { // returns finished strokes
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
    * Fileformat is that the string will be JSON:
    *
    * { "matchid": "x",
    *   "userId": "x",
    *   "word": "x",
    *   "image": "x"
    * }
    *
    *
    * */


    public JSONObject toJson() throws JSONException{
        /*
        * Create JSON object with matchid, username, word and image.
        *
        * */

        // Drawing meta
        JSONObject json = new JSONObject();
        json.put("id", null);
        json.put("userId", this.getUserId());
        json.put("word", this.getWord());
        json.put("matchId", this.getMatchId());

        JSONArray image = new JSONArray();

        for (Map.Entry<SerializablePath, Paint> entry : strokes.entrySet()) {
            String path = StringSerializer.serializeToString(entry.getKey());
            JSONObject paint = PaintSerializer.toJSONObject(entry.getValue());

            JSONObject stroke = new JSONObject();
            stroke.put("path", path);
            stroke.put("paint", paint);

            image.put(stroke);
        }

        json.put("image", image);

        return json;
    }

    public String toJsonString() throws JSONException{
        return this.toJson().toString();
    }

    public static Drawing fromJsonString(String serializedDrawing)throws JSONException{
        Drawing newDrawing = new Drawing();

        JSONObject json = new JSONObject(serializedDrawing);

        JSONArray strokes = json.getJSONArray("image");

        for (int i = 0; i < strokes.length(); i++) {
            JSONObject stroke = (JSONObject) strokes.get(i);
            SerializablePath path = StringSerializer.constructFromString(stroke.getString("path"));
            Paint paint = new Paint();
            PaintSerializer.fromJSONObject(paint, stroke.getJSONObject("paint"));

            newDrawing.strokes.put(path, paint);
        }

        return newDrawing;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Map<SerializablePath, Paint> undoLastStroke() {
        if (strokes.size() > 0) {
            Path lastStroke = null;
            for (Path p: strokes.keySet()) {
                lastStroke = p;
            }
            if (lastStroke != null) {
                strokes.remove(lastStroke);
            }
        }
        return strokes;
    }
}
