package com.events.uninorte.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.events.uninorte.R;
import com.events.uninorte.Utils.Progress;

public class ShowNewActivity extends Activity {

    private Progress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_new);
        progress = new Progress(findViewById(R.id.newProgress), new View(getApplicationContext()), getResources());
    }

    public void back(View v) {
        finish();
    }

    public void assist(View v) {

    }
}
