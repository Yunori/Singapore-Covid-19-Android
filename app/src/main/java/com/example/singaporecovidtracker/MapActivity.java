package com.example.singaporecovidtracker;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.layers.TransitionOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.toNumber;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private GeoJsonSource geoJsonSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Mapbox.getInstance(this, "pk.eyJ1IjoiZGFya3JlaWtvbiIsImEiOiJjazk1amh0enAwYzBtM2ZsY3E0anZpYmdvIn0.Rkp56-Frgm817xKQuS7oZA");

        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap map) {
                map.setStyle(Style.DARK, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        mapboxMap = map;
                        mapboxMap.getUiSettings().setAttributionEnabled(false);
                        mapboxMap.getUiSettings().setLogoEnabled(false);

                        style.setTransition(new TransitionOptions(0, 0, false));

                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1.3521, 103.8198), 8));
                        addPolygonLayer(style);
                        addClusteredGeoJsonSource(style);

                        style.addImage(
                                "icon-id",
                                Objects.requireNonNull(BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle))));
                    }
                });
            }
        });
    }

    private void addClusteredGeoJsonSource(@NonNull Style loadedMapStyle) {
        try {
            geoJsonSource = new GeoJsonSource("infected",
                    new URI("https://raw.githubusercontent.com/wentjun/covid-19-sg/master/src/data/covid-sg.json"),
                    new GeoJsonOptions()
                            .withCluster(true)
                            .withClusterMaxZoom(50)
                            .withClusterRadius(50)
            );
            loadedMapStyle.addSource(
                    geoJsonSource
            );
        } catch (URISyntaxException uriSyntaxException) {
            Log.e("Check the URL %s", Objects.requireNonNull(uriSyntaxException.getMessage()));
        }

// Creating a marker layer for single data points
        SymbolLayer unclustered = new SymbolLayer("unclustered-points", "infected");

        unclustered.setProperties(
                iconImage("icon-id")
        );
        loadedMapStyle.addLayer(unclustered);

        int[][] layers = new int[][] {
                new int[] {150, ContextCompat.getColor(this, R.color.mapboxPurple)},
                new int[] {20, ContextCompat.getColor(this, R.color.mapboxOrange)},
                new int[] {0, ContextCompat.getColor(this, R.color.mapboxPinkDark)}
        };

        String[] queryLayerIds = new String[layers.length];

        for (int i = 0; i < layers.length; i++) {
            queryLayerIds[i] = "cluster-" + i;

            CircleLayer circles = new CircleLayer(queryLayerIds[i], "infected");
            circles.setProperties(
                    circleColor(layers[i][1]),
                    circleRadius(18f)
            );

            Expression pointCount = toNumber(get("point_count"));

            circles.setFilter(
                    i == 0
                            ? all(has("point_count"),
                            gte(pointCount, literal(layers[i][0]))
                    ) : all(has("point_count"),
                            gte(pointCount, literal(layers[i][0])),
                            lt(pointCount, literal(layers[i - 1][0]))
                    )
            );
            loadedMapStyle.addLayer(circles);
        }

        SymbolLayer count = new SymbolLayer("count", "infected");
        count.setProperties(
                textField(Expression.toString(get("point_count"))),
                textSize(12f),
                textColor(Color.WHITE),
                textIgnorePlacement(true),
                textAllowOverlap(true)
        );
        loadedMapStyle.addLayer(count);
    }

    private void addPolygonLayer(@NonNull Style loadedMapStyle) {
        try {
            geoJsonSource = new GeoJsonSource("clusters",
                    new URI("https://raw.githubusercontent.com/wentjun/covid-19-sg/master/src/data/locations.json")
            );
            loadedMapStyle.addSource(
                    geoJsonSource
            );
        } catch (URISyntaxException uriSyntaxException) {
            Log.e("Check the URL %s", Objects.requireNonNull(uriSyntaxException.getMessage()));
        }
        FillLayer countryPolygonFillLayer = new FillLayer("polygon", "clusters");
        countryPolygonFillLayer.setProperties(
                PropertyFactory.fillColor(Color.RED),
                PropertyFactory.fillOpacity(.4f));
        countryPolygonFillLayer.setFilter(eq(literal("$type"), literal("Polygon")));
        loadedMapStyle.addLayer(countryPolygonFillLayer);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}