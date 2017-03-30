package com.example.matthewsykes.navigation;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Matthew Sykes on 10/31/2016.
 */

public class FriendFeedFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Event> list = new ArrayList<>();

    int[] image_id = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i};

    //TODO: Add strings.xml for Event data
    String[] name, email, mobile;
    Context ctx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ctx = getActivity().getApplicationContext();

        View v = inflater.inflate(R.layout.fragment_friend_feed, container, false);


        //TODO: Load something into UI before data is updated from online
        name = getResources().getStringArray(R.array.person_name);
        email = getResources().getStringArray(R.array.person_email);
        mobile = getResources().getStringArray(R.array.person_mobile);
        int count = 0;
        //TODO: Parse into current event constructor
        for (String Name : name) {
            Event event = new Event(image_id[count], Name, email[count], mobile[count]);
            count++;
            list.add(event);
        }

        recyclerView = (RecyclerView) v.findViewById(R.id.friend_feed_recyclerView);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        // TODO: Put this list as data from server
        adapter = new EventAdapter(list, ctx);
        recyclerView.setAdapter(adapter);

        // TODO: Get more events by scrolling down
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){

                if (layoutManager.findLastCompletelyVisibleItemPosition() == list.size() -1){
                    load_data_from_server(list.get(list.size()-1).getId());
                }

            }
        });

        return v;
    }

    private void load_data_from_server(int id){
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = OkHttpClient();

                Request request = new Request.Builder().url("phpURL goes here.php?id="+id);
                Response response = client.newCall(request).execute();

                JSON array = new JSONArray(response.body().string());

                for i < array.length()
                        JSONObject object = array.getJSONObject(i);
                Event event = new Event(Fill constructor with-> object.getString("Data for event from server"),
                        object.getString("More of the same, maybe date, time, event location, ect"));
                        object.getInt("time");
                list.add(event);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);

    }
}

