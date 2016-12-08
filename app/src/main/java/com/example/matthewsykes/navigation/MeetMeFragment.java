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
    static int requestCode = 1;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_meet_me, container, false);
            button = (Button) view.findViewById(R.id.newEventButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CreateEvent.class);
                    startActivityForResult(intent, requestCode);
                }
            });

            return view;

        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == HomeActivity.RESULT_OK){
                String result = data.getStringExtra("result");
            }
            if (resultCode == HomeActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
