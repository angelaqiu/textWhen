package com.example.angelaq.textwhen;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

public class GPSActivity extends Activity {

    LocationManager locationManager;
    String destination;
    String when;
    String number;

    TextView txt;

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Double lat = location.getLatitude();
            Double lon = location.getLongitude();
            Log.v(GPSActivity.class.getName(), "lat: " + lat + "long: " + lon);
            Toast.makeText(GPSActivity.this, lat.toString(), Toast.LENGTH_SHORT).show();
            checkDistance(location);
        }



        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        txt = (TextView)findViewById(R.id.gps);
        final EditText mEdit = (EditText)findViewById(R.id.editText2);

        Button go = (Button)findViewById(R.id.ButtonSendFeedback);

        mEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
            * has valid values.
            */
                Log.v(GPSActivity.class.getName(), "??????");
                if (!hasFocus) {
                    destination = mEdit.getText().toString();
                    Log.v(GPSActivity.class.getName(), "destination saved");
                }
            }
        });

        final EditText mEdit2 = (EditText)findViewById(R.id.editText);

        mEdit2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
            * has valid values.
            */
                if (!hasFocus) {
                    when = mEdit2.getText().toString();
                    Log.v(GPSActivity.class.getName(), "when saved");
                }
            }
        });

        final EditText mEdit3 = (EditText)findViewById(R.id.editText3);

        mEdit3.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            /* When focus is lost check that the text field
            * has valid values.
            */
                if (!hasFocus) {
                    number = mEdit3.getText().toString();
                    Log.v(GPSActivity.class.getName(), "number saved");
                }
            }
        });

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
    }

    public void checkDistance(Location loc) {
        final TextView mTxtDisplay;
        mTxtDisplay = (TextView) findViewById(R.id.gps);
        Log.v(GPSActivity.class.getName(), "checking distance");

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC&mode=bicycling&language=fr-FR";

// Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        System.out.println(response.substring(0, 100));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String dest = response.getString("destination_addresses"),
                                    orig = response.getString("origin_addresses");
                            Log.v(GPSActivity.class.getName(), "dest: "+dest+"\norig: "+orig);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonRequest);

// Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
