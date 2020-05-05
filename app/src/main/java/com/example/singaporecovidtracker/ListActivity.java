package com.example.singaporecovidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private RecyclerView rv;
    ArrayList<Infected> objects;
    String url = "https://raw.githubusercontent.com/wentjun/covid-19-sg/master/src/data/covid-sg.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toast.makeText(this, "Loading... please wait.", Toast.LENGTH_LONG).show();
        rv = findViewById(R.id.list);
        request();
    }

    private void request() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("features")) {
                                JSONArray cases = response.getJSONArray("features");
                                objects = Infected.createInfectedList(cases);
                                InfectedAdapter adapter = new InfectedAdapter(objects, getApplicationContext());
                                rv.setAdapter(adapter);
                                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
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
}
