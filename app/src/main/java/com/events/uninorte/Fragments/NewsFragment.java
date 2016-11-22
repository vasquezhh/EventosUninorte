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

import com.events.uninorte.Activities.ShowNewActivity;
import com.events.uninorte.Adapters.NewsAdapter;
import com.events.uninorte.Models.NewsModel;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private TextView titlePage;
    private Progress progress;
    private int page = 1;
    private Service service;
    private ListView news;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        progress = new Progress(v.findViewById(R.id.newsProgress), v.findViewById(R.id.news), getResources());
        titlePage = (TextView) v.findViewById(R.id.title_page);
        news = (ListView) v.findViewById(R.id.news);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL).addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(Service.class);
        v.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page > 1) {
                    page--;
                    download();
                }
            }
        });
        v.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page < 8) {
                    page++;
                    download();
                }
            }
        });
        news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ShowNewActivity.class);
                intent.putExtra("url", ((NewsAdapter) news.getAdapter()).getItem(position).getLink());
                startActivity(intent);
            }
        });
        download();
        return v;
    }

    private void download() {
        progress.showProgress(true);
        titlePage.setText(page + " / 8");
        service.getNews(page + "").enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response.code() == 200) {
                    news.setAdapter(new NewsAdapter(getContext(), response.body()));
                    if (response.body().size() == 0) {
                        Toast.makeText(getContext(), "No hay Convocatorias", Toast.LENGTH_LONG).show();
                    }
                }
                progress.showProgress(false);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                t.fillInStackTrace();
                progress.showProgress(false);
            }
        });
    }
}
