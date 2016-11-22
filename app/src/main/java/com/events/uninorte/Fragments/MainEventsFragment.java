package com.events.uninorte.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.events.uninorte.Adapters.MainEventsAdapter;
import com.events.uninorte.Models.EventMainModel;
import com.events.uninorte.R;
import com.events.uninorte.Services.Service;
import com.events.uninorte.Utils.Constants;
import com.events.uninorte.Utils.Progress;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainEventsFragment extends Fragment {

    private Progress progress;
    private ListView mainEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_events, container, false);
        progress = new Progress(v.findViewById(R.id.mainEventsProgress), v.findViewById(R.id.mainEvents), getResources());
        mainEvents = (ListView) v.findViewById(R.id.mainEvents);
        download();
        return v;
    }

    private void download() {
        progress.showProgress(true);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL).addConverterFactory(GsonConverterFactory.create()).build();
        Service service = retrofit.create(Service.class);
        service.getMain().enqueue(new Callback<List<EventMainModel>>() {
            @Override
            public void onResponse(Call<List<EventMainModel>> call, Response<List<EventMainModel>> response) {
                if (response.code() == 200) {
                    mainEvents.setAdapter(new MainEventsAdapter(getContext(), response.body()));
                    if (response.body().size() == 0) {
                        Toast.makeText(getContext(), "No hay Eventos", Toast.LENGTH_LONG).show();
                    }
                }
                progress.showProgress(false);
            }

            @Override
            public void onFailure(Call<List<EventMainModel>> call, Throwable t) {
                t.fillInStackTrace();
                progress.showProgress(false);
            }
        });
    }
}
