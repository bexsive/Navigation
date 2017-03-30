package com.example.matthewsykes.navigation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class CreateEvent extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final int PLACE_PICKER_REQUEST = 1;

    int year, month, day, hour, min;
    String location_name, location_address, location_phone, location_lat, location_long, date, time;
    TextView showTime, showDate, showDestination;
    Button destinationButton, timePickerButton, datePickerButton, submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        destinationButton = (Button) findViewById(R.id.destinationButton);
        showDestination = (TextView) findViewById(R.id.showDestination);

        timePickerButton = (Button) findViewById(R.id.timePickerButton);
        showTime = (TextView) findViewById(R.id.showTime);

        datePickerButton = (Button) findViewById(R.id.datePickerButton);
        showDate = (TextView) findViewById(R.id.showDate);

        destinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent = builder.build(CreateEvent.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                min = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateEvent.this, CreateEvent.this, hour, min, false);
                timePickerDialog.show();
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEvent.this, CreateEvent.this, year, month, day);
                datePickerDialog.show();
            }
        });

        submitButton = (Button) findViewById(R.id.returnHomeButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                PostEventToServer postEventToServer = new PostEventToServer(CreateEvent.this);
                postEventToServer.execute(location_name, location_address, location_phone, location_lat, location_long, date, time);

                setResult(HomeActivity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        year = i;
        month = i1 + 1;
        day = i2;

        date = month + " / " + day + " / " + year;

        showDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hour = i;
        min = i1;

        time = hour + ":" + min;
        showTime.setText(time);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                location_name = place.getName().toString();
                location_address = place.getAddress().toString();
                location_phone = place.getPhoneNumber().toString();
                LatLng queriedLocation = place.getLatLng();
                Double l1 = queriedLocation.latitude;
                Double l2 = queriedLocation.longitude;
                location_lat = l1.toString();
                location_long = l2.toString();

                String toastMsg = String.format("Place: %s", place.getName());
                showDestination.setText(toastMsg);
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class PostEventToServer extends AsyncTask<String, Void, String>{

        Context ctx;
        Activity activity;

        public PostEventToServer(Context ctx){
            this.ctx = ctx;
            activity = (Activity)ctx;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://inertiamobility.com/events/addEvent.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String location_name = params[0];
                String location_address = params[1];
                String location_phone = params[2];
                String location_lat = params[3];
                String location_long = params[4];
                String date = params[5];
                String time = params[6];
                String data = URLEncoder.encode("location_name", "UTF-8")+"="+ URLEncoder.encode(location_name, "UTF-8")+"&"+
                        URLEncoder.encode("location_address", "UTF-8")+"="+ URLEncoder.encode(location_address, "UTF-8")+"&"+
                        URLEncoder.encode("location_phone", "UTF-8")+"="+ URLEncoder.encode(location_phone, "UTF-8")+"&"+
                        URLEncoder.encode("location_lat", "UTF-8")+"="+ URLEncoder.encode(location_lat, "UTF-8")+"&"+
                        URLEncoder.encode("location_long", "UTF-8")+"="+ URLEncoder.encode(location_long, "UTF-8")+"&"+
                        URLEncoder.encode("date", "UTF-8")+"="+ URLEncoder.encode(date, "UTF-8")+"&"+
                        URLEncoder.encode("time", "UTF-8")+"="+ URLEncoder.encode(time, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while((line = bufferedReader.readLine())!=null){

                    stringBuilder.append(line+"\n");

                }
                httpURLConnection.disconnect();
                Thread.sleep(2000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


}