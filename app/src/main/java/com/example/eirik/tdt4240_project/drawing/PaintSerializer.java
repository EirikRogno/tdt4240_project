package com.example.eirik.tdt4240_project.drawing;

/**
 * Created by Sindre on 4/19/2017.
 */

import android.graphics.Color;
import android.graphics.Paint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 Serialization helper for android.graphics.Paint objects.
 */
public final class PaintSerializer
    /* we don't call this class Paint b/c the name collisions would be a headache */ {
    /**
     Serializes p to a new JSONObject and returns it.

     @param p Input object to serialize. Must not be null.
     @return Serialized state of p.
     */
    public static JSONObject toJSONObject(final Paint p) throws JSONException{
        JSONObject jo = new JSONObject();
        jo.put("color", p.getColor());
        jo.put("strokeWidth", p.getStrokeWidth());
        jo.put("flags", p.getFlags());
        jo.put("alpha", p.getAlpha());
        jo.put("style", p.getStyle().toString() );
        return jo;
    }

    private final static Pattern PATTERN_INTEGER = Pattern.compile("^\\d+$");
    /**
     Deserializes p from jo. Returns false if jo "does not appear" to have been
     serialized by toJSONObject() (and does not modify p in that case), else
     it returns true.

     @param jo Source object in the format produced by toJSONObject()
     @param p Target object to deserialize the state to.
     @return True if deserialization at least partly worked, else false.
     */
    public static boolean fromJSONObject( final Paint p, final JSONObject jo){
        String s = jo.optString("color", null);
        if (null == s) return false;
        else { // try integer and #RRGGBB
            final Matcher match = PATTERN_INTEGER.matcher( s );
            p.setColor(Integer.parseInt(s));
        }
        double d = jo.optDouble("strokeWidth", p.getStrokeWidth() );
        p.setStrokeWidth( (float)d );
        int i = jo.optInt("flags", p.getFlags());
        p.setFlags( i );
        i = jo.optInt("alpha", p.getAlpha());
        p.setAlpha( i );
        s = jo.optString("style",null);
        if(null!=s) {
            try {
                p.setStyle(Paint.Style.valueOf(s));
            }catch(Exception e){/* ignore*/}
        }
        return true;
    }
}