package com.example.matthewsykes.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Matthew Sykes on 10/31/2016.
 */

public class FriendFeedFragment extends Fragment {
    String JSON_STRING;
    JSONObject jsonObject;
    JSONArray jsonArray;
    EventAdapter eventAdapter;
    ListView listView;
    Button syncButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_friend_feed, container, false);

        syncButton = (Button) v.findViewById(R.id.syncButton);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SyncFeed();
            }
        });

        JSON_STRING = getResources().getString(R.string.json_string);

        eventAdapter = new EventAdapter(getActivity(), R.layout.row_layout);

        listView = (ListView) v.findViewById(R.id.friend_feed_listView);
        listView.setAdapter(eventAdapter);

        buildList(JSON_STRING);

        return v;
    }

    public void buildList(String json){

        JSON_STRING = json;


        try {
            jsonObject = new JSONObject(JSON_STRING);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;

            String userName, date, time;

            while (count < jsonArray.length()){

                JSONObject JO = jsonArray.getJSONObject(count);
                userName = JO.getString("name");
                date = JO.getString("email");
                time = JO.getString("mobile");

                Event event = new Event(userName, date, time);
                eventAdapter.add(event);

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void SyncFeed(){
        //SyncListView syncListView = new SyncListView();
        //syncListView.execute();
        //JSON_STRING = syncListView.getMyData();
        buildList(JSON_STRING);
    }


}


