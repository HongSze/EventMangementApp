package com.fit2081.assignment1;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.assignment1.databinding.ActivityGoogleMapBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;
    private String categoryLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the location from the Intent
        categoryLocation = getIntent().getStringExtra("CATEGORY_LOCATION");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Use Geocoder to get the location coordinates
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        LatLng latLng = null;
        String locationTitle = categoryLocation;

        try {
            if (categoryLocation == null || categoryLocation.isEmpty()) {
                // If the location is empty, default to Malaysia
                categoryLocation = "Malaysia";
                locationTitle = "Malaysia";
                Toast.makeText(this, "Category location not found, defaulting to Malaysia", Toast.LENGTH_SHORT).show();
            }

            List<Address> addresses = geocoder.getFromLocationName(categoryLocation, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                latLng = new LatLng(address.getLatitude(), address.getLongitude());
            } else {
                // Default to Malaysia if the location is not found
                List<Address> defaultAddresses = geocoder.getFromLocationName("Malaysia", 1);
                if (defaultAddresses != null && !defaultAddresses.isEmpty()) {
                    Address defaultAddress = defaultAddresses.get(0);
                    latLng = new LatLng(defaultAddress.getLatitude(), defaultAddress.getLongitude());
                    locationTitle = "Malaysia";
                    Toast.makeText(this, "Category location not found, defaulting to Malaysia", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Geocoder service not available", Toast.LENGTH_SHORT).show();
        }

        if (latLng != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            mMap.addMarker(new MarkerOptions().position(latLng).title(locationTitle));
        }
    }
}