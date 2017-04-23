package com.example.eirik.tdt4240_project.drawing;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.eirik.tdt4240_project.R;

/**
 * Created by ninagraneggen on 22.04.2017.
 */

public class BrushSizePopup {

    DrawingActivity belowActivity;

    BrushSizePopup(DrawingActivity activity) {
        belowActivity = activity;
    }

    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.brush_size_popup);

        Button closeButton = (Button) dialog.findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // BRUSH SIZE BUTTONS

        // Extra small brush
        ImageButton xSmall = (ImageButton) dialog.findViewById(R.id.xsmall);
        xSmall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeBrushSize(5);
                dialog.dismiss();
            }
        });

        // Small brush
        ImageButton small = (ImageButton) dialog.findViewById(R.id.small);
        small.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeBrushSize(10);
                dialog.dismiss();
            }
        });

        // Medium brush
        ImageButton medium = (ImageButton) dialog.findViewById(R.id.medium);
        medium.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeBrushSize(25);
                dialog.dismiss();
            }
        });

        // Large brush
        ImageButton large = (ImageButton) dialog.findViewById(R.id.large);
        large.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeBrushSize(50);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void changeBrushSize(int size) {
        belowActivity.changeSize(size);
    }
}
