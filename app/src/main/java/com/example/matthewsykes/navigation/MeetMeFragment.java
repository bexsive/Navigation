package com.example.matthewsykes.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * Created by Matthew Sykes on 10/31/2016.
 */

public class MeetMeFragment extends Fragment {

    Button button;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_meet_me, container, false);
            button = (Button) view.findViewById(R.id.newEventButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CreateEvent.class);
                    startActivity(intent);
                }
            });

            return view;

        }
}
