package com.example.eirik.tdt4240_project.mainmenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.drawing.DrawingActivity;
import com.example.eirik.tdt4240_project.guessing.GuessingActivity;
import com.example.eirik.tdt4240_project.models.Match;
import com.example.eirik.tdt4240_project.newGame.NewGameActivity;

public class MainMenuActivity extends AppCompatActivity {

    MainMenuController controller = new MainMenuController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ListView listView = (ListView) findViewById(R.id.matchList);

        controller.getAndDisplayMatches(listView, getApplicationContext(), this);

    }

    public void goToDrawingView(Match match){
        startActivity(new Intent(MainMenuActivity.this, DrawingActivity.class));
    }

    public void goToGuessingView(Match match){
        startActivity(new Intent(MainMenuActivity.this, GuessingActivity.class));
    }

    public void displayDialog(String message, final Match match){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        controller.acceptInvitation(true, match);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        controller.acceptInvitation(false, match);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setTitle("Invite");
        builder.setMessage(message);
        builder.setPositiveButton("Yes", dialogClickListener);
        builder.setNegativeButton("No", dialogClickListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void newGame(View v){
        startActivity(new Intent(MainMenuActivity.this, NewGameActivity.class));
    }
}
