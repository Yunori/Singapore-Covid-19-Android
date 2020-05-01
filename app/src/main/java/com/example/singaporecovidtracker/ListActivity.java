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
    String url = "https://services6.arcgis.com/LZwBmoXba0zrRap7/arcgis/rest/services/COVID_19_Prod_feature/FeatureServer/0/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=%2A&orderByFields=Case_ID%20desc&resultOffset=0&resultRecordCount=9999&resultType=standard&cacheHint=true";
    int reqno = 1;

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
                            if(reqno == 1 && response.has("error")) {
                                url = "https://services6.arcgis.com/LZwBmoXba0zrRap7/arcgis/rest/services/COVID_19_Prod_B_feature/FeatureServer/0/query?f=json&where=1%3D1&returnGeometry=false&spatialRel=esriSpatialRelIntersects&outFields=%2A&orderByFields=Case_ID%20desc&resultOffset=0&resultRecordCount=9999&resultType=standard&cacheHint=true";
                                reqno = 2;
                                request();
                            }
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
