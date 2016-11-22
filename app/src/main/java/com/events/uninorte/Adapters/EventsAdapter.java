package com.events.uninorte.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.events.uninorte.Models.EventModel;
import com.events.uninorte.R;

import java.util.List;

/**
 * Created by hainerv on 9/10/16.
 */

public class EventsAdapter extends BaseAdapter {
    private Context mContext;
    private List<EventModel> eventModels;

    public EventsAdapter(Context _mContext, List<EventModel> _eventModels) {
        eventModels = _eventModels;
        mContext = _mContext;
    }

    @Override
    public int getCount() {
        return eventModels.size();
    }

    @Override
    public EventModel getItem(int position) {
        return eventModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventModel eventModel = eventModels.get(position);
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_row_event, null);
        }
        ((TextView) convertView.findViewById(R.id.title)).setText(eventModel.getTitle());
        ((TextView) convertView.findViewById(R.id.date)).setText(eventModel.getDate());
        ((TextView) convertView.findViewById(R.id.hour)).setText(eventModel.getHour());
        ((TextView) convertView.findViewById(R.id.categories)).setText(eventModel.getCategories());
        return convertView;
    }
}
