package com.example.matthewsykes.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Matthew Sykes on 10/31/2016.
 */

public class ScoreboardFragment extends android.support.v4.app.Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            return inflater.inflate(R.layout.fragment_scoreboard, container, false);
        }
}
