package com.example.eirik.tdt4240_project.mainmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.login.LogInActivity;
import com.example.eirik.tdt4240_project.newGame.NewGameActivity;

public class MainMenuActivity extends AppCompatActivity {

    MainMenuController controller = new MainMenuController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ListView listView = (ListView) findViewById(R.id.matchList);

        controller.getAndDisplayMatches(listView, getApplicationContext());

    }

    public void newGame(View v){
        startActivity(new Intent(MainMenuActivity.this, NewGameActivity.class));
    }
}
