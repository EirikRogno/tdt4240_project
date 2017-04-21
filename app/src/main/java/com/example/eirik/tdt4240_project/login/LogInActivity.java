package com.example.eirik.tdt4240_project.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.mainmenu.MainMenuActivity;
import com.pushwoosh.fragment.PushEventListener;
import com.pushwoosh.fragment.PushFragment;


public class LogInActivity extends FragmentActivity implements PushEventListener {

    EditText usernameInput;
    LogInController controller = new LogInController();

    public void goToMainMenu(){
        startActivity(new Intent(LogInActivity.this, MainMenuActivity.class));
    }

    public void displayMessage(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    public void logIn(View v){
        controller.getUser(usernameInput.getText().toString(), this);
    }

    public void newAccount(View v){
        controller.createUser(usernameInput.getText().toString(), this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameInput = (EditText)findViewById(R.id.txtUsername);

        //Init Pushwoosh fragment
        PushFragment.init(this);

    }

    @Override
    public void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        //Check if we've got new intent with a push notification
        PushFragment.onNewIntent(this, intent);
    }

    private final String TAG = "pushwoosh";

    @Override
    public void doOnRegistered(String registrationId)
    {
        Log.i(TAG, "Registered for pushes: " + registrationId);
    }

    @Override
    public void doOnRegisteredError(String errorId)
    {
        Log.e(TAG, "Failed to register for pushes: " + errorId);
    }

    @Override
    public void doOnMessageReceive(String message)
    {
        Log.i(TAG, "Notification opened: " + message);
    }

    @Override
    public void doOnUnregistered(final String message)
    {
        Log.i(TAG, "Unregistered from pushes: " + message);
    }

    @Override
    public void doOnUnregisteredError(String errorId)
    {
        Log.e(TAG, "Failed to unregister from pushes: " + errorId);
    }

}
