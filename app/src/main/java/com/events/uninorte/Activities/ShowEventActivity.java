package com.events.uninorte.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.events.uninorte.Models.EventModel;
import com.events.uninorte.R;
import com.events.uninorte.Receivers.NotificationReceiver;
import com.events.uninorte.Services.Service;
import com.events.uninorte.Utils.Constants;
import com.events.uninorte.Utils.Progress;
import com.events.uninorte.Utils.Utils;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowEventActivity extends Activity {

    private Progress progress;
    private EventModel eventModel;
    private String dateTime;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        Intent intent = getIntent();
        progress = new Progress(findViewById(R.id.eventProgress), findViewById(R.id.event), getResources());
        if (intent.hasExtra("url")) {
            getEvent(intent.getStringExtra("url"));
        }
        findViewById(R.id.assist).setVisibility(View.GONE);
    }

    public void back(View v) {
        finish();
    }

    public void assist(View v) {
        NotificationReceiver.SetAlarm(getApplicationContext(), date.getTime() - 2 * 60 * 60000, eventModel);
        Toast.makeText(getApplicationContext(), "Se ha agendado el evento", Toast.LENGTH_LONG).show();
        v.setVisibility(View.GONE);
    }

    public void assist_2(View v) {
        NotificationReceiver.SetAlarm(getApplicationContext(), Calendar.getInstance().getTimeInMillis() + 15000, eventModel);
        Toast.makeText(getApplicationContext(), "Se ha agendado el evento", Toast.LENGTH_LONG).show();
        v.setVisibility(View.GONE);
    }

    private void getEvent(String url) {
        progress.showProgress(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL).addConverterFactory(GsonConverterFactory.create()).build();
        Service service = retrofit.create(Service.class);
        service.getEvent(url).enqueue(new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                progress.showProgress(false);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        eventModel = response.body();
                        ((TextView) findViewById(R.id.title)).setText(eventModel.getTitle());
                        ((TextView) findViewById(R.id.time)).setText(eventModel.getTime());
                        ((TextView) findViewById(R.id.categories)).setText(eventModel.getCategories());
                        ((WebView) findViewById(R.id.description)).loadData(eventModel.getDescription(), "text/html; charset=UTF-8", null);
                        dateTime = eventModel.getTime().split(" - ")[0].replace(")", "");
                        date = Utils.String2DateSpecial(dateTime, "EEEE, MMMM d, yyyy (hh:mm aaa");
                        Calendar calendar = Calendar.getInstance();
                        if (date != null) {
                            if (date.after(calendar.getTime())) {
                                findViewById(R.id.assist).setVisibility(View.VISIBLE);
                            }
                        } else {
                            dateTime = dateTime.replace("PM", "p. m.").replace("AM", "a. m.");
                            date = Utils.String2DateSpecial(dateTime, "EEEE, MMMM d, yyyy (hh:mm aaa");
                            if (date != null) {
                                if (date.after(calendar.getTime())) {
                                    findViewById(R.id.assist).setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "No se formatear la fecha", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo cargar evento", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                progress.showProgress(false);
                Toast.makeText(getApplicationContext(), "No se pudo cargar evento", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}
