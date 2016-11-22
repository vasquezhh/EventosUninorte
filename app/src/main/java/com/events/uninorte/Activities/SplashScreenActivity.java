package com.events.uninorte.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.events.uninorte.R;
import com.events.uninorte.Utils.Constants;

public class SplashScreenActivity extends Activity {

    private Activity _this = this;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        prefs = getSharedPreferences(Constants.PACKAGE, MODE_PRIVATE);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                Intent intent;
                if (isToken()) {
                    intent = new Intent(_this, MainActivity.class);
                } else {
                    intent = new Intent(_this, LoginActivity.class);
                }
                startActivity(intent);
            }
        }, 2000);
    }

    private boolean isToken() {
        return prefs.getBoolean("login", false);
    }
}
