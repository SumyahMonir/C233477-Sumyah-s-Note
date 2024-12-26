package com.example.sumyahsnote;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private static final int PERMISSION_ID = 42;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        getLastLocation();

        // Indicate that this fragment has its own menu options
        setHasOptionsMenu(true);

        return view;
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    if (mapFragment != null) {
                        mapFragment.getMapAsync(MapFragment.this);
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions()
                .position(currentLatLng)
                .title("Your Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.map_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mMap != null) {
            if (item.getItemId() == R.id.none) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
            } else if (item.getItemId() == R.id.normal) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            } else if (item.getItemId() == R.id.hybrid) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            } else if (item.getItemId() == R.id.terrain) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            } else if (item.getItemId() == R.id.satellite) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
