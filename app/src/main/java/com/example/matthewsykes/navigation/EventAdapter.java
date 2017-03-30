package com.example.matthewsykes.navigation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matthew Sykes on 11/6/2016.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    ArrayList<Event> events = new ArrayList<Event>();
    Context ctx;

    public EventAdapter(ArrayList<Event> events, Context ctx){
        this.events = events;
        this.ctx = ctx;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_layout, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(view, ctx, events);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

        //TODO: Thia ia where the JSON is bound to the card list items
        Event EVNT = events.get(position);
        holder.profile_pic.setImageResource(EVNT.getImage_id());
        holder.member_name.setText(EVNT.getUserName());
        holder.dateOfEvent.setText(EVNT.getDate());
        holder.timeOfEvent.setText(EVNT.getTime());


    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView profile_pic;
        TextView member_name, dateOfEvent, timeOfEvent;
        ArrayList<Event> events = new ArrayList<Event>();
        Context ctx;


        public EventViewHolder(View view, Context ctx, ArrayList<Event> events){
            super(view);
            this.events = events;
            this.ctx = ctx;
            view.setOnClickListener(this);

            //TODO: Thia ia where the mapping of variables go to XML layout items

            profile_pic = (ImageView)view.findViewById(R.id.profile_pic);
            member_name = (TextView) view.findViewById(R.id.member_name);
            dateOfEvent = (TextView) view.findViewById(R.id.dateOfEvent);
            timeOfEvent = (TextView) view.findViewById(R.id.timeOfEvent);
        }

        @Override
        public void onClick(View v){
            int position = getAdapterPosition();
            Event event = this.events.get(position);

            Intent intent = new Intent(this.ctx, EventDetails.class);
            intent.putExtra("img_id", event.getImage_id());
            intent.putExtra("member_name", event.getUserName());
            intent.putExtra("date", event.getDate());
            intent.putExtra("time", event.getTime());
            this.ctx.startActivity(intent);



        }
    }
}
