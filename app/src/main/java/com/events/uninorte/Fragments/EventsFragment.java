package com.events.uninorte.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.events.uninorte.Activities.ShowEventActivity;
import com.events.uninorte.Adapters.EventsAdapter;
import com.events.uninorte.Dialogs.DateEventPickerDialog;
import com.events.uninorte.Models.EventModel;
import com.events.uninorte.R;
import com.events.uninorte.Services.Service;
import com.events.uninorte.Utils.Constants;
import com.events.uninorte.Utils.Progress;
import com.events.uninorte.Utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment implements DateEventPickerDialog.DatePickerListener {

    private TextView titleDate;
    private Progress progress;
    private Calendar date;
    private Service service;
    private ListView events;
    private Fragment _this = this;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events, container, false);
        progress = new Progress(v.findViewById(R.id.eventsProgress), v.findViewById(R.id.events), getResources());
        titleDate = (TextView) v.findViewById(R.id.title_date);
        events = (ListView) v.findViewById(R.id.events);
        date = Calendar.getInstance();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(Service.class);
        v.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.add(Calendar.DAY_OF_MONTH, -1);
                download();
            }
        });
        v.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.add(Calendar.DAY_OF_MONTH, 1);
                download();
            }
        });
        titleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new DateEventPickerDialog(_this)).show(getActivity().getFragmentManager(), "Date Picker");
            }
        });
        events.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ShowEventActivity.class);
                intent.putExtra("url", ((EventsAdapter) events.getAdapter()).getItem(position).getLink());
                startActivity(intent);
            }
        });
        download();
        return v;
    }

    private void download() {
        progress.showProgress(true);
        titleDate.setText(Utils.date(date.getTime(), "EEEE, MMMM d, yyyy"));
        service.getEvents(Utils.date(date.getTime(), "yyyy-MM-dd")).enqueue(new Callback<List<EventModel>>() {
            @Override
            public void onResponse(Call<List<EventModel>> call, Response<List<EventModel>> response) {
                if (response.code() == 200) {
                    events.setAdapter(new EventsAdapter(getContext(), response.body()));
                    if (response.body().size() == 0) {
                        Toast.makeText(getContext(), "No hay Eventos", Toast.LENGTH_LONG).show();
                    }
                }
                progress.showProgress(false);
            }

            @Override
            public void onFailure(Call<List<EventModel>> call, Throwable t) {
                t.fillInStackTrace();
                progress.showProgress(false);
            }
        });
    }

    @Override
    public void setDate(Date _date) {
        date.setTime(_date);
        download();
    }
}
