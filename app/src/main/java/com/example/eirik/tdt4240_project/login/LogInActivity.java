package com.example.eirik.tdt4240_project.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.eirik.tdt4240_project.R;
import com.example.eirik.tdt4240_project.mainmenu.MainMenuActivity;
import com.pushwoosh.BasePushMessageReceiver;
import com.pushwoosh.BaseRegistrationReceiver;
import com.pushwoosh.PushManager;


public class LogInActivity extends AppCompatActivity {
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
        controller.getUser(usernameInput.getText().toString().toLowerCase(), this);
    }

    public void newAccount(View v){
        controller.createUser(usernameInput.getText().toString().toLowerCase(), this);
    }

    String TAG = "Drawly";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        usernameInput = (EditText)findViewById(R.id.txtUsername);

        //Pushwoosh Begin Register receivers for push notifications
        registerReceivers();

        //Create and start push manager
        PushManager pushManager = PushManager.getInstance(this);

        //Start push manager, this will count app open for Pushwoosh stats as well
        try {
            pushManager.onStartup(this);
        } catch (Exception e) {
            //push notifications are not available or AndroidManifest.xml is not configured properly
        }

        //Register for push!
        pushManager.registerForPushNotifications();

        checkMessage(getIntent());
        //PushwooshEnd in onCreate
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        checkMessage(intent);
    }

    //Registration receiver
    BroadcastReceiver mBroadcastReceiver = new BaseRegistrationReceiver() {
        @Override
        public void onRegisterActionReceive(Context context, Intent intent) {
            checkMessage(intent);
        }
    };

    //Push message receiver
    private BroadcastReceiver mReceiver = new BasePushMessageReceiver() {
        @Override
        protected void onMessageReceive(Intent intent) {
            //JSON_DATA_KEY contains JSON payload of push notification.
            showMessage("push message is " + intent.getExtras().getString(JSON_DATA_KEY));
        }
    };

    //Registration of the receivers
    public void registerReceivers() {
        IntentFilter intentFilter = new IntentFilter(getPackageName() + ".action.PUSH_MESSAGE_RECEIVE");

        registerReceiver(mReceiver, intentFilter, getPackageName() + ".permission.C2D_MESSAGE", null);

        registerReceiver(mBroadcastReceiver, new IntentFilter(getPackageName() + "." + PushManager.REGISTER_BROAD_CAST_ACTION));
    }

    public void unregisterReceivers() {
        //Unregister receivers on pause
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
            // pass.
        }

        try {
            unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            //pass through
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Unregister receivers on pause
        unregisterReceivers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Re-register receivers on resume
        registerReceivers();
    }

    /**
     * Will check PushWoosh extras in this intent, and fire actual method
     *
     * @param intent activity intent
     */
    private void checkMessage(Intent intent) {
        if (null != intent) {
            if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT)) {
                showMessage("push message is " + intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT));
            } else if (intent.hasExtra(PushManager.REGISTER_EVENT)) {
                showMessage("register");
            } else if (intent.hasExtra(PushManager.UNREGISTER_EVENT)) {
                showMessage("unregister");
            } else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT)) {
                showMessage("register error");
            } else if (intent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT)) {
                showMessage("unregister error");
            }

            resetIntentValues();
        }
    }

    /**
     * Will check main Activity intent and if it contains any PushWoosh data, will clear it
     */
    private void resetIntentValues() {
        Intent mainAppIntent = getIntent();
        if (mainAppIntent != null) {
            if (mainAppIntent.hasExtra(PushManager.PUSH_RECEIVE_EVENT)) {
                mainAppIntent.removeExtra(PushManager.PUSH_RECEIVE_EVENT);
            } else if (mainAppIntent.hasExtra(PushManager.REGISTER_EVENT)) {
                mainAppIntent.removeExtra(PushManager.REGISTER_EVENT);
            } else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_EVENT)) {
                mainAppIntent.removeExtra(PushManager.UNREGISTER_EVENT);
            } else if (mainAppIntent.hasExtra(PushManager.REGISTER_ERROR_EVENT)) {
                mainAppIntent.removeExtra(PushManager.REGISTER_ERROR_EVENT);
            } else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT)) {
                mainAppIntent.removeExtra(PushManager.UNREGISTER_ERROR_EVENT);
            }

            setIntent(mainAppIntent);
        }

    }

    private void showMessage(String message) {
        Log.i(TAG,message);
    }
}
