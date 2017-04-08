package com.example.eirik.tdt4240_project.mainmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.eirik.tdt4240_project.R;

public class MainMenuActivity extends AppCompatActivity {

    MainMenuController controller = new MainMenuController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ListView listView = (ListView) findViewById(R.id.matchList);

        controller.getAndDisplayMatches(listView, getApplicationContext());

    }
}
