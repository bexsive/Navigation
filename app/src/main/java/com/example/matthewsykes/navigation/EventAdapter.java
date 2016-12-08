package com.example.matthewsykes.navigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew Sykes on 11/6/2016.
 */

public class EventAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public EventAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        EventHolder eventHolder;

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            eventHolder = new EventHolder();
            eventHolder.txt_name = (TextView) row.findViewById(R.id.member_name);
            // Change this when I add separate TextView for date/time
            eventHolder.txt_date = (TextView) row.findViewById(R.id.eventDistance);
            eventHolder.txt_time = (TextView) row.findViewById(R.id.timeOfEvent);
            row.setTag(eventHolder);
        }
        else {
            eventHolder = (EventHolder) row.getTag();
        }

        Event event = (Event) this.getItem(position);

        eventHolder.txt_name.setText(event.getUserName());
        eventHolder.txt_date.setText(event.getDate());
        eventHolder.txt_time.setText(event.getTime());



        return row;
    }

    static class EventHolder{
        TextView txt_name, txt_date, txt_time;
    }
}
