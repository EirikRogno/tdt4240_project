package com.example.eirik.tdt4240_project.drawing;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.mainmenu.MainMenuActivity;

public class DrawingActivity extends Activity {

    private DrawingController drawingController;
    private TextView wordField;
    private Button sendButton;
    private ImageButton colorButton;
    private ImageButton undoButton;
    private ColorPopup colors;
    private BrushSizePopup brushSize;
    private ImageButton pencilButton;
    private ImageButton eraserButton;
    private ImageButton brushSizeButton;
    private TextView currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        this.drawingController = (DrawingController)findViewById(R.id.draw);
        this.wordField = (TextView)findViewById(R.id.word);

        this.sendButton = (Button)findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopDrawingActivity();
            }
        });

        this.colorButton = (ImageButton)findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openColorPopup();
            }
        });

        this.brushSizeButton = (ImageButton)findViewById(R.id.brushSizeButton);
        brushSizeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openBrushSizePopup();
            }
        });

        this.undoButton = (ImageButton)findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                undo();
            }
        });

        this.currentColor = (TextView)findViewById(R.id.currentColor);

        this.eraserButton = (ImageButton)findViewById(R.id.eraser);
        eraserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activateEraser();
            }
        });

        this.pencilButton = (ImageButton)findViewById(R.id.pencil);
        pencilButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                activatePencil();
            }
        });

        drawingController.getRemoteWord(this);
    }

    private void openColorPopup() {
        colors = new ColorPopup(this);
        colors.showDialog(this);
    }

    private void openBrushSizePopup() {
        brushSize = new BrushSizePopup(this);
        brushSize.showDialog(this);
    }

    private void undo() {
        drawingController.undoLastStroke();
    }

    public void changeColor(int color) { // user wants to change color from colorPopup-window, notify controller
        drawingController.changeColor(color, currentColor);
    }

    public void changeSize(int size) { // user wants to change brush size from brushSizePopup-window, notify controller
        drawingController.changeBrushSize(size);
    }

    public void activateEraser() {
        pencilButton.setAlpha(0.5f);
        eraserButton.setAlpha(1.0f);
        colorButton.setAlpha(0f);
        currentColor.setAlpha(0f);
        brushSizeButton.setAlpha(0f);
        drawingController.activateErasor();
    }

    public void activatePencil() {
        pencilButton.setAlpha(1.0f);
        eraserButton.setAlpha(0.5f);
        colorButton.setAlpha(1f);
        currentColor.setAlpha(1f);
        brushSizeButton.setAlpha(1f);
        drawingController.activatePencil();
    }

    public void startDrawingActivity(String word) {
        // get a word from server
        drawingController.getDrawing().setWord(word);
        wordField.setText(word);
    }

    public void goToMainMenu(){
        startActivity(new Intent(DrawingActivity.this, MainMenuActivity.class));
    }

    private void stopDrawingActivity() {
        // send drawing to server
        Log.d("drawing", "About to send the drawing to the server, please wait for lift-off");
        try {
            drawingController.sendDrawing(this);
        } catch (Exception e) {
            // Display error to user
        }

    }

}
