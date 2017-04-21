package com.example.eirik.tdt4240_project.drawing;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.eirik.tdt4240_project.R;

/**
 * Created by ninagraneggen on 24.03.2017.
 */

public class ColorPopup {

    DrawingActivity belowActivity;

    ColorPopup(DrawingActivity activity) {
        belowActivity = activity;
    }

    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.color_popup);

        Button closeButton = (Button) dialog.findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // COLOR BUTTONS

        Button blackButton = (Button) dialog.findViewById(R.id.blackButton);
        blackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeColor(Color.parseColor("#000000"));
                dialog.dismiss();
            }
        });

        Button redButton = (Button) dialog.findViewById(R.id.redButton);
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeColor(Color.parseColor("#FFFF4444"));
                dialog.dismiss();
            }
        });

        Button blueButton = (Button) dialog.findViewById(R.id.blueButton);
        blueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeColor(Color.parseColor("#FF33B5E5"));
                dialog.dismiss();
            }
        });

        Button greenButton = (Button) dialog.findViewById(R.id.greenButton);
        greenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeColor(Color.parseColor("#FF99CC00"));
                dialog.dismiss();
            }
        });

        Button yellowButton = (Button) dialog.findViewById(R.id.yellowButton);
        yellowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeColor(Color.parseColor("#ffffff00"));
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void changeColor(int color) {
        belowActivity.changeColor(color);
    }
}
