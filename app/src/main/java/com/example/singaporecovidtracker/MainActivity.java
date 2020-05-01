package com.example.singaporecovidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

private TextView title, confirmedCases, deaths, recovered, active;
private ImageButton listButton, mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No internet permission", Toast.LENGTH_LONG).show();
        }
        else {
            if (isNetworkConnected()){
                Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
                request();
            }else{
                Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
                request();
            }

        }
    }

    public boolean isNetworkConnected() {
        final ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
                final Network n = cm.getActiveNetwork();

                if (n != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);

                    assert nc != null;
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        listButton.setEnabled(true);
        mapButton.setEnabled(true);
    }

    private void setupUI() {
        title = findViewById(R.id.title);
        confirmedCases = findViewById(R.id.confirmedCases);
        deaths = findViewById(R.id.deaths);
        active = findViewById(R.id.active);
        recovered = findViewById(R.id.recovered);
        listButton = findViewById(R.id.listButton);
        mapButton = findViewById(R.id.mapButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listButton.setEnabled(false);
                mapButton.setEnabled(false);
                startList();
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapButton.setEnabled(false);
                listButton.setEnabled(false);
                startMap();
            }
        });
    }

    private void request() {
        String url = "https://corona.lmao.ninja/v2/countries/singapore";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Date df = new java.util.Date(Long.parseLong(response.get("updated").toString()));
                            String sd = new SimpleDateFormat("dd-MM-yyyy hh:mma").format(df);
                            title.setText(getString(R.string.Title)+ System.getProperty("line.separator") + sd);
                            confirmedCases.setText(getString(R.string.ConfirmedCases)+ System.getProperty("line.separator") + response.get("cases"));
                            deaths.setText(getString(R.string.Deaths) + System.getProperty("line.separator") + response.get("deaths"));
                            recovered.setText(getString(R.string.RecoveredCases) + System.getProperty("line.separator") + response.get("recovered"));
                            active.setText(getString(R.string.ActiveCases) + System.getProperty("line.separator") + response.get("active"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Data error", Toast.LENGTH_LONG).show();
                    }
                });
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }

    public void startList() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void startMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
