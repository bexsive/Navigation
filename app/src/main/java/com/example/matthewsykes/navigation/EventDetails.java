package com.example.matthewsykes.navigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetails extends AppCompatActivity {

    ImageView imageView;
    TextView tx_name, tx_email, tx_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        imageView = (ImageView) findViewById(R.id.d_contact_image);
        tx_name = (TextView) findViewById(R.id.d_contact_name);
        tx_email = (TextView) findViewById(R.id.d_contact_email);
        tx_mobile = (TextView) findViewById(R.id.d_contact_mobile);

        imageView.setImageResource(getIntent().getIntExtra("img_id", 00));
        tx_name.setText("Name : "+getIntent().getStringExtra("member_name"));
        tx_email.setText("Email : "+getIntent().getStringExtra("date"));
        tx_mobile.setText("Mobile : "+getIntent().getStringExtra("time"));

    }
}