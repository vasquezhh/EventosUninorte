package com.events.uninorte.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.events.uninorte.Models.EventMainModel;
import com.events.uninorte.R;

import java.util.List;

/**
 * Created by hainerv on 9/10/16.
 */

public class MainEventsAdapter extends BaseAdapter {

    private Context mContext;
    private List<EventMainModel> eventMainModels;

    public MainEventsAdapter(Context _mContext, List<EventMainModel> _eventMainModels) {
        eventMainModels = _eventMainModels;
        mContext = _mContext;
    }

    @Override
    public int getCount() {
        return eventMainModels.size();
    }

    @Override
    public EventMainModel getItem(int position) {
        return eventMainModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventMainModel eventMainModel = eventMainModels.get(position);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_row_event_main, null);
        }
        ((TextView) convertView.findViewById(R.id.title)).setText(eventMainModel.getTitle());
        ((TextView) convertView.findViewById(R.id.day)).setText(eventMainModel.getDay());
        ((TextView) convertView.findViewById(R.id.moth)).setText(eventMainModel.getMonth());
        return convertView;
    }
}
