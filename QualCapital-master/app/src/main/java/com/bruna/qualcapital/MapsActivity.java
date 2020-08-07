package com.bruna.qualcapital;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Button btnSearch;
    private TextInputEditText inputSearch;
    private String[] cities;
    private String[] countries;
    private String[] cityCodes;
    // typed array por ser um array de drawables
    private TypedArray flags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        btnSearch   = (Button) findViewById(R.id.btnSearch);
        inputSearch = (TextInputEditText) findViewById(R.id.textPlace);

        // popula arrays com infos dos xml
        cities      = getResources().getStringArray(R.array.cities);
        countries   = getResources().getStringArray(R.array.countries);
        cityCodes   = getResources().getStringArray(R.array.cities_codes);
        flags       = getResources().obtainTypedArray(R.array.flags);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchCity = inputSearch.getText().toString();

                for (int i = 0; i < cities.length; i++) {
                    if (cities[i].toLowerCase().equals(searchCity.toLowerCase())) {

                        // split cria array
                        // de "1,2"
                        // para ["1", "2"]
                        double latitude    = Double.parseDouble(cityCodes[i].split(",")[0]);
                        double longitude   = Double.parseDouble(cityCodes[i].split(",")[1]);

                        LatLng loc = new LatLng(latitude, longitude);

                        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(flags.getResourceId(i, 0));

                        mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .title(countries[i])
                                .icon(icon));

                        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                        mMap.animateCamera( CameraUpdateFactory.zoomTo( 7.0f ) );

                        return;
                    }
                }

                Toast.makeText(getApplicationContext(), R.string.error_no_city, Toast.LENGTH_SHORT).show();

            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
