package com.events.uninorte.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.events.uninorte.Models.NewsModel;
import com.events.uninorte.R;
import com.events.uninorte.Utils.ImageLoader;

import java.util.List;

/**
 * Created by hainerv on 9/10/16.
 */

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewsModel> newsModels;
    private ImageLoader imgLoader;

    public NewsAdapter(Context _mContext, List<NewsModel> _newsModels) {
        imgLoader = new ImageLoader(_mContext);
        newsModels = _newsModels;
        mContext = _mContext;
    }

    @Override
    public int getCount() {
        return newsModels.size();
    }

    @Override
    public NewsModel getItem(int position) {
        return newsModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsModel newsModel = newsModels.get(position);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_row_new, null);
        }
        ((TextView) convertView.findViewById(R.id.title)).setText(newsModel.getTitle());
        ((TextView) convertView.findViewById(R.id.date)).setText(newsModel.getDate());
        imgLoader.DisplayImage(newsModel.getImg(), R.drawable.photo_not_available, (ImageView) convertView.findViewById(R.id.img));
        return convertView;
    }
}
