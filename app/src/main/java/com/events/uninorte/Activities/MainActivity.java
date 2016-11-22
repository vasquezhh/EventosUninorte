package com.events.uninorte.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.events.uninorte.Dialogs.ConfirmDialog;
import com.events.uninorte.Fragments.EventsFragment;
import com.events.uninorte.Fragments.MainEventsFragment;
import com.events.uninorte.Fragments.NewsFragment;
import com.events.uninorte.R;
import com.events.uninorte.Utils.Constants;

public class MainActivity extends FragmentActivity implements ConfirmDialog.LogoutListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost mTabHost = (FragmentTabHost) findViewById(R.id.menutabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.menutabcontent);
        mTabHost.addTab(mTabHost.newTabSpec("Principal").setIndicator(createTabView(mTabHost.getContext(), "Principal")), MainEventsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Eventos").setIndicator(createTabView(mTabHost.getContext(), "Eventos")), EventsFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("Convocatorias").setIndicator(createTabView(mTabHost.getContext(), "Convocatorias")), NewsFragment.class, null);
    }

    private View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
        TextView tv = (TextView) view.findViewById(R.id.tabsText);
        tv.setText(text);
        return view;
    }

    public void logout(View v) {
        (new ConfirmDialog()).show(getFragmentManager(), "Confirm");
    }

    @Override
    public void onLogout() {
        getSharedPreferences(Constants.PACKAGE, Activity.MODE_PRIVATE).edit().remove("login").commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
